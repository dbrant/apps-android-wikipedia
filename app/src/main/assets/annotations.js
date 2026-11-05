/*
 * Anchors user annotations to the text of the article, and renders them as highlights.
 *
 * Each annotation is described by a W3C-style TextQuoteSelector (exact, prefix, suffix) and a
 * TextPositionSelector (start, end), both measured against the concatenated text of the article's
 * sections. When the article has been edited since the annotation was made, the quote is located
 * by exact search (scored by surrounding context), and failing that, by approximate search.
 */
(function () {
    if (window.wikiAnnotations) {
        return;
    }

    var CONTEXT_LENGTH = 32;
    var MAX_ERROR_RATIO = 0.25;
    var MAX_ERRORS = 64;
    var MARK_CLASS = 'wiki-annotation';
    var FLASH_CLASS = 'wiki-annotation-flash';
    var EXCLUDED_SELECTOR = 'script, style, noscript, .pcs-edit-section-link-container';
    var SECTION_SELECTOR = 'section[data-mw-section-id]';
    var XHTML_NAMESPACE = 'http://www.w3.org/1999/xhtml';

    var style = document.createElement('style');
    style.textContent =
        'mark.' + MARK_CLASS + ' { background-color: rgba(255, 204, 51, 0.45); color: inherit; border-radius: 2px; transition: background-color 0.4s; }' +
        'mark.' + MARK_CLASS + '.' + FLASH_CLASS + ' { background-color: rgba(255, 170, 0, 0.9); }';
    document.head.appendChild(style);

    function getRoots() {
        var sections = Array.prototype.slice.call(document.querySelectorAll(SECTION_SELECTOR));
        var topLevel = sections.filter(function (section) {
            return !section.parentElement || !section.parentElement.closest(SECTION_SELECTOR);
        });
        return topLevel.length ? topLevel : [document.body];
    }

    // Returns the article's text, plus the list of text nodes with the offset at which each begins.
    function buildIndex() {
        var nodes = [];
        var parts = [];
        var length = 0;
        getRoots().forEach(function (root) {
            var walker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT, {
                acceptNode: function (node) {
                    var parent = node.parentElement;
                    return parent && parent.closest(EXCLUDED_SELECTOR) ? NodeFilter.FILTER_REJECT : NodeFilter.FILTER_ACCEPT;
                }
            });
            var node;
            while ((node = walker.nextNode())) {
                nodes.push({ node: node, start: length });
                parts.push(node.data);
                length += node.data.length;
            }
        });
        return { nodes: nodes, text: parts.join('') };
    }

    function boundaryToOffset(index, container, offset) {
        var i;
        if (container.nodeType === Node.TEXT_NODE) {
            for (i = 0; i < index.nodes.length; i++) {
                if (index.nodes[i].node === container) {
                    return index.nodes[i].start + offset;
                }
            }
        }
        var point = document.createRange();
        point.setStart(container, offset);
        for (i = 0; i < index.nodes.length; i++) {
            if (point.comparePoint(index.nodes[i].node, 0) >= 0) {
                return index.nodes[i].start;
            }
        }
        return index.text.length;
    }

    function buildSelector(text, start, end) {
        return {
            exact: text.substring(start, end),
            prefix: text.substring(Math.max(0, start - CONTEXT_LENGTH), start),
            suffix: text.substring(end, end + CONTEXT_LENGTH),
            start: start,
            end: end
        };
    }

    function trimWhitespace(text, range) {
        var start = range.start;
        var end = range.end;
        while (start < end && /\s/.test(text.charAt(start))) {
            start++;
        }
        while (end > start && /\s/.test(text.charAt(end - 1))) {
            end--;
        }
        return { start: start, end: end };
    }

    function getRevision() {
        try {
            return parseInt(pcs.c1.Page.getRevision(), 10) || 0;
        } catch (e) {
            return 0;
        }
    }

    // Number of characters of the stored prefix and suffix that match the text around [start, end).
    function contextScore(text, start, end, annotation) {
        var score = 0;
        var prefix = annotation.prefix || '';
        var suffix = annotation.suffix || '';
        var i;
        for (i = 1; i <= prefix.length && start - i >= 0; i++) {
            if (prefix.charAt(prefix.length - i) !== text.charAt(start - i)) {
                break;
            }
            score++;
        }
        for (i = 0; i < suffix.length && end + i < text.length; i++) {
            if (suffix.charAt(i) !== text.charAt(end + i)) {
                break;
            }
            score++;
        }
        return score;
    }

    // Penalty between 0 and 1, used only to break ties in favor of the original position.
    function distancePenalty(text, start, expectedStart) {
        return Math.abs(start - expectedStart) / Math.max(text.length, 1);
    }

    /*
     * Approximate substring search (Sellers' algorithm with Ukkonen's cutoff), allowing a number
     * of edits proportional to the length of the quote. Tracks the start position of each match
     * alongside its edit distance, so the matched region can be recovered directly.
     */
    function fuzzySearch(text, annotation) {
        var pattern = annotation.exact;
        var m = pattern.length;
        var maxErrors = Math.min(Math.floor(m * MAX_ERROR_RATIO), MAX_ERRORS);
        if (maxErrors < 1) {
            return null;
        }
        var cost = new Int32Array(m + 1);
        var origin = new Int32Array(m + 1);
        var i;
        for (i = 0; i <= m; i++) {
            cost[i] = i;
        }
        var lastActive = Math.min(maxErrors, m);
        var best = null;

        for (var j = 1; j <= text.length; j++) {
            var ch = text.charCodeAt(j - 1);
            var diagCost = cost[0];
            var diagOrigin = origin[0];
            cost[0] = 0;
            origin[0] = j;
            var limit = Math.min(lastActive + 1, m);
            for (i = 1; i <= limit; i++) {
                // Rows beyond the last active row are known to exceed maxErrors.
                var leftCost = i > lastActive ? maxErrors + 1 : cost[i];
                var leftOrigin = origin[i];
                var newCost = diagCost + (pattern.charCodeAt(i - 1) === ch ? 0 : 1);
                var newOrigin = diagOrigin;
                if (leftCost + 1 < newCost) {
                    newCost = leftCost + 1;
                    newOrigin = leftOrigin;
                }
                if (cost[i - 1] + 1 < newCost) {
                    newCost = cost[i - 1] + 1;
                    newOrigin = origin[i - 1];
                }
                diagCost = leftCost;
                diagOrigin = leftOrigin;
                cost[i] = newCost;
                origin[i] = newOrigin;
            }
            lastActive = limit;
            while (lastActive > 0 && cost[lastActive] > maxErrors) {
                lastActive--;
            }
            if (lastActive === m) {
                var start = origin[m];
                var errors = cost[m];
                var context = contextScore(text, start, j, annotation);
                var penalty = distancePenalty(text, start, annotation.start);
                if (!best || errors < best.errors ||
                    (errors === best.errors && (context > best.context || (context === best.context && penalty < best.penalty)))) {
                    best = { start: start, end: j, errors: errors, context: context, penalty: penalty };
                }
            }
        }
        return best ? trimWhitespace(text, best) : null;
    }

    function locate(text, annotation) {
        if (!annotation.exact) {
            return null;
        }
        if (text.substring(annotation.start, annotation.end) === annotation.exact) {
            return { start: annotation.start, end: annotation.end, status: 'exact' };
        }
        var best = null;
        var position = text.indexOf(annotation.exact);
        while (position !== -1) {
            var end = position + annotation.exact.length;
            var score = contextScore(text, position, end, annotation) - distancePenalty(text, position, annotation.start);
            if (!best || score > best.score) {
                best = { start: position, end: end, score: score };
            }
            position = text.indexOf(annotation.exact, position + 1);
        }
        if (best) {
            return { start: best.start, end: best.end, status: 'moved' };
        }
        var fuzzy = fuzzySearch(text, annotation);
        if (fuzzy && fuzzy.end > fuzzy.start) {
            return { start: fuzzy.start, end: fuzzy.end, status: 'fuzzy' };
        }
        return null;
    }

    // Taps on links inside a highlight are left for the link handler.
    document.addEventListener('click', function (event) {
        var target = event.target;
        var mark = target && target.closest ? target.closest('mark.' + MARK_CLASS) : null;
        if (!mark || target.closest('a[href]')) {
            return;
        }
        event.preventDefault();
        event.stopPropagation();
        pcsClient.onReceiveMessage(JSON.stringify({
            action: 'annotation_clicked',
            data: { id: parseInt(mark.getAttribute('data-annotation-id'), 10) }
        }));
    }, true);

    // Wraps the text in [start, end) with mark elements, splitting text nodes as needed and
    // keeping the index in sync with the split nodes.
    function wrap(index, start, end, id) {
        var marks = [];
        for (var i = 0; i < index.nodes.length; i++) {
            var entry = index.nodes[i];
            var node = entry.node;
            var nodeEnd = entry.start + node.data.length;
            if (nodeEnd <= start) {
                continue;
            }
            if (entry.start >= end) {
                break;
            }
            if (start > entry.start) {
                index.nodes.splice(i + 1, 0, { node: node.splitText(start - entry.start), start: start });
                continue;
            }
            if (end < nodeEnd) {
                index.nodes.splice(i + 1, 0, { node: node.splitText(end - entry.start), start: end });
            }
            var parent = node.parentNode;
            if (!parent || parent.namespaceURI !== XHTML_NAMESPACE || !/\S/.test(node.data)) {
                continue;
            }
            var mark = document.createElement('mark');
            mark.className = MARK_CLASS;
            mark.setAttribute('data-annotation-id', String(id));
            parent.insertBefore(mark, node);
            mark.appendChild(node);
            marks.push(mark);
        }
        return marks;
    }

    function getMarks(id) {
        return Array.prototype.slice.call(document.querySelectorAll('mark.' + MARK_CLASS + '[data-annotation-id="' + id + '"]'));
    }

    function unwrap(marks) {
        marks.forEach(function (mark) {
            var parent = mark.parentNode;
            while (mark.firstChild) {
                parent.insertBefore(mark.firstChild, mark);
            }
            parent.removeChild(mark);
            parent.normalize();
        });
    }

    function anchorAll(index, annotations) {
        return annotations.map(function (annotation) {
            var location = locate(index.text, annotation);
            if (!location || !wrap(index, location.start, location.end, annotation.id).length) {
                return { id: annotation.id, status: 'orphaned' };
            }
            var result = buildSelector(index.text, location.start, location.end);
            result.id = annotation.id;
            result.status = location.status;
            return result;
        });
    }

    window.wikiAnnotations = {
        describeSelection: function () {
            var selection = window.getSelection();
            if (!selection || selection.rangeCount === 0 || selection.isCollapsed) {
                return null;
            }
            var range = selection.getRangeAt(0);
            var index = buildIndex();
            var bounds = trimWhitespace(index.text, {
                start: boundaryToOffset(index, range.startContainer, range.startOffset),
                end: boundaryToOffset(index, range.endContainer, range.endOffset)
            });
            if (bounds.end <= bounds.start) {
                return null;
            }
            var result = buildSelector(index.text, bounds.start, bounds.end);
            result.revision = getRevision();
            return result;
        },

        // Replaces all highlights on the page with the given annotations.
        apply: function (annotations) {
            unwrap(Array.prototype.slice.call(document.querySelectorAll('mark.' + MARK_CLASS)));
            return { revision: getRevision(), results: anchorAll(buildIndex(), annotations) };
        },

        add: function (annotation) {
            unwrap(getMarks(annotation.id));
            return { revision: getRevision(), results: anchorAll(buildIndex(), [annotation]) };
        },

        remove: function (id) {
            unwrap(getMarks(id));
        },

        scrollTo: function (id) {
            var marks = getMarks(id);
            if (!marks.length) {
                return false;
            }
            var top = marks[0].getBoundingClientRect().top + window.scrollY;
            window.scrollTo(0, Math.max(0, top - window.innerHeight / 3));
            setTimeout(function () {
                marks.forEach(function (mark) { mark.classList.add(FLASH_CLASS); });
                setTimeout(function () {
                    marks.forEach(function (mark) { mark.classList.remove(FLASH_CLASS); });
                }, 800);
            }, 250);
            return true;
        }
    };
})();
