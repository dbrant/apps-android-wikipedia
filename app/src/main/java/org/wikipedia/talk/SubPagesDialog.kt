package org.wikipedia.talk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.wikipedia.Constants
import org.wikipedia.R
import org.wikipedia.databinding.DialogSubpagesBinding
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.dataclient.mwapi.MwQueryPage
import org.wikipedia.page.ExtendedBottomSheetDialogFragment
import org.wikipedia.page.PageTitle
import org.wikipedia.readinglist.database.ReadingList
import org.wikipedia.util.L10nUtil
import org.wikipedia.util.StringUtil
import org.wikipedia.util.log.L
import org.wikipedia.views.PageItemView

class SubPagesDialog : ExtendedBottomSheetDialogFragment() {
    private var _binding: DialogSubpagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageTitle: PageTitle
    private val titleList = mutableListOf<MwQueryPage>()
    private val itemCallback = ItemCallback()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitle = requireArguments().getParcelable(TITLE)!!
    }

    override fun onDestroy() {
        disposables.clear()
        _binding = null
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogSubpagesBinding.inflate(inflater, container, false)
        binding.subpagesRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.subpagesRecycler.adapter = TitleAdapter()

        binding.subpagesDialogTitle.text = StringUtil.fromHtml(getString(R.string.sub_pages_dialog_title, pageTitle.displayText))
        L10nUtil.setConditionalLayoutDirection(binding.root, pageTitle.wikiSite.languageCode)
        loadSubPages()
        return binding.root
    }

    private fun loadSubPages() {
        binding.subpagesError.visibility = View.GONE
        binding.subpagesNoneFound.visibility = View.GONE
        binding.subpagesRecycler.visibility = View.GONE
        binding.dialogSubpagesProgress.visibility = View.VISIBLE

        val searchPrefix = pageTitle.prefixedText + "/"
        disposables.add(ServiceFactory.get(pageTitle.wikiSite).prefixSearch(searchPrefix, 100, searchPrefix)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { binding.dialogSubpagesProgress.visibility = View.GONE }
                .subscribe({ response ->
                    titleList.clear()
                    response.query?.pages?.forEach {
                        titleList.add(it)
                    }
                    titleList.sortBy { it.title }
                    layOutSubPages()
                }) {
                    binding.subpagesError.setError(it)
                    binding.subpagesError.visibility = View.VISIBLE
                    L.e(it)
                })
    }

    private fun layOutSubPages() {
        if (titleList.isEmpty()) {
            binding.subpagesNoneFound.visibility = View.VISIBLE
            binding.subpagesRecycler.visibility = View.GONE
        }
        binding.subpagesRecycler.visibility = View.VISIBLE
        binding.subpagesNoneFound.visibility = View.GONE
        binding.subpagesError.visibility = View.GONE
    }

    private inner class TitleItemHolder constructor(itemView: PageItemView<PageTitle>) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(page: MwQueryPage) {
            val title = PageTitle(page.title, pageTitle.wikiSite)
            view.item = title
            var text = title.displayText
            if (text.startsWith(pageTitle.displayText)) {
                text = pageTitle.displayText + "<strong>" + title.displayText.replace(pageTitle.displayText, "") + "</strong>"
            }
            view.setTitle(text)
        }

        val view: PageItemView<PageTitle>
            get() = itemView as PageItemView<PageTitle>
    }

    private inner class TitleAdapter : RecyclerView.Adapter<TitleItemHolder>() {
        override fun getItemCount(): Int {
            return titleList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, pos: Int): TitleItemHolder {
            val view = PageItemView<PageTitle>(requireContext())
            return TitleItemHolder(view)
        }

        override fun onBindViewHolder(holder: TitleItemHolder, pos: Int) {
            holder.bindItem(titleList[pos])
        }

        override fun onViewAttachedToWindow(holder: TitleItemHolder) {
            super.onViewAttachedToWindow(holder)
            holder.view.callback = itemCallback
        }

        override fun onViewDetachedFromWindow(holder: TitleItemHolder) {
            holder.view.callback = null
            super.onViewDetachedFromWindow(holder)
        }
    }

    private inner class ItemCallback : PageItemView.Callback<PageTitle?> {
        override fun onClick(item: PageTitle?) {
            if (item != null) {
                startActivity(TalkTopicsActivity.newIntent(requireActivity(), item, Constants.InvokeSource.TALK_ACTIVITY))
            }
        }

        override fun onLongClick(item: PageTitle?): Boolean {
            return false
        }

        override fun onActionClick(item: PageTitle?, view: View) {}

        override fun onListChipClick(readingList: ReadingList) {}
    }

    companion object {
        private const val TITLE = "title"

        @JvmStatic
        fun newInstance(title: PageTitle): SubPagesDialog {
            return SubPagesDialog().apply { arguments = bundleOf(TITLE to title) }
        }
    }
}
