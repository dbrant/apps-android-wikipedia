(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory();
	else if(typeof define === 'function' && define.amd)
		define([], factory);
	else if(typeof exports === 'object')
		exports["pcs"] = factory();
	else
		root["pcs"] = factory();
})(this, function() {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/pcs/index.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./node_modules/@babel/runtime/helpers/classCallCheck.js":
/*!***************************************************************!*\
  !*** ./node_modules/@babel/runtime/helpers/classCallCheck.js ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

module.exports = _classCallCheck;
module.exports["default"] = module.exports, module.exports.__esModule = true;

/***/ }),

/***/ "./node_modules/@babel/runtime/helpers/createClass.js":
/*!************************************************************!*\
  !*** ./node_modules/@babel/runtime/helpers/createClass.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function _defineProperties(target, props) {
  for (var i = 0; i < props.length; i++) {
    var descriptor = props[i];
    descriptor.enumerable = descriptor.enumerable || false;
    descriptor.configurable = true;
    if ("value" in descriptor) descriptor.writable = true;
    Object.defineProperty(target, descriptor.key, descriptor);
  }
}

function _createClass(Constructor, protoProps, staticProps) {
  if (protoProps) _defineProperties(Constructor.prototype, protoProps);
  if (staticProps) _defineProperties(Constructor, staticProps);
  return Constructor;
}

module.exports = _createClass;
module.exports["default"] = module.exports, module.exports.__esModule = true;

/***/ }),

/***/ "./node_modules/banana-i18n/dist/banana-i18n.js":
/*!******************************************************!*\
  !*** ./node_modules/banana-i18n/dist/banana-i18n.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

!function(e,u){ true?module.exports=u():undefined}(this,(function(){return function(e){var u={};function d(t){if(u[t])return u[t].exports;var r=u[t]={i:t,l:!1,exports:{}};return e[t].call(r.exports,r,r.exports,d),r.l=!0,r.exports}return d.m=e,d.c=u,d.d=function(e,u,t){d.o(e,u)||Object.defineProperty(e,u,{enumerable:!0,get:t})},d.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},d.t=function(e,u){if(1&u&&(e=d(e)),8&u)return e;if(4&u&&"object"==typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(d.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&u&&"string"!=typeof e)for(var r in e)d.d(t,r,function(u){return e[u]}.bind(null,r));return t},d.n=function(e){var u=e&&e.__esModule?function(){return e.default}:function(){return e};return d.d(u,"a",u),u},d.o=function(e,u){return Object.prototype.hasOwnProperty.call(e,u)},d.p="",d(d.s=2)}([function(e){e.exports=JSON.parse('{"ar":"٠١٢٣٤٥٦٧٨٩","fa":"۰۱۲۳۴۵۶۷۸۹","ml":"൦൧൨൩൪൫൬൭൮൯","kn":"೦೧೨೩೪೫೬೭೮೯","lo":"໐໑໒໓໔໕໖໗໘໙","or":"୦୧୨୩୪୫୬୭୮୯","kh":"០១២៣៤៥៦៧៨៩","nqo":"߀߁߂߃߄߅߆߇߈߉","pa":"੦੧੨੩੪੫੬੭੮੯","gu":"૦૧૨૩૪૫૬૭૮૯","hi":"०१२३४५६७८९","my":"၀၁၂၃၄၅၆၇၈၉","ta":"௦௧௨௩௪௫௬௭௮௯","te":"౦౧౨౩౪౫౬౭౮౯","th":"๐๑๒๓๔๕๖๗๘๙","bo":"༠༡༢༣༤༥༦༧༨༩"}')},function(e){e.exports=JSON.parse('{"ab":["ru"],"ace":["id"],"aln":["sq"],"als":["gsw","de"],"an":["es"],"anp":["hi"],"arn":["es"],"arz":["ar"],"av":["ru"],"ay":["es"],"ba":["ru"],"bar":["de"],"bat-smg":["sgs","lt"],"bcc":["fa"],"be-x-old":["be-tarask"],"bh":["bho"],"bjn":["id"],"bm":["fr"],"bpy":["bn"],"bqi":["fa"],"bug":["id"],"cbk-zam":["es"],"ce":["ru"],"crh":["crh-latn"],"crh-cyrl":["ru"],"csb":["pl"],"cv":["ru"],"de-at":["de"],"de-ch":["de"],"de-formal":["de"],"dsb":["de"],"dtp":["ms"],"egl":["it"],"eml":["it"],"ff":["fr"],"fit":["fi"],"fiu-vro":["vro","et"],"frc":["fr"],"frp":["fr"],"frr":["de"],"fur":["it"],"gag":["tr"],"gan":["gan-hant","zh-hant","zh-hans"],"gan-hans":["zh-hans"],"gan-hant":["zh-hant","zh-hans"],"gl":["pt"],"glk":["fa"],"gn":["es"],"gsw":["de"],"hif":["hif-latn"],"hsb":["de"],"ht":["fr"],"ii":["zh-cn","zh-hans"],"inh":["ru"],"iu":["ike-cans"],"jut":["da"],"jv":["id"],"kaa":["kk-latn","kk-cyrl"],"kbd":["kbd-cyrl"],"khw":["ur"],"kiu":["tr"],"kk":["kk-cyrl"],"kk-arab":["kk-cyrl"],"kk-latn":["kk-cyrl"],"kk-cn":["kk-arab","kk-cyrl"],"kk-kz":["kk-cyrl"],"kk-tr":["kk-latn","kk-cyrl"],"kl":["da"],"ko-kp":["ko"],"koi":["ru"],"krc":["ru"],"ks":["ks-arab"],"ksh":["de"],"ku":["ku-latn"],"ku-arab":["ckb"],"kv":["ru"],"lad":["es"],"lb":["de"],"lbe":["ru"],"lez":["ru"],"li":["nl"],"lij":["it"],"liv":["et"],"lmo":["it"],"ln":["fr"],"ltg":["lv"],"lzz":["tr"],"mai":["hi"],"map-bms":["jv","id"],"mg":["fr"],"mhr":["ru"],"min":["id"],"mo":["ro"],"mrj":["ru"],"mwl":["pt"],"myv":["ru"],"mzn":["fa"],"nah":["es"],"nap":["it"],"nds":["de"],"nds-nl":["nl"],"nl-informal":["nl"],"no":["nb"],"os":["ru"],"pcd":["fr"],"pdc":["de"],"pdt":["de"],"pfl":["de"],"pms":["it"],"pt":["pt-br"],"pt-br":["pt"],"qu":["es"],"qug":["qu","es"],"rgn":["it"],"rmy":["ro"],"roa-rup":["rup"],"rue":["uk","ru"],"ruq":["ruq-latn","ro"],"ruq-cyrl":["mk"],"ruq-latn":["ro"],"sa":["hi"],"sah":["ru"],"scn":["it"],"sg":["fr"],"sgs":["lt"],"sli":["de"],"sr":["sr-ec"],"srn":["nl"],"stq":["de"],"su":["id"],"szl":["pl"],"tcy":["kn"],"tg":["tg-cyrl"],"tt":["tt-cyrl","ru"],"tt-cyrl":["ru"],"ty":["fr"],"udm":["ru"],"ug":["ug-arab"],"uk":["ru"],"vec":["it"],"vep":["et"],"vls":["nl"],"vmf":["de"],"vot":["fi"],"vro":["et"],"wa":["fr"],"wo":["fr"],"wuu":["zh-hans"],"xal":["ru"],"xmf":["ka"],"yi":["he"],"za":["zh-hans"],"zea":["nl"],"zh":["zh-hans"],"zh-classical":["lzh"],"zh-cn":["zh-hans"],"zh-hant":["zh-hans"],"zh-hk":["zh-hant","zh-hans"],"zh-min-nan":["nan"],"zh-mo":["zh-hk","zh-hant","zh-hans"],"zh-my":["zh-sg","zh-hans"],"zh-sg":["zh-hans"],"zh-tw":["zh-hant","zh-hans"],"zh-yue":["yue"]}')},function(e,u,d){"use strict";d.r(u),d.d(u,"default",(function(){return o}));var t=d(0);class r{constructor(e){this.locale=e}convertPlural(e,u){var d=new RegExp("\\d+=","i");if(!u||0===u.length)return"";for(let t=0;t<u.length;t++){let r=u[t];if(d.test(r)){if(parseInt(r.slice(0,r.indexOf("=")),10)===e)return r.slice(r.indexOf("=")+1);u[t]=void 0}}u=u.filter(e=>!!e);let t=this.getPluralForm(e,this.locale);return t=Math.min(t,u.length-1),u[t]}getPluralForm(e,u){const d=new Intl.PluralRules(u),t=d.resolvedOptions().pluralCategories,r=d.select(e);return["zero","one","two","few","many","other"].filter(e=>t.includes(e)).indexOf(r)}convertNumber(e,u){let d=this.digitTransformTable(this.locale),t=String(e),r="";if(!d)return e;if(u){if(parseFloat(e,10)===e)return e;let u=[];for(let e in d)u[d[e]]=e;d=u}for(let e=0;e<t.length;e++)d[t[e]]?r+=d[t[e]]:r+=t[e];return u?parseFloat(r,10):r}convertGrammar(e,u){return e}gender(e,u){if(!u||0===u.length)return"";for(;u.length<2;)u.push(u[u.length-1]);return"male"===e?u[0]:"female"===e?u[1]:3===u.length?u[2]:u[0]}digitTransformTable(e){return!!t[e]&&t[e].split("")}}var n={bs:class extends r{convertGrammar(e,u){switch(u){case"instrumental":e="s "+e;break;case"lokativ":e="o "+e}return e}},default:r,dsb:class extends r{convertGrammar(e,u){switch(u){case"instrumental":e="z "+e;break;case"lokatiw":e="wo "+e}return e}},fi:class extends r{convertGrammar(e,u){let d=e.match(/[aou][^äöy]*$/i),t=e;switch(e.match(/wiki$/i)&&(d=!1),e.match(/[bcdfghjklmnpqrstvwxz]$/i)&&(e+="i"),u){case"genitive":e+="n";break;case"elative":e+=d?"sta":"stä";break;case"partitive":e+=d?"a":"ä";break;case"illative":e+=e.slice(-1)+"n";break;case"inessive":e+=d?"ssa":"ssä";break;default:e=t}return e}},ga:class extends r{convertGrammar(e,u){if("ainmlae"===u)switch(e){case"an Domhnach":e="Dé Domhnaigh";break;case"an Luan":e="Dé Luain";break;case"an Mháirt":e="Dé Mháirt";break;case"an Chéadaoin":e="Dé Chéadaoin";break;case"an Déardaoin":e="Déardaoin";break;case"an Aoine":e="Dé hAoine";break;case"an Satharn":e="Dé Sathairn"}return e}},he:class extends r{convertGrammar(e,u){switch(u){case"prefixed":case"תחילית":"ו"===e.slice(0,1)&&"וו"!==e.slice(0,2)&&(e="ו"+e),"ה"===e.slice(0,1)&&(e=e.slice(1)),(e.slice(0,1)<"א"||e.slice(0,1)>"ת")&&(e="־"+e)}return e}},hsb:class extends r{convertGrammar(e,u){switch(u){case"instrumental":e="z "+e;break;case"lokatiw":e="wo "+e}return e}},hu:class extends r{convertGrammar(e,u){switch(u){case"rol":e+="ról";break;case"ba":e+="ba";break;case"k":e+="k"}return e}},hy:class extends r{convertGrammar(e,u){return"genitive"===u&&("ա"===e.slice(-1)?e=e.slice(0,-1)+"այի":"ո"===e.slice(-1)?e=e.slice(0,-1)+"ոյի":"գիրք"===e.slice(-4)?e=e.slice(0,-4)+"գրքի":e+="ի"),e}},la:class extends r{convertGrammar(e,u){switch(u){case"genitive":e=(e=(e=(e=(e=(e=(e=(e=(e=e.replace(/u[ms]$/i,"i")).replace(/ommunia$/i,"ommunium")).replace(/a$/i,"ae")).replace(/libri$/i,"librorum")).replace(/nuntii$/i,"nuntiorum")).replace(/tio$/i,"tionis")).replace(/ns$/i,"ntis")).replace(/as$/i,"atis")).replace(/es$/i,"ei");break;case"accusative":e=(e=(e=(e=(e=(e=(e=(e=(e=e.replace(/u[ms]$/i,"um")).replace(/ommunia$/i,"am")).replace(/a$/i,"ommunia")).replace(/libri$/i,"libros")).replace(/nuntii$/i,"nuntios")).replace(/tio$/i,"tionem")).replace(/ns$/i,"ntem")).replace(/as$/i,"atem")).replace(/es$/i,"em");break;case"ablative":e=(e=(e=(e=(e=(e=(e=(e=(e=e.replace(/u[ms]$/i,"o")).replace(/ommunia$/i,"ommunibus")).replace(/a$/i,"a")).replace(/libri$/i,"libris")).replace(/nuntii$/i,"nuntiis")).replace(/tio$/i,"tione")).replace(/ns$/i,"nte")).replace(/as$/i,"ate")).replace(/es$/i,"e")}return e}},os:class extends r{convertGrammar(e,u){var d,t,r,n;switch(d="мæ",t="",r="",n="",e.match(/тæ$/i)?(e=e.slice(0,-1),d="æм"):e.match(/[аæеёиоыэюя]$/i)?t="й":e.match(/у$/i)?e.slice(-2,-1).match(/[аæеёиоыэюя]$/i)||(t="й"):e.match(/[бвгджзйклмнопрстфхцчшщьъ]$/i)||(r="-"),u){case"genitive":n=r+t+"ы";break;case"dative":n=r+t+"æн";break;case"allative":n=r+d;break;case"ablative":n="й"===t?r+t+"æ":r+t+"æй";break;case"superessive":n=r+t+"ыл";break;case"equative":n=r+t+"ау";break;case"comitative":n=r+"имæ"}return e+n}},ru:class extends r{convertGrammar(e,u){return"genitive"===u&&("ь"===e.slice(-1)?e=e.slice(0,-1)+"я":"ия"===e.slice(-2)?e=e.slice(0,-2)+"ии":"ка"===e.slice(-2)?e=e.slice(0,-2)+"ки":"ти"===e.slice(-2)?e=e.slice(0,-2)+"тей":"ды"===e.slice(-2)?e=e.slice(0,-2)+"дов":"ник"===e.slice(-3)&&(e=e.slice(0,-3)+"ника")),e}},sl:class extends r{convertGrammar(e,u){switch(u){case"mestnik":e="o "+e;break;case"orodnik":e="z "+e}return e}},uk:class extends r{convertGrammar(e,u){switch(u){case"genitive":"ь"===e.slice(-1)?e=e.slice(0,-1)+"я":"ія"===e.slice(-2)?e=e.slice(0,-2)+"ії":"ка"===e.slice(-2)?e=e.slice(0,-2)+"ки":"ти"===e.slice(-2)?e=e.slice(0,-2)+"тей":"ды"===e.slice(-2)?e=e.slice(0,-2)+"дов":"ник"===e.slice(-3)&&(e=e.slice(0,-3)+"ника");break;case"accusative":"ія"===e.slice(-2)&&(e=e.slice(0,-2)+"ію")}return e}}};const a=new RegExp("(?:([A-Za-zªµºÀ-ÖØ-öø-ʸʻ-ˁːˑˠ-ˤˮͰ-ͳͶͷͺ-ͽͿΆΈ-ΊΌΎ-ΡΣ-ϵϷ-҂Ҋ-ԯԱ-Ֆՙ-՟ա-և։ः-हऻऽ-ीॉ-ौॎ-ॐक़-ॡ।-ঀংঃঅ-ঌএঐও-নপ-রলশ-হঽ-ীেৈোৌৎৗড়ঢ়য়-ৡ০-ৱ৴-৺ਃਅ-ਊਏਐਓ-ਨਪ-ਰਲਲ਼ਵਸ਼ਸਹਾ-ੀਖ਼-ੜਫ਼੦-੯ੲ-ੴઃઅ-ઍએ-ઑઓ-નપ-રલળવ-હઽ-ીૉોૌૐૠૡ૦-૰ૹଂଃଅ-ଌଏଐଓ-ନପ-ରଲଳଵ-ହଽାୀେୈୋୌୗଡ଼ଢ଼ୟ-ୡ୦-୷ஃஅ-ஊஎ-ஐஒ-கஙசஜஞடணதந-பம-ஹாிுூெ-ைொ-ௌௐௗ௦-௲ఁ-ఃఅ-ఌఎ-ఐఒ-నప-హఽు-ౄౘ-ౚౠౡ౦-౯౿ಂಃಅ-ಌಎ-ಐಒ-ನಪ-ಳವ-ಹಽ-ೄೆ-ೈೊೋೕೖೞೠೡ೦-೯ೱೲംഃഅ-ഌഎ-ഐഒ-ഺഽ-ീെ-ൈൊ-ൌൎൗൟ-ൡ൦-൵൹-ൿංඃඅ-ඖක-නඳ-රලව-ෆා-ෑෘ-ෟ෦-෯ෲ-෴ก-ะาำเ-ๆ๏-๛ກຂຄງຈຊຍດ-ທນ-ຟມ-ຣລວສຫອ-ະາຳຽເ-ໄໆ໐-໙ໜ-ໟༀ-༗༚-༴༶༸༾-ཇཉ-ཬཿ྅ྈ-ྌ྾-࿅࿇-࿌࿎-࿚က-ာေးျြဿ-ၗၚ-ၝၡ-ၰၵ-ႁႃႄႇ-ႌႎ-ႜ႞-ჅჇჍა-ቈቊ-ቍቐ-ቖቘቚ-ቝበ-ኈኊ-ኍነ-ኰኲ-ኵኸ-ኾዀዂ-ዅወ-ዖዘ-ጐጒ-ጕጘ-ፚ፠-፼ᎀ-ᎏᎠ-Ᏽᏸ-ᏽᐁ-ᙿᚁ-ᚚᚠ-ᛸᜀ-ᜌᜎ-ᜑᜠ-ᜱ᜵᜶ᝀ-ᝑᝠ-ᝬᝮ-ᝰក-ឳាើ-ៅះៈ។-៚ៜ០-៩᠐-᠙ᠠ-ᡷᢀ-ᢨᢪᢰ-ᣵᤀ-ᤞᤣ-ᤦᤩ-ᤫᤰᤱᤳ-ᤸ᥆-ᥭᥰ-ᥴᦀ-ᦫᦰ-ᧉ᧐-᧚ᨀ-ᨖᨙᨚ᨞-ᩕᩗᩡᩣᩤᩭ-ᩲ᪀-᪉᪐-᪙᪠-᪭ᬄ-ᬳᬵᬻᬽ-ᭁᭃ-ᭋ᭐-᭪᭴-᭼ᮂ-ᮡᮦᮧ᮪ᮮ-ᯥᯧᯪ-ᯬᯮ᯲᯳᯼-ᰫᰴᰵ᰻-᱉ᱍ-᱿᳀-᳇᳓᳡ᳩ-ᳬᳮ-ᳳᳵᳶᴀ-ᶿḀ-ἕἘ-Ἕἠ-ὅὈ-Ὅὐ-ὗὙὛὝὟ-ώᾀ-ᾴᾶ-ᾼιῂ-ῄῆ-ῌῐ-ΐῖ-Ίῠ-Ῥῲ-ῴῶ-ῼ‎ⁱⁿₐ-ₜℂℇℊ-ℓℕℙ-ℝℤΩℨK-ℭℯ-ℹℼ-ℿⅅ-ⅉⅎ⅏Ⅰ-ↈ⌶-⍺⎕⒜-ⓩ⚬⠀-⣿Ⰰ-Ⱞⰰ-ⱞⱠ-ⳤⳫ-ⳮⳲⳳⴀ-ⴥⴧⴭⴰ-ⵧⵯ⵰ⶀ-ⶖⶠ-ⶦⶨ-ⶮⶰ-ⶶⶸ-ⶾⷀ-ⷆⷈ-ⷎⷐ-ⷖⷘ-ⷞ々-〇〡-〩〮〯〱-〵〸-〼ぁ-ゖゝ-ゟァ-ヺー-ヿㄅ-ㄭㄱ-ㆎ㆐-ㆺㇰ-㈜㈠-㉏㉠-㉻㉿-㊰㋀-㋋㋐-㋾㌀-㍶㍻-㏝㏠-㏾㐀-䶵一-鿕ꀀ-ꒌꓐ-ꘌꘐ-ꘫꙀ-ꙮꚀ-ꚝꚠ-ꛯ꛲-꛷Ꜣ-ꞇ꞉-ꞭꞰ-ꞷꟷ-ꠁꠃ-ꠅꠇ-ꠊꠌ-ꠤꠧ꠰-꠷ꡀ-ꡳꢀ-ꣃ꣎-꣙ꣲ-ꣽ꤀-ꤥ꤮-ꥆꥒ꥓꥟-ꥼꦃ-ꦲꦴꦵꦺꦻꦽ-꧍ꧏ-꧙꧞-ꧤꧦ-ꧾꨀ-ꨨꨯꨰꨳꨴꩀ-ꩂꩄ-ꩋꩍ꩐-꩙꩜-ꩻꩽ-ꪯꪱꪵꪶꪹ-ꪽꫀꫂꫛ-ꫫꫮ-ꫵꬁ-ꬆꬉ-ꬎꬑ-ꬖꬠ-ꬦꬨ-ꬮꬰ-ꭥꭰ-ꯤꯦꯧꯩ-꯬꯰-꯹가-힣ힰ-ퟆퟋ-ퟻ-舘並-龎ﬀ-ﬆﬓ-ﬗＡ-Ｚａ-ｚｦ-ﾾￂ-ￇￊ-ￏￒ-ￗￚ-ￜ]|\ud800[\udc00-\udc0b]|\ud800[\udc0d-\udc26]|\ud800[\udc28-\udc3a]|𐀼|𐀽|\ud800[\udc3f-\udc4d]|\ud800[\udc50-\udc5d]|\ud800[\udc80-\udcfa]|𐄀|𐄂|\ud800[\udd07-\udd33]|\ud800[\udd37-\udd3f]|\ud800[\uddd0-\uddfc]|\ud800[\ude80-\ude9c]|\ud800[\udea0-\uded0]|\ud800[\udf00-\udf23]|\ud800[\udf30-\udf4a]|\ud800[\udf50-\udf75]|\ud800[\udf80-\udf9d]|\ud800[\udf9f-\udfc3]|\ud800[\udfc8-\udfd5]|\ud801[\udc00-\udc9d]|\ud801[\udca0-\udca9]|\ud801[\udd00-\udd27]|\ud801[\udd30-\udd63]|𐕯|\ud801[\ude00-\udf36]|\ud801[\udf40-\udf55]|\ud801[\udf60-\udf67]|𑀀|\ud804[\udc02-\udc37]|\ud804[\udc47-\udc4d]|\ud804[\udc66-\udc6f]|\ud804[\udc82-\udcb2]|𑂷|𑂸|\ud804[\udcbb-\udcc1]|\ud804[\udcd0-\udce8]|\ud804[\udcf0-\udcf9]|\ud804[\udd03-\udd26]|𑄬|\ud804[\udd36-\udd43]|\ud804[\udd50-\udd72]|\ud804[\udd74-\udd76]|\ud804[\udd82-\uddb5]|\ud804[\uddbf-\uddc9]|𑇍|\ud804[\uddd0-\udddf]|\ud804[\udde1-\uddf4]|\ud804[\ude00-\ude11]|\ud804[\ude13-\ude2e]|𑈲|𑈳|𑈵|\ud804[\ude38-\ude3d]|\ud804[\ude80-\ude86]|𑊈|\ud804[\ude8a-\ude8d]|\ud804[\ude8f-\ude9d]|\ud804[\ude9f-\udea9]|\ud804[\udeb0-\udede]|\ud804[\udee0-\udee2]|\ud804[\udef0-\udef9]|𑌂|𑌃|\ud804[\udf05-\udf0c]|𑌏|𑌐|\ud804[\udf13-\udf28]|\ud804[\udf2a-\udf30]|𑌲|𑌳|\ud804[\udf35-\udf39]|\ud804[\udf3d-\udf3f]|\ud804[\udf41-\udf44]|𑍇|𑍈|\ud804[\udf4b-\udf4d]|𑍐|𑍗|\ud804[\udf5d-\udf63]|\ud805[\udc80-\udcb2]|𑒹|\ud805[\udcbb-\udcbe]|𑓁|\ud805[\udcc4-\udcc7]|\ud805[\udcd0-\udcd9]|\ud805[\udd80-\uddb1]|\ud805[\uddb8-\uddbb]|𑖾|\ud805[\uddc1-\udddb]|\ud805[\ude00-\ude32]|𑘻|𑘼|𑘾|\ud805[\ude41-\ude44]|\ud805[\ude50-\ude59]|\ud805[\ude80-\udeaa]|𑚬|𑚮|𑚯|𑚶|\ud805[\udec0-\udec9]|\ud805[\udf00-\udf19]|𑜠|𑜡|𑜦|\ud805[\udf30-\udf3f]|\ud806[\udca0-\udcf2]|𑣿|\ud806[\udec0-\udef8]|\ud808[\udc00-\udf99]|\ud809[\udc00-\udc6e]|\ud809[\udc70-\udc74]|\ud809[\udc80-\udd43]|\ud80c[\udc00-\udfff]|\ud80d[\udc00-\udc2e]|\ud811[\udc00-\ude46]|\ud81a[\udc00-\ude38]|\ud81a[\ude40-\ude5e]|\ud81a[\ude60-\ude69]|𖩮|𖩯|\ud81a[\uded0-\udeed]|𖫵|\ud81a[\udf00-\udf2f]|\ud81a[\udf37-\udf45]|\ud81a[\udf50-\udf59]|\ud81a[\udf5b-\udf61]|\ud81a[\udf63-\udf77]|\ud81a[\udf7d-\udf8f]|\ud81b[\udf00-\udf44]|\ud81b[\udf50-\udf7e]|\ud81b[\udf93-\udf9f]|𛀀|𛀁|\ud82f[\udc00-\udc6a]|\ud82f[\udc70-\udc7c]|\ud82f[\udc80-\udc88]|\ud82f[\udc90-\udc99]|𛲜|𛲟|\ud834[\udc00-\udcf5]|\ud834[\udd00-\udd26]|\ud834[\udd29-\udd66]|\ud834[\udd6a-\udd72]|𝆃|𝆄|\ud834[\udd8c-\udda9]|\ud834[\uddae-\udde8]|\ud834[\udf60-\udf71]|\ud835[\udc00-\udc54]|\ud835[\udc56-\udc9c]|𝒞|𝒟|𝒢|𝒥|𝒦|\ud835[\udca9-\udcac]|\ud835[\udcae-\udcb9]|𝒻|\ud835[\udcbd-\udcc3]|\ud835[\udcc5-\udd05]|\ud835[\udd07-\udd0a]|\ud835[\udd0d-\udd14]|\ud835[\udd16-\udd1c]|\ud835[\udd1e-\udd39]|\ud835[\udd3b-\udd3e]|\ud835[\udd40-\udd44]|𝕆|\ud835[\udd4a-\udd50]|\ud835[\udd52-\udea5]|\ud835[\udea8-\udeda]|\ud835[\udedc-\udf14]|\ud835[\udf16-\udf4e]|\ud835[\udf50-\udf88]|\ud835[\udf8a-\udfc2]|\ud835[\udfc4-\udfcb]|\ud836[\udc00-\uddff]|\ud836[\ude37-\ude3a]|\ud836[\ude6d-\ude74]|\ud836[\ude76-\ude83]|\ud836[\ude85-\ude8b]|\ud83c[\udd10-\udd2e]|\ud83c[\udd30-\udd69]|\ud83c[\udd70-\udd9a]|\ud83c[\udde6-\ude02]|\ud83c[\ude10-\ude3a]|\ud83c[\ude40-\ude48]|🉐|🉑|[\ud840-\ud868][\udc00-\udfff]|\ud869[\udc00-\uded6]|\ud869[\udf00-\udfff]|[\ud86a-\ud86c][\udc00-\udfff]|\ud86d[\udc00-\udf34]|\ud86d[\udf40-\udfff]|\ud86e[\udc00-\udc1d]|\ud86e[\udc20-\udfff]|[\ud86f-\ud872][\udc00-\udfff]|\ud873[\udc00-\udea1]|\ud87e[\udc00-\ude1d]|[\udb80-\udbbe][\udc00-\udfff]|\udbbf[\udc00-\udffd]|[\udbc0-\udbfe][\udc00-\udfff]|\udbff[\udc00-\udffd])|([֐־׀׃׆׈-׿߀-ߪߴߵߺ-ࠕࠚࠤࠨ࠮-ࡘ࡜-࢟‏יִײַ-ﬨשׁ-ﭏ؈؋؍؛-ي٭-ٯٱ-ەۥۦۮۯۺ-ܐܒ-ܯ݋-ޥޱ-޿ࢠ-࣢ﭐ-ﴽ﵀-﷏ﷰ-﷼﷾﷿ﹰ-﻾]|\ud802[\udc00-\udd1e]|\ud802[\udd20-\ude00]|𐨄|\ud802[\ude07-\ude0b]|\ud802[\ude10-\ude37]|\ud802[\ude3b-\ude3e]|\ud802[\ude40-\udee4]|\ud802[\udee7-\udf38]|\ud802[\udf40-\udfff]|\ud803[\udc00-\ude5f]|\ud803[\ude7f-\udfff]|\ud83a[\udc00-\udccf]|\ud83a[\udcd7-\udfff]|\ud83b[\udc00-\uddff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\udf00-\udfff]|\ud83b[\ude00-\udeef]|\ud83b[\udef2-\udeff]))");var c=class{constructor(e){this.locale=e,this.language=new(n[e]||n.default)(e)}emit(e,u){let d,t,r;switch(typeof e){case"string":case"number":d=e;break;case"object":if(t=e.slice(1).map(e=>this.emit(e,u)),r=e[0].toLowerCase(),"function"!=typeof this[r])throw new Error('unknown operation "'+r+'"');d=this[r](t,u);break;case"undefined":d="";break;default:throw new Error("unexpected type in AST: "+typeof e)}return d}concat(e){let u="";return e.forEach(e=>{u+=e}),u}replace(e,u){let d=parseInt(e[0],10);return d<u.length?u[d]:"$"+(d+1)}plural(e){let u=parseFloat(this.language.convertNumber(e[0],10)),d=e.slice(1);return d.length?this.language.convertPlural(u,d):""}gender(e){let u=e[0],d=e.slice(1);return this.language.gender(u,d)}grammar(e){let u=e[0],d=e[1];return d&&u&&this.language.convertGrammar(d,u)}wikilink(e){let u,d=e[0];":"===d.charAt(0)&&(d=d.slice(1));const t="./"+d;return u=1===e.length?d:e[1],`<a href="${t}" title="${d}">${u}</a>`}extlink(e){if(2!==e.length)throw new Error("Expected two items in the node");return`<a href="${e[0]}">${e[1]}</a>`}bidi(e){var u=function(e){var u=e.match(a);if(!u)return null;if(void 0===u[2])return"ltr";return"rtl"}(e[0]);return"ltr"===u?"‪"+e[0]+"‬":"rtl"===u?"‫"+e[0]+"‬":e[0]}};function l(e){let u,d,t,r,n,a,c,l=0;function i(e){return()=>{for(let u=0;u<e.length;u++){let d=e[u]();if(null!==d)return d}return null}}function s(e){let u=l,d=[];for(let t=0;t<e.length;t++){let r=e[t]();if(null===r)return l=u,null;d.push(r)}return d}function f(e,u){return()=>{let d=l,t=[],r=u();for(;null!==r;)t.push(r),r=u();return t.length<e?(l=d,null):t}}function o(u){let d=u.length;return()=>{let t=null;return e.slice(l,l+d)===u&&(t=u,l+=d),t}}function h(u){return()=>{let d=e.slice(l).match(u);return null===d?null:(l+=d[0].length,d[0])}}const b=h(/^\s+/),m=o("|"),p=o(":"),k=o("\\"),g=h(/^./),v=o("$"),w=h(/^\d+/),z=h(/^[^{}[\]$\\]/),$=h(/^[^{}[\]$\\|]/),y=h(/^[^{}[\]$\s]/);const x=i([j,y]);function j(){let e=s([k,g]);return null===e?null:e[1]}function O(){let e=s([v,w]);return null===e?null:["REPLACE",parseInt(e[1],10)-1]}var M,C;function S(){let e=s([m,f(0,a)]);if(null===e)return null;let u=e[1];return u.length>1?["CONCAT"].concat(u):u[0]}function A(){let e=s([r,p,O]);return null===e?null:[e[0],e[2]]}function G(){let e=s([r,p,a]);return null===e?null:[e[0],e[2]]}i([j,y]),u=i([j,$]),d=i([j,z]),M=h(/^[ !"$&'()*,./0-9;=?@A-Z^_`a-z~\x80-\xFF+-]+/),C=function(e){return e.toString()},r=()=>{let e=M();return null===e?null:C(e)},t=i([function(){let e=s([i([A,G]),f(0,S)]);return null===e?null:e[0].concat(e[1])},function(){let e=s([r,f(0,S)]);return null===e?null:[e[0]].concat(e[1])}]);const T=o("{{"),E=o("}}"),P=o("[["),q=o("]]"),N=o("["),F=o("]");function I(){let e=s([T,t,E]);return null===e?null:e[1]}const D=i([function(){var e=s([f(1,a),m,f(1,n)]);return null===e?null:[["CONCAT"].concat(e[0]),["CONCAT"].concat(e[2])]},function(){var e=s([f(1,a)]);return null===e?null:[["CONCAT"].concat(e[0])]}]);function L(){let e=null;const u=s([P,D,q]);if(null!==u){const d=u[1];e=["WIKILINK"].concat(d)}return e}function _(){let e=null;const u=s([N,f(1,R),b,f(1,n),F]);if(null!==u){e=["EXTLINK",1===u[1].length?u[1][0]:["CONCAT"].concat(u[1]),["CONCAT"].concat(u[3])]}return e}const R=i([I,O,L,_,function(){const e=f(1,x)();return null===e?null:e.join("")}]);if(n=i([I,O,L,_,function(){const e=f(1,d)();return null===e?null:e.join("")}]),a=i([I,O,function(){let e=f(1,u)();return null===e?null:e.join("")}]),c=function(){let e=f(0,n)();return null===e?null:["CONCAT"].concat(e)}(),null===c||l!==e.length)throw new Error("Parse error at position "+l.toString()+" in input: "+e);return c}class i{constructor(e){this.locale=e,this.emitter=new c(this.locale)}parse(e,u){if(e.includes("{{")||e.includes("[")){let d=new l(e);return this.emitter.emit(d,u)}return this.simpleParse(e,u)}simpleParse(e,u){return e.replace(/\$(\d+)/g,(e,d)=>{let t=parseInt(d,10)-1;return void 0!==u[t]?u[t]:"$"+d})}}class s{constructor(e){this.sourceMap=new Map}load(e,u){if("object"!=typeof e)throw new Error("Invalid message source. Must be an object");if(u){if(!/^[a-zA-Z0-9-]+$/.test(u))throw new Error("Invalid locale "+u);for(let d in e)if(0!==d.indexOf("@")){if("object"==typeof e[d])return this.load(e);if("string"!=typeof e[d])throw new Error(`Invalid message for message ${d} in ${u} locale.`);break}this.sourceMap.has(u)?this.sourceMap.set(u,Object.assign(this.sourceMap.get(u),e)):this.sourceMap.set(u,e)}else for(u in e)this.load(e[u],u)}getMessage(e,u){let d=this.sourceMap.get(u);return d?d[e]:null}hasLocale(e){return this.sourceMap.has(e)}}var f=d(1);class o{constructor(e,u){u=u||{},this.locale=e,this.parser=new i(this.locale),this.messageStore=new s,u.messages&&this.load(u.messages,this.locale),this.finalFallback=u.finalFallback||"en"}load(e,u){return this.messageStore.load(e,u||this.locale)}i18n(e,...u){return this.parser.parse(this.getMessage(e),u)}setLocale(e){this.locale=e,this.parser=new i(this.locale)}getFallbackLocales(){return[...f[this.locale]||[],this.finalFallback]}getMessage(e){let u=this.locale,d=0;const t=this.getFallbackLocales(this.locale);for(;u;){let r=u.split("-"),n=r.length;do{let u=r.slice(0,n).join("-"),d=this.messageStore.getMessage(e,u);if(d)return d;n--}while(n);u=t[d],d++}return e}}}]).default}));
//# sourceMappingURL=banana-i18n.js.map

/***/ }),

/***/ "./src/pcs/c1/DemoMode.js":
/*!********************************!*\
  !*** ./src/pcs/c1/DemoMode.js ***!
  \********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _Footer__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Footer */ "./src/pcs/c1/Footer.js");

/**
 * Add the page footer
 * @param {!URL} url
 * @return {void}
 */

var addFooter = function addFooter(url) {
  var parts = url.pathname.split('v1/page/mobile-html/');
  var baseURL = "".concat(url.protocol, "//").concat(url.host).concat(parts[0], "v1");
  var title = parts[1];
  _Footer__WEBPACK_IMPORTED_MODULE_0__["default"].add({
    version: '2',
    title: title,
    menu: {
      items: [_Footer__WEBPACK_IMPORTED_MODULE_0__["default"].MenuItemType.languages, _Footer__WEBPACK_IMPORTED_MODULE_0__["default"].MenuItemType.lastEdited, _Footer__WEBPACK_IMPORTED_MODULE_0__["default"].MenuItemType.pageIssues, _Footer__WEBPACK_IMPORTED_MODULE_0__["default"].MenuItemType.disambiguation, _Footer__WEBPACK_IMPORTED_MODULE_0__["default"].MenuItemType.talkPage],
      fragment: 'pcs-menu',
      editedDaysAgo: 3 // hard-coded for demo for now

    },
    readMore: {
      itemCount: 3,
      baseURL: baseURL,
      fragment: 'pcs-read-more'
    }
  });
};

/* harmony default export */ __webpack_exports__["default"] = ({
  addFooter: addFooter
});

/***/ }),

/***/ "./src/pcs/c1/Footer.js":
/*!******************************!*\
  !*** ./src/pcs/c1/Footer.js ***!
  \******************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var banana_i18n__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! banana-i18n */ "./node_modules/banana-i18n/dist/banana-i18n.js");
/* harmony import */ var banana_i18n__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(banana_i18n__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _transform_FooterContainer__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../transform/FooterContainer */ "./src/transform/FooterContainer.js");
/* harmony import */ var _transform_FooterLegal__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../transform/FooterLegal */ "./src/transform/FooterLegal.js");
/* harmony import */ var _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../transform/FooterMenu */ "./src/transform/FooterMenu.js");
/* harmony import */ var _transform_FooterReadMore__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../transform/FooterReadMore */ "./src/transform/FooterReadMore.js");





var handlers;
/**
 * Sets up the interaction handlers for the footer.
 * @param {!{}} newHandlers an object with handlers for {
 *   footerItemSelected, saveOtherPage, viewLicense, viewInBrowser
 * }
 * @return {void}
 */

var _connectHandlers = function _connectHandlers(newHandlers) {
  handlers = newHandlers;
};
/**
 * Note: T249541: pluralization with banana-i18 1.1.2 doesn't work if the zero case comes first
 * in the string.
 * @param {!Banana} banana
 * @param {?number} editedDaysAgo
 * @return {string}
 */


var _getPageLastEditedString = function _getPageLastEditedString(banana, editedDaysAgo) {
  if (editedDaysAgo === undefined || editedDaysAgo < 0) {
    return '';
  }

  try {
    var result = banana.i18n('page-last-edited', editedDaysAgo);

    if (result && result.includes('undefined')) {
      result = '';
    }

    return result;
  } catch (err) {
    return '';
  }
};

var _getArticleTitleFromLocation = function _getArticleTitleFromLocation(location) {
  return location.pathname.split('/page/mobile-html/')[1];
};
/**
 * Adds footer to the end of the document
 * @param {!Object.<any>} params parameters as follows
 *   {!map} menu
 *       {!array<string>} items menu items to add
 *   {!map} readMore
 *       {!number} itemCount number of read more items to add
 *       {!string} baseURL base url for RESTBase to fetch read more
 * @return {void}
 */


var add = function add(params) {
  var _params$menu = params.menu,
      menuItems = _params$menu.items,
      editedDaysAgo = _params$menu.editedDaysAgo,
      _params$readMore = params.readMore,
      readMoreItemCount = _params$readMore.itemCount,
      readMoreBaseURL = _params$readMore.baseURL; // Add container

  if (_transform_FooterContainer__WEBPACK_IMPORTED_MODULE_1__["default"].isContainerAttached(document) === false) {
    var pcs = document.getElementById('pcs');
    pcs.appendChild(_transform_FooterContainer__WEBPACK_IMPORTED_MODULE_1__["default"].containerFragment(document));
  }
  /**
   * Callback for after i18n messages are loaded.
   * @param {!Banana} banana
   * @return {void}
   */


  var finish = function finish(banana) {
    // Add menu
    _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].setHeading(banana.i18n('article-about-title'), 'pcs-footer-container-menu-heading', document);
    menuItems.forEach(function (item) {
      var title = '';
      var subtitle = '';

      switch (item) {
        case _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].MenuItemType.lastEdited:
          title = banana.i18n('page-edit-history');
          subtitle = _getPageLastEditedString(banana, editedDaysAgo);
          break;

        case _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].MenuItemType.pageIssues:
          title = banana.i18n('page-issues');
          subtitle = banana.i18n('page-issues-subtitle');
          break;

        case _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].MenuItemType.disambiguation:
          title = banana.i18n('page-similar-titles');
          break;

        case _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].MenuItemType.coordinate:
          title = banana.i18n('page-location');
          break;

        case _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].MenuItemType.talkPage:
          title = banana.i18n('page-talk-page');
          subtitle = banana.i18n('page-talk-page-subtitle');
          break;

        default:
      }
      /**
       * @param {!map} payload menu item payload
       * @return {void}
       */


      var itemSelectionHandler = function itemSelectionHandler(payload) {
        if (handlers) {
          handlers.footerItemSelected(item, payload);
        }
      };

      _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].maybeAddItem(title, subtitle, item, 'pcs-footer-container-menu-items', itemSelectionHandler, document);
    });

    if (readMoreItemCount && readMoreItemCount > 0) {
      _transform_FooterReadMore__WEBPACK_IMPORTED_MODULE_4__["default"].fetchAndAdd(_getArticleTitleFromLocation(window.location), banana.i18n('article-read-more-title'), readMoreItemCount, 'pcs-footer-container-readmore', 'pcs-footer-container-readmore-pages', readMoreBaseURL, document);
    }
    /**
     * @return {void}
     */


    var viewInBrowserLinkClickHandler = function viewInBrowserLinkClickHandler() {
      if (handlers) {
        handlers.viewInBrowser();
      }
    };

    _transform_FooterLegal__WEBPACK_IMPORTED_MODULE_2__["default"].add(document, banana.i18n('license-footer-text'), banana.i18n('license-footer-name'), 'pcs-footer-container-legal', banana.i18n('view-in-browser-footer-link'), viewInBrowserLinkClickHandler);
  };

  var xhr = new XMLHttpRequest(); // eslint-disable-line no-undef

  var href = window.location.href || '';
  var index = href.indexOf('/page/mobile-html/'); // hax

  var stringsBaseURL = index >= 0 ? href.slice(0, index) : '';
  var stringsURL = "".concat(stringsBaseURL, "/data/i18n/pcs");
  xhr.open('GET', stringsURL, true);
  var failsafeBanana = {
    i18n: function i18n(message) {
      return message;
    }
  };

  xhr.onload = function () {
    var banana;

    try {
      var response = JSON.parse(xhr.responseText); // default locale is returned in the response

      var locale = response && response.locale || 'en'; // actual locale (with variant if applicable) is in the meta tag

      var localeMetaTag = document.head.querySelector('meta[property="pcs:locale"]');

      if (localeMetaTag) {
        var content = localeMetaTag.getAttribute('content');

        if (content) {
          locale = content;
        }
      }

      banana = new banana_i18n__WEBPACK_IMPORTED_MODULE_0___default.a(locale);
      banana.load(response.messages);
    } catch (e) {
      banana = failsafeBanana;
    }

    finish(banana);
  };

  xhr.onerror = function () {
    finish(failsafeBanana);
  };

  xhr.send();
};

/* harmony default export */ __webpack_exports__["default"] = ({
  MenuItemType: _transform_FooterMenu__WEBPACK_IMPORTED_MODULE_3__["default"].MenuItemType,
  add: add,
  // to be used internally or for unit testing only:
  _connectHandlers: _connectHandlers,
  _getPageLastEditedString: _getPageLastEditedString
});

/***/ }),

/***/ "./src/pcs/c1/InteractionHandling.js":
/*!*******************************************!*\
  !*** ./src/pcs/c1/InteractionHandling.js ***!
  \*******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/createClass */ "./node_modules/@babel/runtime/helpers/createClass.js");
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../transform/CollapseTable */ "./src/transform/CollapseTable.js");
/* harmony import */ var _transform_EditTransform__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../transform/EditTransform */ "./src/transform/EditTransform.js");
/* harmony import */ var _Footer__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./Footer */ "./src/pcs/c1/Footer.js");
/* harmony import */ var _transform_LazyLoadTransform__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../transform/LazyLoadTransform */ "./src/transform/LazyLoadTransform.ts");
/* harmony import */ var _transform_ReferenceCollection__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../../transform/ReferenceCollection */ "./src/transform/ReferenceCollection.js");
/* harmony import */ var _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../../transform/SectionUtilities */ "./src/transform/SectionUtilities.ts");








/**
 * Type of actions users can click which may need to be handled by the native side.
 * @type {!Object}
 */

var Actions = {
  InitialSetup: 'setup',
  FinalSetup: 'final_setup',
  LinkClicked: 'link',
  ImageClicked: 'image',
  ReferenceClicked: 'reference',
  BackLink: 'back_link',
  EditSection: 'edit_section',
  HeaderItemSelected: 'header_item',
  AddTitleDescription: 'add_title_description',
  PronunciationClicked: 'pronunciation',
  ScrollToAnchor: 'scroll_to_anchor',

  /* Footer related actions: */
  FooterItemSelected: 'footer_item',
  SaveOtherPage: 'save_other_page',
  ViewInBrowser: 'view_in_browser'
};
var interactionHandler;
/**
 * Model of an Interaction.
 */

var Interaction =
/**
 * @param {!string} action the type of action
 * @param {?Object.<any>} data details of the action
 */
function Interaction(action, data) {
  _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_1___default()(this, Interaction);

  this.action = action;
  this.data = data;
};
/**
 * Type of items users can click which we may need to handle.
 * @type {!Object}
 */


var ItemType = {
  unknown: 0,
  link: 1,
  image: 2,
  imagePlaceholder: 3,
  reference: 4,
  backLink: 5
};
/**
 * Model of clicked item.
 * Reminder: separate `target` and `href` properties
 * needed to handle non-anchor targets such as images.
 */

var ClickedItem = /*#__PURE__*/function () {
  /**
   * @param {!EventTarget} target event target
   * @param {!string} href
   * @param {!string} pageLinkTitle last path component of the current document as read
   * from dc:isVersionOf
   */
  function ClickedItem(target, href, pageLinkTitle) {
    _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_1___default()(this, ClickedItem);

    this.target = target;
    this.href = href;
    this.pageLinkTitle = pageLinkTitle;
  }
  /**
   * Determines type of item based on its properties.
   * @return {!ItemType} Type of the item
   */


  _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_0___default()(ClickedItem, [{
    key: "type",
    value: function type() {
      if (_transform_ReferenceCollection__WEBPACK_IMPORTED_MODULE_6__["default"].isCitation(this.href, this.pageLinkTitle)) {
        return ItemType.reference;
      }

      if (_transform_ReferenceCollection__WEBPACK_IMPORTED_MODULE_6__["default"].isBackLink(this.href, this.pageLinkTitle)) {
        return ItemType.backLink;
      } else if (this.target.tagName === 'IMG' && (this.target.classList.contains(_transform_LazyLoadTransform__WEBPACK_IMPORTED_MODULE_5__["default"].CLASSES.IMAGE_LOADED_CLASS) || this.target.classList.contains(_transform_LazyLoadTransform__WEBPACK_IMPORTED_MODULE_5__["default"].CLASSES.IMAGE_LOADING_CLASS)) // FIXME(T266143): 'figure-inline' is being deprecated
      && (this.target.closest('figure') || this.target.closest('figure-inline') || this.target.closest('span'))) {
        return ItemType.image;
      } else if (this.target.tagName === 'SPAN' && this.target.classList.contains(_transform_LazyLoadTransform__WEBPACK_IMPORTED_MODULE_5__["default"].CLASSES.PLACEHOLDER_CLASS) // FIXME(T266143): 'figure-inline' is being deprecated
      && (this.target.closest('figure') || this.target.closest('figure-inline') || this.target.closest('span'))) {
        return ItemType.imagePlaceholder;
      } else if (this.href) {
        return ItemType.link;
      }

      return ItemType.unknown;
    }
  }]);

  return ClickedItem;
}();
/**
 * Posts a message to native land using the interaction handler.
 * @param {Interaction} interaction the interaction data
 * @return {void}
 */


var postMessage = function postMessage(interaction) {
  if (interactionHandler) {
    interactionHandler(interaction);
  }
};
/**
 * Posts message for a link click.
 * @param {!Element} target element
 * @param {!string} href url
 * @return {void}
 */


var postMessageForLink = function postMessageForLink(target, href) {
  if (href[0] === '#') {
    _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__["default"].expandCollapsedTableIfItContainsElement(document.getElementById(href.substring(1)));
  }

  postMessage(new Interaction(Actions.LinkClicked, {
    href: href,
    text: target.innerText,
    title: target.title,
    source: target.getAttribute('data-pcs-source')
  }));
};
/**
 * Canonical file href
 * @param {!string} href url for the image
 * @return {!string} canonicalized file href
 */

/**
 * Posts message for an image click.
 * @param {!Element} target an image element
 * @param {!string} href url for the image
 * @return {void}
 */


var postMessageForImage = function postMessageForImage(target, href) {
  postMessage(new Interaction(Actions.ImageClicked, {
    href: href,
    src: target.getAttribute('src'),
    'data-file-width': target.getAttribute('data-file-width'),
    'data-file-height': target.getAttribute('data-file-height')
  }));
};
/**
 * Posts a message for a lazy load image placeholder click.
 * @param {!Element} innerPlaceholderSpan
 * @param {!string} href url for the image
 * @return {void}
 */


var postMessageForImagePlaceholder = function postMessageForImagePlaceholder(innerPlaceholderSpan, href) {
  var outerSpan = innerPlaceholderSpan.parentElement;
  postMessage(new Interaction(Actions.ImageClicked, {
    href: href,
    src: outerSpan.getAttribute('data-src'),
    'data-file-width': outerSpan.getAttribute('data-data-file-width'),
    'data-file-height': outerSpan.getAttribute('data-data-file-height')
  }));
};
/**
 * Posts a message for a reference click.
 * @param {!Element} target an anchor element
 * @param {?string} href
 * @return {void}
 */


var postMessageForReferenceWithTarget = function postMessageForReferenceWithTarget(target, href) {
  var nearbyReferences = _transform_ReferenceCollection__WEBPACK_IMPORTED_MODULE_6__["default"].collectNearbyReferences(document, target);
  nearbyReferences.href = href;
  postMessage(new Interaction(Actions.ReferenceClicked, nearbyReferences));
};
/**
 * Posts a message for a back link click.
 * @param {!Element} target an anchor element
 * @param {?string} href
 * @return {void}
 */


var postMessageForBackLinkWithTarget = function postMessageForBackLinkWithTarget(target, href) {
  var nearbyReferences = _transform_ReferenceCollection__WEBPACK_IMPORTED_MODULE_6__["default"].collectReferencesForBackLink(document, target, href);
  postMessage(new Interaction(Actions.BackLink, nearbyReferences));
};
/**
 * Post messages to native land for respective click types.
 * @param  {!ClickedItem} item the item which was clicked on
 * @return {boolean} `true` if a message was sent, otherwise `false`
 */


var postMessageForClickedItem = function postMessageForClickedItem(item) {
  switch (item.type()) {
    case ItemType.link:
      postMessageForLink(item.target.closest('a'), item.href);
      break;

    case ItemType.image:
      postMessageForImage(item.target, item.href);
      break;

    case ItemType.imagePlaceholder:
      postMessageForImagePlaceholder(item.target, item.href);
      break;

    case ItemType.reference:
      postMessageForReferenceWithTarget(item.target, item.href);
      break;

    case ItemType.backLink:
      postMessageForBackLinkWithTarget(item.target, item.href);
      break;

    default:
      return false;
  }

  return true;
};
/**
 * Handler for the click event. Posts messages across the JS bridge to native land.
 * @param  {Event} event the event being handled
 * @return {void}
 */


var handleClickEvent = function handleClickEvent(event) {
  var target = event.target;

  if (!target) {
    return;
  } // Find anchor for non-anchor targets - like images.


  var anchorForTarget = target.closest('A');

  if (!anchorForTarget) {
    return;
  } // "View article in browser" handled in `browserLinkClickHandler`. Need to ignore it here.


  if (anchorForTarget.className === "pcs-footer-browser-link") {
    return;
  }

  if (anchorForTarget.className === "pcs-title-icon-talk-page") {
    var data = {
      itemType: "talkPage",
      payload: []
    };
    postMessage(new Interaction(Actions.HeaderItemSelected, data));
    return;
  } // Handle edit links.


  if (anchorForTarget.getAttribute('data-action') === 'edit_section') {
    var sectionId = anchorForTarget.getAttribute('data-id') || undefined;
    var _data = {
      sectionId: sectionId
    };

    if (sectionId && sectionId === '0') {
      var descriptionElement = document.getElementById(_transform_EditTransform__WEBPACK_IMPORTED_MODULE_3__["default"].IDS.TITLE_DESCRIPTION);
      _data.descriptionSource = descriptionElement && descriptionElement.getAttribute(_transform_EditTransform__WEBPACK_IMPORTED_MODULE_3__["default"].DATA_ATTRIBUTE.DESCRIPTION_SOURCE) || undefined;
      _data.wikidataEntityID = descriptionElement && descriptionElement.getAttribute(_transform_EditTransform__WEBPACK_IMPORTED_MODULE_3__["default"].DATA_ATTRIBUTE.WIKIDATA_ENTITY_ID) || undefined;
    }

    postMessage(new Interaction(Actions.EditSection, _data));
    return;
  } // Handle add title description link.


  if (anchorForTarget.getAttribute('data-section')) {
    var sectionId = anchorForTarget.getAttribute('data-section') || undefined;
    var _data = {
      sectionId: sectionId
    };
    postMessage(new Interaction(Actions.EditSection, _data));
    return;
  }


  if (anchorForTarget.getAttribute('data-action') === 'add_title_description') {
    postMessage(new Interaction(Actions.AddTitleDescription));
    return;
  } // Handle audio pronunciation button.


  if (anchorForTarget.getAttribute('data-action') === 'title_pronunciation') {
    var url = anchorForTarget.getAttribute(_transform_EditTransform__WEBPACK_IMPORTED_MODULE_3__["default"].DATA_ATTRIBUTE.PRONUNCIATION_URL) || undefined;
    postMessage(new Interaction(Actions.PronunciationClicked, {
      url: url
    }));
    return;
  }

  var href = anchorForTarget.getAttribute('href');

  if (!href) {
    return;
  }

  var pageTitle;
  var linkElement = document.head.querySelector('link[rel="dc:isVersionOf"]');

  if (linkElement) {
    var linkHref = linkElement.href;

    if (linkHref) {
      var components = linkHref.split('/');
      pageTitle = components.pop();

      if (pageTitle === '') {
        pageTitle = components.pop;
      }
    }
  }

  postMessageForClickedItem(new ClickedItem(target, href, pageTitle));
};
/**
 * @param {!string} itemType type of footer menu item
 * @param {!map} payload menu item payload
 * @return {void}
 */


var footerItemSelected = function footerItemSelected(itemType, payload) {
  postMessage(new Interaction(Actions.FooterItemSelected, {
    itemType: itemType,
    payload: payload
  }));
};
/**
 * @param {!string} title page title
 * @return {void}
 */


var saveOtherPage = function saveOtherPage(title) {
  postMessage(new Interaction(Actions.SaveOtherPage, {
    title: title
  }));
};
/**
 * @return {void}
 */


var viewInBrowser = function viewInBrowser() {
  postMessage(new Interaction(Actions.ViewInBrowser));
};
/**
 * @return {void}
 */


var initialSetupComplete = function initialSetupComplete() {
  postMessage(new Interaction(Actions.InitialSetup));
};
/**
 * @return {void}
 */


var finalSetupComplete = function finalSetupComplete() {
  postMessage(new Interaction(Actions.FinalSetup));
};
/**
 * @param {!string} anchor id for the element
 * @param {!string} rect client rect for the element
 * @return {void}
 */


var scrollToAnchor = function scrollToAnchor(anchor, rect) {
  postMessage(new Interaction(Actions.ScrollToAnchor, {
    anchor: anchor,
    rect: rect,
    href: "#".concat(anchor)
  }));
};
/**
 * Gets information about the current text selection
 * @param {?Window} optionalWindow
 * @return {!map} selection info
 */


var getSelectionInfo = function getSelectionInfo(optionalWindow) {
  var selection = (optionalWindow || window).getSelection();
  var text = selection.toString();
  var anchorNode = selection.anchorNode;
  var section = _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_7__["default"].getSectionIDOfElement(anchorNode);
  var isTitleDescription = anchorNode && anchorNode.parentElement && anchorNode.parentElement.id === 'pcs-edit-section-title-description' || false;
  return {
    text: text,
    section: section,
    isTitleDescription: isTitleDescription
  };
};
/**
 * Sets the interaction handler function.
 * @param {!~Function} myHandlerFunction a platform specific bridge function.
 * On iOS consider using something like:
 *   (interaction) => { window.webkit.messageHandlers.interaction.postMessage(interaction) }
 * On Android consider using something like:
 *   (interaction) => { window.InteractionWebInterface.post(interaction) }
 * To test in a browser consider using something like:
 *   (interaction) => { console.log(JSON.stringify(interaction)) }
 * @return {void}
 */


var setInteractionHandler = function setInteractionHandler(myHandlerFunction) {
  interactionHandler = myHandlerFunction;

  _Footer__WEBPACK_IMPORTED_MODULE_4__["default"]._connectHandlers({
    footerItemSelected: footerItemSelected,
    saveOtherPage: saveOtherPage,
    viewInBrowser: viewInBrowser
  }); // Associate our custom click handler logic with the document `click` event.


  document.addEventListener('click', function (event) {
    event.preventDefault();
    handleClickEvent(event);
  }, false);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  Actions: Actions,
  getSelectionInfo: getSelectionInfo,
  setInteractionHandler: setInteractionHandler,
  initialSetupComplete: initialSetupComplete,
  finalSetupComplete: finalSetupComplete,
  scrollToAnchor: scrollToAnchor
});

/***/ }),

/***/ "./src/pcs/c1/Page.js":
/*!****************************!*\
  !*** ./src/pcs/c1/Page.js ***!
  \****************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _transform_AdjustTextSize__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../transform/AdjustTextSize */ "./src/transform/AdjustTextSize.ts");
/* harmony import */ var _transform_BodySpacingTransform__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../transform/BodySpacingTransform */ "./src/transform/BodySpacingTransform.ts");
/* harmony import */ var _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../transform/CollapseTable */ "./src/transform/CollapseTable.js");
/* harmony import */ var _DemoMode__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./DemoMode */ "./src/pcs/c1/DemoMode.js");
/* harmony import */ var _transform_DimImagesTransform__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../transform/DimImagesTransform */ "./src/transform/DimImagesTransform.js");
/* harmony import */ var _transform_EditTransform__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../transform/EditTransform */ "./src/transform/EditTransform.js");
/* harmony import */ var _InteractionHandling__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./InteractionHandling */ "./src/pcs/c1/InteractionHandling.js");
/* harmony import */ var _transform_LazyLoadTransformer__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../../transform/LazyLoadTransformer */ "./src/transform/LazyLoadTransformer.js");
/* harmony import */ var _transform_NodeUtilities__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../../transform/NodeUtilities */ "./src/transform/NodeUtilities.js");
/* harmony import */ var _transform_PlatformTransform__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../../transform/PlatformTransform */ "./src/transform/PlatformTransform.js");
/* harmony import */ var _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ../../transform/SectionUtilities */ "./src/transform/SectionUtilities.ts");
/* harmony import */ var _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ../../transform/ThemeTransform */ "./src/transform/ThemeTransform.js");
/* harmony import */ var _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ../../transform/HTMLUtilities */ "./src/transform/HTMLUtilities.ts");













var unitsRegex = /[^0-9]+$/;
var PCS_CSS_CLASS_ELEMENT_HIGHLIGHT = 'pcs-element-highlight';
/**
 * Waits for the next paint, then calls the callback
 * @param {?OnSuccess} onSuccess callback
 * @return {void}
 */

var waitForNextPaint = function waitForNextPaint(onSuccess) {
  if (!(onSuccess instanceof Function)) {
    return;
  }

  if (window && window.requestAnimationFrame) {
    // request animation frame and set timeout before callback to ensure paint occurs
    window.requestAnimationFrame(function () {
      setTimeout(function () {
        onSuccess();
      }, 1);
    });
  } else {
    onSuccess();
  }
};
/**
 * Makes multiple page modifications based on client specific settings, which should be called
 * during initial page load.
 * @param {?{}} optionalSettings client settings
 *   { platform, version, theme, dimImages, margins, areTablesInitiallyExpanded,
 *   scrollTop, textSizeAdjustmentPercentage }
 * @param {?OnSuccess} onSuccess callback
 * @return {void}
 */


var setup = function setup(optionalSettings, onSuccess) {
  var settings = optionalSettings || {};
  _transform_PlatformTransform__WEBPACK_IMPORTED_MODULE_9__["default"].setVersion(document, settings.version);

  if (settings.platform !== undefined) {
    _transform_PlatformTransform__WEBPACK_IMPORTED_MODULE_9__["default"].setPlatform(document, _transform_PlatformTransform__WEBPACK_IMPORTED_MODULE_9__["default"].CLASS_PREFIX + settings.platform);
  }

  if (settings.theme !== undefined) {
    _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_11__["default"].setTheme(document, _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_11__["default"].CLASS_PREFIX + settings.theme);
  }

  if (settings.bodyFont !== undefined) {
    _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_11__["default"].setBodyFont(document, settings.bodyFont);
  }

  if (settings.dimImages !== undefined) {
    _transform_DimImagesTransform__WEBPACK_IMPORTED_MODULE_4__["default"].dimImages(document, settings.dimImages);
  }

  var metaTags; // lazy load these to avoid a double query selector

  if (settings.margins !== undefined || settings.leadImageHeight !== undefined) {
    var margins = settings.margins || {};

    if (settings.leadImageHeight !== undefined) {
      if (!metaTags) {
        metaTags = getMetaTags();
      }

      var leadImage = getLeadImageFromMetaTags(metaTags);

      if (leadImage.source) {
        if (margins.top) {
          var top = parseFloat(margins.top, 10);
          var height = parseFloat(settings.leadImageHeight, 10);
          var units = margins.top.match(unitsRegex) || '';
          margins.top = top + height + units;
        } else {
          margins.top = settings.leadImageHeight;
        }
      }
    }

    _transform_BodySpacingTransform__WEBPACK_IMPORTED_MODULE_1__["default"].setMargins(document.body, margins);
  }

  if (settings.maxWidth !== undefined) {
    setMaxWidth(settings.maxWidth);
  }

  if (settings.userGroups !== undefined) {
    if (!metaTags) {
      metaTags = getMetaTags();
    }

    var protection = getProtectionFromMetaTags(metaTags);
    var isEditable = settings.isEditable !== undefined ? settings.isEditable : true;
    var isProtected = false;

    if (protection.edit) {
      isProtected = true;

      for (var i = 0; i < settings.userGroups.length; i++) {
        var userGroup = settings.userGroups[i];

        if (userGroup === protection.edit) {
          isProtected = false;
          break;
        }
      }
    }

    setEditButtons(isEditable, isProtected);
  }

  if (settings.setupTableEventHandling === undefined || settings.setupTableEventHandling) {
    var isInitiallyCollapsed = settings.areTablesInitiallyExpanded !== true;
    _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__["default"].setupEventHandling(window, document, isInitiallyCollapsed, function (container) {
      window.scrollTo(0, container.offsetTop - window.innerHeight / 2);
    });
  }

  if (settings.textSizeAdjustmentPercentage !== undefined) {
    _transform_AdjustTextSize__WEBPACK_IMPORTED_MODULE_0__["default"].setPercentage(document.body, settings.textSizeAdjustmentPercentage);
  }

  if (settings.loadImages === undefined || settings.loadImages === true) {
    var lazyLoader = new _transform_LazyLoadTransformer__WEBPACK_IMPORTED_MODULE_7__["default"](window, 2);
    lazyLoader.collectExistingPlaceholders(document.body);
    lazyLoader.loadPlaceholders();
  }

  if (settings.talkPageButton) {
    setTalkPageButton(settings.talkPageButton);
  }

  if (settings.footer) {
    _DemoMode__WEBPACK_IMPORTED_MODULE_3__["default"].addFooter(new URL(document.location));
  }

  waitForNextPaint(onSuccess);
};
/**
 * Sets the theme.
 * @param {!string} theme one of the values in Themes
 * @return {void}
 */


var setTheme = function setTheme(theme) {
  _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_11__["default"].setTheme(document, theme);
};
/**
 * Toggles dimming of images.
 * @param {!boolean} dimImages true if images should be dimmed, false otherwise
 * @return {void}
 */


var setDimImages = function setDimImages(dimImages) {
  _transform_DimImagesTransform__WEBPACK_IMPORTED_MODULE_4__["default"].dimImages(document, dimImages);
};
/**
 * Sets the margins.
 * @param {!{BodySpacingTransform.Spacing}} margins
 * @return {void}
 */


var setMargins = function setMargins(margins) {
  _transform_BodySpacingTransform__WEBPACK_IMPORTED_MODULE_1__["default"].setMargins(document.body, margins);
};
/**
 * Sets the max width of the content.
 * @param {!string} maxWidth
 * @return {void}
 */


var setMaxWidth = function setMaxWidth(maxWidth) {
  if (!document || !document.body) {
    return;
  }

  document.body.style.maxWidth = _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_12__["default"].escape(maxWidth);
};
/**
 * Sets text size adjustment percentage of the body element
 * @param  {!string} textSize percentage for text-size-adjust in format of string, like '100%'
 * @return {void}
 */


var setTextSizeAdjustmentPercentage = function setTextSizeAdjustmentPercentage(textSize) {
  _transform_AdjustTextSize__WEBPACK_IMPORTED_MODULE_0__["default"].setPercentage(document.body, textSize);
};
/**
 * Enables edit buttons to be shown (and which ones).
 * @param {?boolean} isEditable true if edit buttons should be shown
 * @param {?boolean} isProtected true if the protected edit buttons should be shown
 * @return {void}
 */


var setEditButtons = function setEditButtons(isEditable, isProtected) {
  _transform_EditTransform__WEBPACK_IMPORTED_MODULE_5__["default"].setEditButtons(document, isEditable, isProtected);
};
/**
 * Enables header title icon buttons to be shown.
 * @param {?boolean} isVisible true if the title icon should be shown
 * @return {void}
 */


var setTalkPageButton = function setTalkPageButton(isVisible) {
  _transform_EditTransform__WEBPACK_IMPORTED_MODULE_5__["default"].setTalkPageButton(document, isVisible);
};
/**
 * Gets the revision of the current mobile-html page.
 * @return {?string}
 */


var getRevision = function getRevision() {
  var about = document.documentElement.getAttribute('about');

  if (!about) {
    return undefined;
  }

  return about.substring(about.lastIndexOf('/') + 1);
};
/**
 * Get structured table of contents data
 * @return {!Array}
 */


var getTableOfContents = function getTableOfContents() {
  var headings = document.querySelectorAll('h1,h2,h3,h4,h5,h6');
  var result = [];
  var curId = 0;
  [].forEach.call(headings, function (heading) {
    var level = parseInt(heading.tagName.charAt(1), 10) - 1;
    var id = curId++;

    if (!id || isNaN(id) || id < 1) {
      return;
    }

    var span = heading.querySelector('span');
    if (!span) {
      return;
    }

    result.push({
      level: level,
      id: id,
      anchor: span.getAttribute('id'),
      title: span.innerHTML.trim()
    });
  });
  return result;
};
/**
 * Get protection information for the page from given meta tags
 * @private
 * @param {!Array} metaTags
 * @return {!map}
 */


var getProtectionFromMetaTags = function getProtectionFromMetaTags(metaTags) {
  var protection = {};
  var protectionPrefix = 'mw:pageProtection:';
  var protectionPrefixLength = protectionPrefix.length;
  metaTags.forEach(function (metaTag) {
    var property = metaTag.getAttribute('property');

    if (property && property.startsWith(protectionPrefix)) {
      protection[property.substring(protectionPrefixLength)] = metaTag.getAttribute('content');
    }
  });
  return protection;
};
/**
 * Return meta tags for the page
 * @private
 * @return {!Array}
 */


var getMetaTags = function getMetaTags() {
  return document.head.querySelectorAll('meta');
};
/**
 * Get protection information for the page
 * @return {!map}
 */


var getProtection = function getProtection() {
  return getProtectionFromMetaTags(getMetaTags());
};
/**
 * Ensures the element with the given anchor is visible before scrolling and returns the
 * boundingClientRect of that element
 * @param {!string} anchor of the element that will be scrolled to
 * @param {!{}} options object with options. currently only supports
 * highlight = true to highlight the element
 * @return {void}
 */


var prepareForScrollToAnchor = function prepareForScrollToAnchor(anchor, options) {
  if (!document) {
    return undefined;
  }

  var element = document.getElementById(anchor);

  if (!element) {
    return undefined;
  }

  _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_10__["default"].expandCollapsedSectionIfItContainsElement(document, element);
  _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__["default"].expandCollapsedTableIfItContainsElement(element);

  if (options && options.highlight) {
    removeHighlightsFromHighlightedElements();
    element.classList.add(PCS_CSS_CLASS_ELEMENT_HIGHLIGHT);
  }

  waitForNextPaint(function () {
    var rect = _transform_NodeUtilities__WEBPACK_IMPORTED_MODULE_8__["default"].getBoundingClientRectAsPlainObject(element);
    _InteractionHandling__WEBPACK_IMPORTED_MODULE_6__["default"].scrollToAnchor(anchor, rect);
  });
};
/**
 * Removes highlight class from any highlighted elements
 * @return {void}
 */


var removeHighlightsFromHighlightedElements = function removeHighlightsFromHighlightedElements() {
  if (!document) {
    return;
  }

  var selector = ".".concat(PCS_CSS_CLASS_ELEMENT_HIGHLIGHT);
  var element = document.querySelector(selector);

  while (element) {
    element.classList.remove(PCS_CSS_CLASS_ELEMENT_HIGHLIGHT);
    element = document.querySelector(selector);
  }
};
/**
 * Expend or collapse all tables
 * @param  {!boolean} Expand/collapse tables manually
 * @return {void}
 */


var expandOrCollapseTables = function expandOrCollapseTables(expand) {
  if (!document) {
    return;
  }

  _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__["default"].expandOrCollapseTables(document, expand);
};
/**
 * Gets the lead image for a page
 * @private
 * @param {!Array} metaTags
 * @return {!map}
 */


var getLeadImageFromMetaTags = function getLeadImageFromMetaTags(metaTags) {
  var image = {};
  var leadImageProperty = 'mw:leadImage';

  for (var i = 0; i < metaTags.length; i++) {
    var metaTag = metaTags[i];
    var property = metaTag.getAttribute('property');

    if (!property || property !== leadImageProperty) {
      continue;
    }

    image.source = metaTag.getAttribute('content');
    var widthString = metaTag.getAttribute('data-file-width');

    if (widthString) {
      image.width = parseInt(widthString, 10);
    }

    var heightString = metaTag.getAttribute('data-file-height');

    if (heightString) {
      image.height = parseInt(heightString, 10);
    }

    break;
  }

  return image;
};
/**
 * Gets the lead image for a page
 * @return {!map}
 */


var getLeadImage = function getLeadImage() {
  return getLeadImageFromMetaTags(getMetaTags());
};
/**
 * Executes pagelib functionality intended to run before any content has loaded
 * @return {void}
 */


var onBodyStart = function onBodyStart() {
  if (!document) {
    return;
  } // eslint-disable-next-line no-undef




  // --------------
  // Modifications to accommodate direct loading from Mobile Web.
  {
    var style = document.createElement('style');
    style.innerHTML = '.header-chrome { visibility: hidden; margin-top: 48px; height: 0px; } #page-secondary-actions { display: none; } .mw-footer { padding-bottom: 72px; } .page-actions-menu { display: none; } .minerva__tab-container { display: none; }';
    document.head.appendChild(style);
  }
  // --------------




  if (typeof pcsClient !== 'undefined' && pcsClient.getSetupSettings) {
    // eslint-disable-next-line no-undef
    var setupJSON = pcsClient.getSetupSettings();
    document.pcsSetupSettings = JSON.parse(setupJSON);
  } // eslint-disable-next-line no-undef


  if (typeof pcsClient !== 'undefined' && pcsClient.onReceiveMessage) {
    document.pcsActionHandler = function (action) {
      // eslint-disable-next-line no-undef
      pcsClient.onReceiveMessage(JSON.stringify(action));
    };
  }

  if (document.pcsActionHandler) {
    _InteractionHandling__WEBPACK_IMPORTED_MODULE_6__["default"].setInteractionHandler(document.pcsActionHandler);
  } else {
    // eslint-disable-next-line no-console
    _InteractionHandling__WEBPACK_IMPORTED_MODULE_6__["default"].setInteractionHandler(function (action) {
      return console.log(action);
    });
  } // eslint-disable-next-line require-jsdoc


  var initialSetupCompletion = function initialSetupCompletion() {
    _InteractionHandling__WEBPACK_IMPORTED_MODULE_6__["default"].initialSetupComplete();
  };

  if (document.pcsSetupSettings) {
    var preSettings = {
      margins: document.pcsSetupSettings.margins,
      maxWidth: document.pcsSetupSettings.maxWidth,
      textSizeAdjustmentPercentage: document.pcsSetupSettings.textSizeAdjustmentPercentage,
      leadImageHeight: document.pcsSetupSettings.leadImageHeight,
      userGroups: document.pcsSetupSettings.userGroups,
      theme: document.pcsSetupSettings.theme,
      platform: document.pcsSetupSettings.platform,
      bodyFont: document.pcsSetupSettings.bodyFont,
      isEditable: document.pcsSetupSettings.isEditable,
      loadImages: false,
      setupTableEventHandling: false
    };
    setup(preSettings, initialSetupCompletion);
    return;
  }

  var defaultInitialSettings = {
    loadImages: false,
    setupTableEventHandling: false,
    maxWidth: '100ex',
    margins: {
      top: '2em',
      bottom: '0'
    },
    userGroups: []
  };
  var queryString = document.location && document.location.search;

  if (queryString) {
    var queryParams = new URLSearchParams(document.location.search);

    if (queryParams.get('theme')) {
      defaultInitialSettings.theme = queryParams.get('theme');
    }
  }

  setup(defaultInitialSettings, initialSetupCompletion);
};
/**
 * Executes pagelib functionality intended to run after all content has loaded
 * @return {void}
 */


var onBodyEnd = function onBodyEnd() {
  if (!document) {
    return;
  }



  // ------ Modifications to accommodate direct loading from Mobile Web. ------
  _transform_CollapseTable__WEBPACK_IMPORTED_MODULE_2__["default"].collapseTables(window, document, "FooBar", false, "Quick Facts", "More information", "Close")
  //-------------




  var remainingContentTimeout = 100;
  _transform_EditTransform__WEBPACK_IMPORTED_MODULE_5__["default"].setARIAEditButtons(document);
  /**
   * Check query parameters to see if the footer should be automatically added.
   * @return {boolean}
   */

  var shouldAddFooter = function shouldAddFooter() {
    if (document.location && document.location.search) {
      var queryParams = new URLSearchParams(document.location.search);
      return queryParams.get('footer') === 'true' || queryParams.get('demo') !== null;
    }

    return false;
  };
  /**
   * Executed when final setup is complete
   * @return {void}
   */


  var finalSetupComplete = function finalSetupComplete() {
    _InteractionHandling__WEBPACK_IMPORTED_MODULE_6__["default"].finalSetupComplete();
  };

  if (document.pcsSetupSettings) {
    var postSettings = document.pcsSetupSettings;
    delete postSettings.theme;
    delete postSettings.margins;
    delete postSettings.maxWidth;
    delete postSettings.userGroups;
    delete postSettings.leadImageHeight;
    delete postSettings.platform;
    delete postSettings.textSizeAdjustmentPercentage;
    postSettings.setupTableEventHandling = true;
    setup(postSettings, finalSetupComplete);
    remainingContentTimeout = document.pcsSetupSettings.remainingTimeout || remainingContentTimeout;
  } else {
    var footer = shouldAddFooter();
    setup({
      setupTableEventHandling: true,
      areTablesInitiallyExpanded: true,
      talkPageButton: false,
      footer: footer
    }, finalSetupComplete);
  }

  setTimeout(function () {
    var sections = document.querySelectorAll('section');

    for (var i = 1; i < sections.length; i++) {
      sections[i].style.display = '';
    }
  }, remainingContentTimeout);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  onBodyStart: onBodyStart,
  onBodyEnd: onBodyEnd,
  setup: setup,
  setTheme: setTheme,
  setDimImages: setDimImages,
  setMargins: setMargins,
  setMaxWidth: setMaxWidth,
  setTextSizeAdjustmentPercentage: setTextSizeAdjustmentPercentage,
  setEditButtons: setEditButtons,
  setTalkPageButton: setTalkPageButton,
  getLeadImage: getLeadImage,
  getProtection: getProtection,
  getRevision: getRevision,
  getTableOfContents: getTableOfContents,
  prepareForScrollToAnchor: prepareForScrollToAnchor,
  removeHighlightsFromHighlightedElements: removeHighlightsFromHighlightedElements,
  expandOrCollapseTables: expandOrCollapseTables,
  waitForNextPaint: waitForNextPaint
});

/***/ }),

/***/ "./src/pcs/c1/Platforms.js":
/*!*********************************!*\
  !*** ./src/pcs/c1/Platforms.js ***!
  \*********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _transform_PlatformTransform__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../transform/PlatformTransform */ "./src/transform/PlatformTransform.js");

var Platforms = _transform_PlatformTransform__WEBPACK_IMPORTED_MODULE_0__["default"].CLASS;
/* harmony default export */ __webpack_exports__["default"] = ({
  ANDROID: Platforms.ANDROID,
  IOS: Platforms.IOS
});

/***/ }),

/***/ "./src/pcs/c1/Sections.js":
/*!********************************!*\
  !*** ./src/pcs/c1/Sections.js ***!
  \********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../transform/SectionUtilities */ "./src/transform/SectionUtilities.ts");

/**
 * Hide or unhide a section.
 * @param {!string} sectionId
 * @param {?boolean} hidden
 * @return {void}
 */

var setHidden = function setHidden(sectionId, hidden) {
  if (!document) {
    return;
  }

  _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_0__["default"].setHidden(document, sectionId, hidden);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  getOffsets: _transform_SectionUtilities__WEBPACK_IMPORTED_MODULE_0__["default"].getSectionOffsets,
  setHidden: setHidden
});

/***/ }),

/***/ "./src/pcs/c1/Themes.js":
/*!******************************!*\
  !*** ./src/pcs/c1/Themes.js ***!
  \******************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../transform/ThemeTransform */ "./src/transform/ThemeTransform.js");

var Themes = _transform_ThemeTransform__WEBPACK_IMPORTED_MODULE_0__["default"].THEME;
/* harmony default export */ __webpack_exports__["default"] = ({
  DEFAULT: Themes.DEFAULT,
  DARK: Themes.DARK,
  SEPIA: Themes.SEPIA,
  BLACK: Themes.BLACK
});

/***/ }),

/***/ "./src/pcs/c1/index.js":
/*!*****************************!*\
  !*** ./src/pcs/c1/index.js ***!
  \*****************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _Footer__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Footer */ "./src/pcs/c1/Footer.js");
/* harmony import */ var _InteractionHandling__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./InteractionHandling */ "./src/pcs/c1/InteractionHandling.js");
/* harmony import */ var _Page__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./Page */ "./src/pcs/c1/Page.js");
/* harmony import */ var _Platforms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./Platforms */ "./src/pcs/c1/Platforms.js");
/* harmony import */ var _Sections__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./Sections */ "./src/pcs/c1/Sections.js");
/* harmony import */ var _Themes__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./Themes */ "./src/pcs/c1/Themes.js");






/* harmony default export */ __webpack_exports__["default"] = ({
  Footer: _Footer__WEBPACK_IMPORTED_MODULE_0__["default"],
  InteractionHandling: _InteractionHandling__WEBPACK_IMPORTED_MODULE_1__["default"],
  Platforms: _Platforms__WEBPACK_IMPORTED_MODULE_3__["default"],
  Page: _Page__WEBPACK_IMPORTED_MODULE_2__["default"],
  Sections: _Sections__WEBPACK_IMPORTED_MODULE_4__["default"],
  Themes: _Themes__WEBPACK_IMPORTED_MODULE_5__["default"]
});

/***/ }),

/***/ "./src/pcs/index.js":
/*!**************************!*\
  !*** ./src/pcs/index.js ***!
  \**************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _c1__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./c1 */ "./src/pcs/c1/index.js");
// Versioned abstraction layer. mobile-html clients are highly encouraged to go through this instead
// of accessing the other JS functionality directly.
// c1 stands for PCS client version 1.

/* harmony default export */ __webpack_exports__["default"] = ({
  c1: _c1__WEBPACK_IMPORTED_MODULE_0__["default"]
});

/***/ }),

/***/ "./src/transform/AdjustTextSize.ts":
/*!*****************************************!*\
  !*** ./src/transform/AdjustTextSize.ts ***!
  \*****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/**
 * Sets text size adjustment percentage of the body element
 * @param  {!HTMLBodyElement} body that needs the margins adjusted.
 * @param  {!string} textSize percentage for text-size-adjust in format of string, like '100%'
 * @return {void}
 */
var setPercentage = function setPercentage(body, textSize) {
  if (textSize) {
    // '-webkit-text-size-adjust' is broken on iPadOS 13: https://bugs.webkit.org/show_bug.cgi?id=201404
    // This hacky code allows us to adjust font sizes, instead of the convenient 2 lines commented
    // out at the bottom of this function. Once (Apple fixes this bug and) the iPadOS versions
    // with the bug have become an insignificant amount of our app usage, we can delete the following
    // few lines and reactivate the two commented-out lines at the end of this function.
    // Notably, as of April 2020, this function is used by iOS but not Android.
    // Android updates text size w/ `webView.getSettings().setDefaultFontSize(...)`.
    // remove percent sign
    var requestedTextSizeNumber = Number(textSize.slice(0, -1)); // Base.css applies a font-size of .9411764706, per discussion w/ Carolyn
    // we're going to round that to 95% for now.

    var calculatedStartPercent = .95;
    var calculatedSize = requestedTextSizeNumber / 100 * calculatedStartPercent;
    var calculatedSizeString = (calculatedSize * 100).toString() + "%";
    body.style['font-size'] = calculatedSizeString; // casting body style to avoid errors with the subscript operator and typescript
    // see https://stackoverflow.com/questions/37655393
    // (<any>body.style)['-webkit-text-size-adjust'] = textSize;
    // (<any>body.style)['text-size-adjust'] = textSize;
  }
};

/* harmony default export */ __webpack_exports__["default"] = ({
  setPercentage: setPercentage
});

/***/ }),

/***/ "./src/transform/BodySpacingTransform.ts":
/*!***********************************************!*\
  !*** ./src/transform/BodySpacingTransform.ts ***!
  \***********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/**
 * Sets the margins on an element via inline styles.
 * @param {!HTMLBodyElement} bodyElement the element that needs the margins adjusted.
 *   For the apps this is usually the body element.
 * @param {Spacing} values { top, right, bottom, left }
 *   Use value strings with units, e.g. '16px'. Undefined values are ignored.
 * @return {void}
 */
var setMargins = function setMargins(bodyElement, values) {
  if (values.top !== undefined) {
    bodyElement.style.marginTop = values.top;
  }

  if (values.right !== undefined) {
    bodyElement.style.marginRight = values.right;
  }

  if (values.bottom !== undefined) {
    bodyElement.style.marginBottom = values.bottom;
  }

  if (values.left !== undefined) {
    bodyElement.style.marginLeft = values.left;
  }
};
/**
 * Sets padding on an element via inline styles.
 * @param {!HTMLBodyElement} bodyElement the element that needs the padding adjusted.
 *   For the apps this is usually the body element.
 * @param {Spacing} values { top, right, bottom, left }
 *   Use value strings with units, e.g. '16px'. Undefined values are ignored.
 * @return {void}
 */


var setPadding = function setPadding(bodyElement, values) {
  if (values.top !== undefined) {
    bodyElement.style.paddingTop = values.top;
  }

  if (values.right !== undefined) {
    bodyElement.style.paddingRight = values.right;
  }

  if (values.bottom !== undefined) {
    bodyElement.style.paddingBottom = values.bottom;
  }

  if (values.left !== undefined) {
    bodyElement.style.paddingLeft = values.left;
  }
};

/* harmony default export */ __webpack_exports__["default"] = ({
  setMargins: setMargins,
  setPadding: setPadding
});

/***/ }),

/***/ "./src/transform/CollapseTable.js":
/*!****************************************!*\
  !*** ./src/transform/CollapseTable.js ***!
  \****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _CollapseTable_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./CollapseTable.less */ "./src/transform/CollapseTable.less");
/* harmony import */ var _CollapseTable_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_CollapseTable_less__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _ElementUtilities__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./ElementUtilities */ "./src/transform/ElementUtilities.js");
/* harmony import */ var _NodeUtilities__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./NodeUtilities */ "./src/transform/NodeUtilities.js");
/* harmony import */ var _Polyfill__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js");
/* harmony import */ var _SectionUtilities__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./SectionUtilities */ "./src/transform/SectionUtilities.ts");
/* harmony import */ var _HTMLUtilities__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./HTMLUtilities */ "./src/transform/HTMLUtilities.ts");






var NODE_TYPE = _NodeUtilities__WEBPACK_IMPORTED_MODULE_2__["default"].NODE_TYPE;
var SECTION_TOGGLED_EVENT_TYPE = 'section-toggled';
var BREAKING_SPACE = ' ';
var CLASS = {
  ICON: 'pcs-collapse-table-icon',
  CONTAINER: 'pcs-collapse-table-container',
  CONTENT: 'pcs-collapse-table-content',
  COLLAPSED_CONTAINER: 'pcs-collapse-table-collapsed-container',
  COLLAPSED: 'pcs-collapse-table-collapsed',
  COLLAPSED_BOTTOM: 'pcs-collapse-table-collapsed-bottom',
  COLLAPSE_TEXT: 'pcs-collapse-table-collapse-text',
  EXPANDED: 'pcs-collapse-table-expanded',
  TABLE_INFOBOX: 'pcs-table-infobox',
  TABLE_OTHER: 'pcs-table-other',
  TABLE: 'pcs-collapse-table'
};
var ID = {
  ARIA_COLLAPSE: 'pcs-collapse-table-aria-collapse',
  ARIA_EXPAND: 'pcs-collapse-table-aria-expand'
};
var MATH_IMG_URL_PATH_REGEX = /\/math\/render\/svg\//;
/**
 * Determine if we want to extract text from this header.
 * @param {!Element} header
 * @return {!boolean}
 */

var isHeaderEligible = function isHeaderEligible(header) {
  return _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].querySelectorAll(header, 'a').length < 3;
};
/**
 * Determine eligibility of extracted text.
 * @param {?string} headerText
 * @return {!boolean}
 */


var isHeaderTextEligible = function isHeaderTextEligible(headerText) {
  return headerText && headerText.replace(/[\s0-9]/g, '').length > 0;
};
/**
 * Extracts first word from string. Returns null if for any reason it is unable to do so.
 * @param  {!string} string
 * @return {?string}
 */


var firstWordFromString = function firstWordFromString(string) {
  // 'If the global flag (g) is not set, Element zero of the array contains the entire match,
  // while elements 1 through n contain any submatches.'
  var matches = string.match(/\w+/); // Only need first match so not using 'g' option.

  if (!matches) {
    return undefined;
  }

  return matches[0];
};
/**
 * Is node's textContent too similar to pageTitle. Checks if the first word of the node's
 * textContent is found at the beginning of pageTitle.
 * @param  {!Node} node
 * @param  {!string} pageTitle
 * @return {!boolean}
 */


var isNodeTextContentSimilarToPageTitle = function isNodeTextContentSimilarToPageTitle(node, pageTitle) {
  var firstPageTitleWord = firstWordFromString(pageTitle);
  var firstNodeTextContentWord = firstWordFromString(node.textContent); // Don't claim similarity if 1st words were not extracted.

  if (!firstPageTitleWord || !firstNodeTextContentWord) {
    return false;
  }

  return firstPageTitleWord.toLowerCase() === firstNodeTextContentWord.toLowerCase();
};
/**
 * Removes leading and trailing whitespace and normalizes other whitespace - i.e. ensures
 * non-breaking spaces, tabs, etc are replaced with regular breaking spaces.
 * @param  {!string} string
 * @return {!string}
 */


var stringWithNormalizedWhitespace = function stringWithNormalizedWhitespace(string) {
  return string.trim().replace(/\s/g, BREAKING_SPACE);
};
/**
 * Determines if node is a BR.
 * @param  {!Node}  node
 * @return {!boolean}
 */


var isNodeBreakElement = function isNodeBreakElement(node) {
  return node.nodeType === NODE_TYPE.ELEMENT_NODE && node.tagName === 'BR';
};
/**
 * Replace node with a text node bearing a single breaking space.
 * @param {!Document} document
 * @param  {!Node} node
 * @return {void}
 */


var replaceNodeWithBreakingSpaceTextNode = function replaceNodeWithBreakingSpaceTextNode(document, node) {
  /* DOM sink status: safe - content transform with no user interference */
  node.parentNode.replaceChild(document.createTextNode(BREAKING_SPACE), node);
};
/**
 * Extracts any header text determined to be eligible.
 * @param {!Document} document
 * @param {!Element} header
 * @param {?string} pageTitle
 * @return {?string}
 */


var extractEligibleHeaderText = function extractEligibleHeaderText(document, header, pageTitle) {
  if (!isHeaderEligible(header)) {
    return null;
  } // Clone header into fragment. This is done so we can remove some elements we don't want
  // represented when "textContent" is used. Because we've cloned the header into a fragment, we are
  // free to strip out anything we want without worrying about affecting the visible document.


  var fragment = document.createDocumentFragment();
  fragment.appendChild(header.cloneNode(true));
  var fragmentHeader = fragment.querySelector('th');
  _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].querySelectorAll(fragmentHeader, '.geo, .coordinates, sup.mw-ref, ol, ul, style, script').forEach(function (el) {
    return el.remove();
  });
  var cur = fragmentHeader.lastChild;

  while (cur) {
    if (pageTitle && _NodeUtilities__WEBPACK_IMPORTED_MODULE_2__["default"].isNodeTypeElementOrText(cur) && isNodeTextContentSimilarToPageTitle(cur, pageTitle)) {
      if (cur.previousSibling) {
        cur = cur.previousSibling;
        cur.nextSibling.remove();
      } else {
        cur.remove();
        cur = undefined;
      }
    } else if (isNodeBreakElement(cur)) {
      replaceNodeWithBreakingSpaceTextNode(document, cur);
      cur = cur.previousSibling;
    } else {
      cur = cur.previousSibling;
    }
  }

  var headerText = fragmentHeader.textContent;

  if (isHeaderTextEligible(headerText)) {
    return stringWithNormalizedWhitespace(headerText);
  }

  return null;
};
/**
 * Find an array of table header (TH) contents. If there are no TH elements in
 * the table or the header's link matches pageTitle, an empty array is returned.
 * @param {!Document} document
 * @param {!Element} element
 * @param {?string} pageTitle Unencoded page title; if this title matches the
 *                            contents of the header exactly, it will be omitted.
 * @return {!Array<string>}
 */


var getTableHeaderTextArray = function getTableHeaderTextArray(document, element, pageTitle) {
  var headerTextArray = [];
  var walker = document.createTreeWalker(element);
  var header = walker.nextNode();

  while (header) {
    if (header.tagName !== 'TH') {
      header = walker.nextNode();
      continue;
    }

    var headerText = extractEligibleHeaderText(document, header, pageTitle);

    if (headerText && headerTextArray.indexOf(headerText) === -1) {
      headerTextArray.push(headerText); // 'newCaptionFragment' only ever uses the first 2 items.

      if (headerTextArray.length === 2) {
        break;
      }
    }

    header = walker.nextNode();
  }

  return headerTextArray;
};
/**
 * @typedef {function} FooterDivClickCallback
 * @param {!HTMLElement}
 * @return {void}
 */

/**
 * @param {!Element} container div
 * @param {?Element} trigger element that was clicked or tapped
 * @param {?FooterDivClickCallback} footerDivClickCallback
 * @param {?Element} expend/collapse tables
 * @return {boolean} true if collapsed, false if expanded.
 */


var toggleCollapsedForContainer = function toggleCollapsedForContainer(container, trigger, footerDivClickCallback, forceExpand) {
  var header = container.children[0];
  var table = container.children[1];
  var footer = container.children[2];
  var caption = header.querySelector('.pcs-collapse-table-aria');
  var collapsed = forceExpand == undefined ? table.style.display !== 'none' : !forceExpand;

  if (collapsed) {
    table.style.display = 'none';
    header.classList.remove(CLASS.COLLAPSED);
    header.classList.remove(CLASS.ICON);
    header.classList.add(CLASS.EXPANDED);

    if (caption) {
      caption.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_5__["ARIA"].LABELED_BY, ID.ARIA_EXPAND);
    }

    footer.style.display = 'none'; // if they clicked the bottom div, then scroll back up to the top of the table.

    if (trigger === footer && footerDivClickCallback) {
      footerDivClickCallback(container);
    }
  } else {
    table.style.display = 'block';
    header.classList.remove(CLASS.EXPANDED);
    header.classList.add(CLASS.COLLAPSED);
    header.classList.add(CLASS.ICON);

    if (caption) {
      caption.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_5__["ARIA"].LABELED_BY, ID.ARIA_COLLAPSE);
    }

    footer.style.display = 'block';
  }

  return collapsed;
};
/**
 * Ex: toggleCollapseClickCallback.bind(el, (container) => {
 *       window.scrollTo(0, container.offsetTop - transformer.getDecorOffset())
 *     })
 * @this HTMLElement
 * @param {?FooterDivClickCallback} footerDivClickCallback
 * @return {boolean} true if collapsed, false if expanded.
 */


var toggleCollapseClickCallback = function toggleCollapseClickCallback(footerDivClickCallback) {
  var container = this.parentNode;
  return toggleCollapsedForContainer(container, this, footerDivClickCallback);
};
/**
 * @param {!HTMLElement} table
 * @return {!boolean} true if table should be collapsed, false otherwise.
 */


var shouldTableBeCollapsed = function shouldTableBeCollapsed(table) {
  var disallowedClasses = ['navbox', 'vertical-navbox', 'navbox-inner', 'metadata', 'mbox-small'];
  var doesContainDisallowedClass = disallowedClasses.some(function (clazz) {
    return table.classList.contains(clazz);
  });
  var isHidden; // Wrap in a try-catch block to avoid Domino crashing on a malformed style declaration.
  // T229521

  try {
    isHidden = table.style.display === 'none';
  } catch (e) {
    // If Domino fails to parse styles, err on the safe side and don't transform
    isHidden = true;
  }

  return !isHidden && !doesContainDisallowedClass;
};
/**
 * @param {!Element} element
 * @return {!boolean} true if element is an infobox, false otherwise.
 */


var isInfobox = function isInfobox(element) {
  return element.classList.contains('infobox') || element.classList.contains('infobox_v3');
};
/**
 * @param {!Document} document
 * @param {!DocumentFragment} content
 * @return {!HTMLDivElement}
 */


var newCollapsedHeaderDiv = function newCollapsedHeaderDiv(document, content) {
  var div = document.createElement('div');
  div.classList.add(CLASS.COLLAPSED_CONTAINER);
  div.classList.add(CLASS.EXPANDED);
  /* DOM sink status: risk? - content come from newCaptionFragment which is potentially risky */

  div.appendChild(content);
  return div;
};
/**
 * @param {!Document} document
 * @param {?string} content HTML string.
 * @return {!HTMLDivElement}
 */


var newCollapsedFooterDiv = function newCollapsedFooterDiv(document, content) {
  var div = document.createElement('div');
  div.classList.add(CLASS.COLLAPSED_BOTTOM);
  div.classList.add(CLASS.ICON);
  /* DOM sink status: sanitized - footer title can be overridden by the client */

  div.textContent = content || '';
  return div;
};
/**
 * @param {!Array.<string>} headerText
 * @return {!HTMLElement}
*/


var transformHeaderText = function transformHeaderText(document, headerTextElem) {
  if (headerTextElem.match(MATH_IMG_URL_PATH_REGEX)) {
    var mathImgElem = document.createElement('img');
    mathImgElem.setAttribute('src', headerTextElem);
    return mathImgElem;
  } else {
    return document.createTextNode(headerTextElem);
  }
};
/**
 * @param {!Document} document
 * @param {!string} title
 * @param {!string} titleClass
 * @param {!Array.<string>} headerText
 * @param {string} collapseText Text for VoiceOver to read
 * @param {string} expandText Text for VoiceOver to read
 * @return {!DocumentFragment}
 */


var newCaptionFragment = function newCaptionFragment(document, title, titleClass, headerText, collapseText, expandText) {
  var fragment = document.createDocumentFragment();
  var strong = document.createElement('strong');
  /* DOM sink status: sanitized - title can be overridden by clients */

  strong.textContent = title;
  strong.classList.add(titleClass);
  fragment.appendChild(strong);
  var span = document.createElement('span');
  span.classList.add(CLASS.COLLAPSE_TEXT);

  if (headerText.length > 0) {
    /* DOM sink status: safe - content from parsoid output */
    span.appendChild(document.createTextNode(' '));
    span.appendChild(transformHeaderText(document, headerText[0]));
  }

  if (headerText.length > 1) {
    /* DOM sink status: safe - content from parsoid output */
    // Don't place a comma between text and formula right after it
    if (!headerText[0].match(MATH_IMG_URL_PATH_REGEX) && headerText[1].match(MATH_IMG_URL_PATH_REGEX)) {
      span.appendChild(document.createTextNode(' '));
    } else {
      span.appendChild(document.createTextNode(', '));
    }

    span.appendChild(transformHeaderText(document, headerText[1]));
  }

  if (headerText.length > 0) {
    /* DOM sink status: safe - content transform with no user interference */
    // As single character `…`, iOS's VoiceOver ignores this. As `...`, it reads it as "ellipsis". :facepalm:
    span.appendChild(document.createTextNode(' ...'));
  }
  /* DOM sink status: safe - content from parsoid output */


  fragment.appendChild(span); // While this should be on the actual caret, we'd need to make the caret it's own element
  // (rather than a backround image on the entire section) to read the action as well as the text.
  // For now, we're creating a new invisible element that is read by the screen reader.

  var ariaDescription = document.createElement('span');
  ariaDescription.classList.add('pcs-collapse-table-aria');
  ariaDescription.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_5__["ARIA"].LABELED_BY, ID.ARIA_EXPAND);
  ariaDescription.setAttribute('role', 'button');
  ariaDescription.setAttribute('display', 'none');
  ariaDescription.appendChild(document.createTextNode('')); // Check if it already exists from another table - only need once on entire document

  if (document.getElementById(ID.ARIA_EXPAND) === null) {
    var ariaDescriptionExpand = document.createElement('span');
    ariaDescriptionExpand.setAttribute('id', ID.ARIA_EXPAND);
    ariaDescriptionExpand.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_5__["ARIA"].LABEL, expandText);
    ariaDescription.appendChild(ariaDescriptionExpand);
  }

  if (document.getElementById(ID.ARIA_COLLAPSE) === null) {
    var ariaDescriptionCollapse = document.createElement('span');
    ariaDescriptionCollapse.setAttribute('id', ID.ARIA_COLLAPSE);
    ariaDescriptionCollapse.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_5__["ARIA"].LABEL, collapseText);
    ariaDescription.appendChild(ariaDescriptionCollapse);
  }

  fragment.appendChild(ariaDescription);
  return fragment;
};
/**
 * @param {!Node} nodeToReplace
 * @param {!Node} replacementNode
 * @return {void}
 */


var replaceNodeInSection = function replaceNodeInSection(nodeToReplace, replacementNode) {
  if (!nodeToReplace || !replacementNode) {
    return;
  }

  var childOfSectionTag = nodeToReplace;
  var sectionTag = nodeToReplace.parentNode;

  if (!sectionTag) {
    return;
  }

  var foundSectionTag = false;

  while (sectionTag) {
    if (_SectionUtilities__WEBPACK_IMPORTED_MODULE_4__["default"].isMediaWikiSectionElement(sectionTag)) {
      foundSectionTag = true;
      break;
    }

    childOfSectionTag = sectionTag;
    sectionTag = sectionTag.parentNode;
  }

  if (!foundSectionTag) {
    childOfSectionTag = nodeToReplace;
    sectionTag = nodeToReplace.parentNode;
  } // T279432 - Handle the case when the table is inside References list


  if (nodeToReplace.closest('.mw-references')) {
    var nodeToReplaceParent = nodeToReplace.parentNode;
    replacementNode.appendChild(nodeToReplace);
    nodeToReplaceParent.appendChild(replacementNode);
  } else {
    sectionTag.insertBefore(replacementNode, childOfSectionTag);
    sectionTag.removeChild(childOfSectionTag);
  }
};
/**
 * @param {!DOMElement} table
 * @param {!Document} document
 * @param {?string} pageTitle use title for this not `display title` (which can contain tags)
 * @param {?string} tableTitle title for the table
 * @param {?string} tableClass css class
 * @param {!Array<string>} headerTextArray array of header text strings
 * @param {?string} footerTitle
 * @param {string} collapseText Text for VoiceOver to read
 * @param {string} expandText Text for VoiceOver to read
 * @return {void}
 */


var prepareTable = function prepareTable(table, document, pageTitle, tableTitle, tableClass, headerTextArray, footerTitle, collapseText, expandText) {
  // create the container div that will contain both the original table
  // and the collapsed version.
  var containerDiv = document.createElement('div');
  containerDiv.className = CLASS.CONTAINER;
  replaceNodeInSection(table, containerDiv); // ensure the table doesn't float

  table.classList.add(CLASS.TABLE);
  var captionFragment; // pcs-collapse-table

  var collapsedHeaderDiv; // T252893 - Create filter to omit NavFrame class
  // Exlicitly set values of filter constants

  var FILTER_ACCEPT = 1;
  var FILTER_REJECT = 2;
  var SHOW_ELEMENT = '-1';
  var filterNavFrame = {
    acceptNode: function acceptNode(n) {
      return n && n.className && n.className.includes("NavFrame") ? FILTER_REJECT : FILTER_ACCEPT;
    }
  }; // Check if table has math elements and does not have text descrition for collapsing header
  // Omit tables with infobox class even if they have math elements. That kind of tables should display only text in the header

  if (!table.className.includes('infobox') && table.className.includes(CLASS.TABLE)) {
    var tableWalker = document.createTreeWalker(table, SHOW_ELEMENT, filterNavFrame);
    var mathAndTextArr = [];

    while (tableWalker.nextNode()) {
      if (tableWalker.currentNode && tableWalker.currentNode.className && tableWalker.currentNode.className.includes('mwe-math-fallback-image-inline')) {
        // Grab the text before math symbol if exists
        if (tableWalker.currentNode.parentNode && tableWalker.currentNode.parentNode.previousSibling) {
          var mathText = tableWalker.currentNode.parentNode.previousSibling.textContent.trim();
          mathAndTextArr.push(mathText);
        }

        var mathImgSrc = tableWalker.currentNode.getAttribute('src');
        mathAndTextArr.push(mathImgSrc);
        headerTextArray = mathAndTextArr;
      }
    }
  }

  captionFragment = newCaptionFragment(document, tableTitle, tableClass, headerTextArray, collapseText, expandText);
  collapsedHeaderDiv = newCollapsedHeaderDiv(document, captionFragment);
  collapsedHeaderDiv.style.display = 'block';
  var collapsedFooterDiv = newCollapsedFooterDiv(document, footerTitle);
  collapsedFooterDiv.style.display = 'none'; // add our stuff to the container

  /* DOM sink status: risk? - collapsedHeaderDiv is potentially risk */

  containerDiv.appendChild(collapsedHeaderDiv);
  /* DOM sink status: safe - content from parsoid output */
  // Add a wrapper div for content to allow for overflow scrolling

  var contentDiv = document.createElement('div');
  contentDiv.className = CLASS.CONTENT;
  contentDiv.appendChild(table);
  containerDiv.appendChild(contentDiv);
  /* DOM sink status: risk? - collapsedFooterDiv is potentially risk */

  containerDiv.appendChild(collapsedFooterDiv); // set initial visibility

  contentDiv.style.display = 'none';
};
/**
 * @param {!Document} document
 * @param {?string} pageTitle use title for this not `display title` (which can contain tags)
 * @param {?string} infoboxTitle
 * @param {?string} otherTitle
 * @param {?string} footerTitle
 * @return {void}
 */


var prepareTables = function prepareTables(document, pageTitle, infoboxTitle, otherTitle, footerTitle) {
  var tables = document.querySelectorAll('table, .infobox_v3');

  for (var i = 0; i < tables.length; ++i) {
    var table = tables[i];

    if (_ElementUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].findClosestAncestor(table, ".".concat(CLASS.CONTAINER)) || !shouldTableBeCollapsed(table)) {
      continue;
    }

    var isBox = isInfobox(table);
    var headerTextArray = getTableHeaderTextArray(document, table, pageTitle);

    if (!headerTextArray.length && !isBox) {
      continue;
    }

    var title = isBox ? infoboxTitle : otherTitle;
    var cls = isBox ? CLASS.TABLE_INFOBOX : CLASS.TABLE_OTHER;
    prepareTable(table, document, pageTitle, title, cls, headerTextArray, footerTitle);
  }
};
/**
 * @param {!Element} container root element to search from
 * @return {void}
 */


var toggleCollapsedForAll = function toggleCollapsedForAll(container) {
  var containerDivs = _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].querySelectorAll(container, ".".concat(CLASS.CONTAINER));
  containerDivs.forEach(function (containerDiv) {
    toggleCollapsedForContainer(containerDiv);
  });
};
/**
 * @param {!Element} container root element to search from
 * @param {!boolean} expand/collapse tables manually
 * @return {void}
 */


var expandOrCollapseTables = function expandOrCollapseTables(container, expand) {
  var containerDivs = _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].querySelectorAll(container, ".".concat(CLASS.CONTAINER));
  containerDivs.forEach(function (containerDiv) {
    toggleCollapsedForContainer(containerDiv, undefined, undefined, expand);
  });
};
/**
 * @param {!Window} window
 * @param {!Element} container root element to search from
 * @param {?boolean} isInitiallyCollapsed
 * @param {?FooterDivClickCallback} footerDivClickCallback
 * @return {void}
 */


var setupEventHandling = function setupEventHandling(window, container, isInitiallyCollapsed, footerDivClickCallback) {
  /**
   * @param {boolean} collapsed
   * @return {boolean}
   */
  var dispatchSectionToggledEvent = function dispatchSectionToggledEvent(collapsed) {
    return window.dispatchEvent(new _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].CustomEvent(SECTION_TOGGLED_EVENT_TYPE, {
      collapsed: collapsed
    }));
  }; // assign click handler to the collapsed divs


  var collapsedHeaderDivs = _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].querySelectorAll(container, ".".concat(CLASS.COLLAPSED_CONTAINER));
  collapsedHeaderDivs.forEach(function (collapsedHeaderDiv) {
    collapsedHeaderDiv.onclick = function () {
      var collapsed = toggleCollapseClickCallback.bind(collapsedHeaderDiv)();
      dispatchSectionToggledEvent(collapsed);
    };
  });
  var collapsedFooterDivs = _Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].querySelectorAll(container, ".".concat(CLASS.COLLAPSED_BOTTOM));
  collapsedFooterDivs.forEach(function (collapsedFooterDiv) {
    collapsedFooterDiv.onclick = function () {
      var collapsed = toggleCollapseClickCallback.bind(collapsedFooterDiv, footerDivClickCallback)();
      dispatchSectionToggledEvent(collapsed);
    };
  });

  if (!isInitiallyCollapsed) {
    toggleCollapsedForAll(container);
  }
};
/**
 * @param {!Window} window
 * @param {!Document} document
 * @param {?string} pageTitle use title for this not `display title` (which can contain tags)
 * @param {?boolean} isMainPage
 * @param {?boolean} isInitiallyCollapsed
 * @param {?string} infoboxTitle
 * @param {?string} otherTitle
 * @param {?string} footerTitle
 * @param {?FooterDivClickCallback} footerDivClickCallback
 * @return {void}
 */


var adjustTables = function adjustTables(window, document, pageTitle, isMainPage, isInitiallyCollapsed, infoboxTitle, otherTitle, footerTitle, footerDivClickCallback) {
  if (isMainPage) {
    return;
  }

  prepareTables(document, pageTitle, infoboxTitle, otherTitle, footerTitle);
  setupEventHandling(window, document, isInitiallyCollapsed, footerDivClickCallback);
};
/**
 * @param {!Window} window
 * @param {!Document} document
 * @param {?string} pageTitle use title for this not `display title` (which can contain tags)
 * @param {?boolean} isMainPage
 * @param {?string} infoboxTitle
 * @param {?string} otherTitle
 * @param {?string} footerTitle
 * @param {?FooterDivClickCallback} footerDivClickCallback
 * @return {void}
 */


var collapseTables = function collapseTables(window, document, pageTitle, isMainPage, infoboxTitle, otherTitle, footerTitle, footerDivClickCallback) {
  adjustTables(window, document, pageTitle, isMainPage, true, infoboxTitle, otherTitle, footerTitle, footerDivClickCallback);
};
/**
 * If you tap a reference targeting an anchor within a collapsed table, this
 * method will expand the references section. The client can then scroll to the
 * references section.
 *
 * The first reference (an "[A]") in the "enwiki > Airplane" article from ~June
 * 2016 exhibits this issue. (You can copy wikitext from this revision into a
 * test wiki page for testing.)
 * @param  {?Element} element
 * @return {void}
*/


var expandCollapsedTableIfItContainsElement = function expandCollapsedTableIfItContainsElement(element) {
  if (element) {
    var containerSelector = "[class*=\"".concat(CLASS.CONTAINER, "\"]");
    var container = _ElementUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].findClosestAncestor(element, containerSelector);

    if (container) {
      var collapsedDiv = container.firstElementChild;

      if (collapsedDiv && collapsedDiv.classList.contains(CLASS.EXPANDED)) {
        collapsedDiv.click();
      }
    }
  }
};

/* harmony default export */ __webpack_exports__["default"] = ({
  CLASS: CLASS,
  SECTION_TOGGLED_EVENT_TYPE: SECTION_TOGGLED_EVENT_TYPE,
  toggleCollapsedForAll: toggleCollapsedForAll,
  toggleCollapseClickCallback: toggleCollapseClickCallback,
  expandOrCollapseTables: expandOrCollapseTables,
  collapseTables: collapseTables,
  getTableHeaderTextArray: getTableHeaderTextArray,
  adjustTables: adjustTables,
  prepareTables: prepareTables,
  prepareTable: prepareTable,
  setupEventHandling: setupEventHandling,
  expandCollapsedTableIfItContainsElement: expandCollapsedTableIfItContainsElement,
  test: {
    extractEligibleHeaderText: extractEligibleHeaderText,
    firstWordFromString: firstWordFromString,
    shouldTableBeCollapsed: shouldTableBeCollapsed,
    isHeaderEligible: isHeaderEligible,
    isHeaderTextEligible: isHeaderTextEligible,
    isInfobox: isInfobox,
    newCollapsedHeaderDiv: newCollapsedHeaderDiv,
    newCollapsedFooterDiv: newCollapsedFooterDiv,
    newCaptionFragment: newCaptionFragment,
    isNodeTextContentSimilarToPageTitle: isNodeTextContentSimilarToPageTitle,
    stringWithNormalizedWhitespace: stringWithNormalizedWhitespace,
    replaceNodeWithBreakingSpaceTextNode: replaceNodeWithBreakingSpaceTextNode,
    getTableHeaderTextArray: getTableHeaderTextArray
  }
});

/***/ }),

/***/ "./src/transform/CollapseTable.less":
/*!******************************************!*\
  !*** ./src/transform/CollapseTable.less ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/CollectionUtilities.js":
/*!**********************************************!*\
  !*** ./src/transform/CollectionUtilities.js ***!
  \**********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _Polyfill__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js");

/**
 * Extracts array of page issues from element
 * @param {!Document} document
 * @return {!Array.<string>} Return empty array if nothing is extracted
 */

var collectPageIssueElements = function collectPageIssueElements(document) {
  if (!document) {
    return [];
  }

  return _Polyfill__WEBPACK_IMPORTED_MODULE_0__["default"].querySelectorAll(document, '.mbox-text-span').map(function (element) {
    _Polyfill__WEBPACK_IMPORTED_MODULE_0__["default"].querySelectorAll(element, '.hide-when-compact, .collapsed').forEach(function (el) {
      return el.remove();
    });
    return element;
  });
};
/**
 * Returns section JSON for an element
 * @param {!Element} element
 * @return {!map} section info
 */


var sectionJSON = function sectionJSON(element) {
  var section = element.closest('section[data-mw-section-id]');
  var headerEl = section && section.querySelector('h1,h2,h3,h4,h5,h6');
  return {
    id: section && parseInt(section.getAttribute('data-mw-section-id'), 10),

    /* DOM sink status: safe - content transform with no user interference */
    title: headerEl && headerEl.innerHTML.trim(),
    anchor: headerEl && headerEl.getAttribute('id')
  };
};
/**
 * Extracts array of page issues from element
 * @param {!Document} document
 * @return {!Array.<Object>} Return empty array if nothing is extracted
 */


var collectPageIssues = function collectPageIssues(document) {
  return collectPageIssueElements(document).map(function (el) {
    return {
      html: el.innerHTML.trim(),
      section: sectionJSON(el)
    };
  });
};
/**
 * Extracts array of hatnotes from an element
 * @param {?Element} element
 * @return {!Array.<Object>} Return empty array if nothing is extracted
 */


var collectHatnotes = function collectHatnotes(element) {
  if (!element) {
    return [];
  }

  return _Polyfill__WEBPACK_IMPORTED_MODULE_0__["default"].querySelectorAll(element, 'div.hatnote').map(function (element) {
    var titles = _Polyfill__WEBPACK_IMPORTED_MODULE_0__["default"].querySelectorAll(element, 'div.hatnote a[href]:not([href=""]):not([redlink="1"])').map(function (el) {
      return el.href;
    });
    return {
      html: element.innerHTML.trim(),
      links: titles,
      section: sectionJSON(element)
    };
  });
};

/* harmony default export */ __webpack_exports__["default"] = ({
  collectHatnotes: collectHatnotes,
  collectPageIssues: collectPageIssues,
  test: {
    collectPageIssueElements: collectPageIssueElements
  }
});

/***/ }),

/***/ "./src/transform/DimImagesTransform.js":
/*!*********************************************!*\
  !*** ./src/transform/DimImagesTransform.js ***!
  \*********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _DimImagesTransform_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./DimImagesTransform.less */ "./src/transform/DimImagesTransform.less");
/* harmony import */ var _DimImagesTransform_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_DimImagesTransform_less__WEBPACK_IMPORTED_MODULE_0__);

var CLASS = 'pcs-dim-images';
/**
 * @param {!Document} document
 * @param {!boolean} enable
 * @return {void}
 */

var dimImages = function dimImages(document, enable) {
  document.body.classList[enable ? 'add' : 'remove'](CLASS);
};
/**
 * @deprecated Use dimImages instead, which only requires a Document
 * @param {!Window} window
 * @param {!boolean} enable
 * @return {void}
 */


var dim = function dim(window, enable) {
  return dimImages(window.document, enable);
};
/**
 * @param {!Document} document
 * @return {boolean}
 */


var areImagesDimmed = function areImagesDimmed(document) {
  return document.body.classList.contains(CLASS);
};
/**
 * @deprecated Use areImagesDimmed instead, which only requires a Document
 * @param {!Window} window
 * @return {boolean}
 */


var isDim = function isDim(window) {
  return areImagesDimmed(window.document);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  CLASS: CLASS,
  dim: dim,
  isDim: isDim,
  dimImages: dimImages,
  areImagesDimmed: areImagesDimmed
});

/***/ }),

/***/ "./src/transform/DimImagesTransform.less":
/*!***********************************************!*\
  !*** ./src/transform/DimImagesTransform.less ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/EditTransform.js":
/*!****************************************!*\
  !*** ./src/transform/EditTransform.js ***!
  \****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _EditTransform_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./EditTransform.less */ "./src/transform/EditTransform.less");
/* harmony import */ var _EditTransform_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_EditTransform_less__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./HTMLUtilities */ "./src/transform/HTMLUtilities.ts");


var CLASS = {
  SECTION_HEADER: 'pcs-edit-section-header',
  TITLE: 'pcs-edit-section-title',
  HEADER_INNER_LEFT: 'pcs-header-inner-left',
  HEADER_INNER_RIGHT: 'pcs-header-inner-right',
  LINK_CONTAINER: 'pcs-edit-section-link-container',
  LINK: 'pcs-edit-section-link',
  PROTECTION: {
    UNPROTECTED: '',
    PROTECTED: 'page-protected',
    FORBIDDEN: 'no-editing'
  },
  TITLE_TALK_BUTTON: 'pcs-title-icon-talk-page',
  TITLE_TALK_BUTTON_WRAPPER: 'pcs-title-icon-talk-page-container'
};
var IDS = {
  TITLE_DESCRIPTION: 'pcs-edit-section-title-description',
  ADD_TITLE_DESCRIPTION: 'pcs-edit-section-add-title-description',
  DIVIDER: 'pcs-edit-section-divider',
  PRONUNCIATION: 'pcs-edit-section-title-pronunciation',
  ARIA_EDIT_PROTECTED: 'pcs-edit-section-aria-protected',
  ARIA_EDIT_NORMAL: 'pcs-edit-section-aria-normal'
};
var DATA_ATTRIBUTE = {
  SECTION_INDEX: 'data-id',
  ACTION: 'data-action',
  PRONUNCIATION_URL: 'data-pronunciation-url',
  DESCRIPTION_SOURCE: 'data-description-source',
  WIKIDATA_ENTITY_ID: 'data-wikdata-entity-id'
};
var ACTION_EDIT_SECTION = 'edit_section';
var ACTION_TITLE_PRONUNCIATION = 'title_pronunciation';
var ACTION_ADD_TITLE_DESCRIPTION = 'add_title_description';
/**
 * Enables edit buttons to be shown (and which ones: protected or regular).
 * @param {!HTMLDocument} document
 * @param {?boolean} isEditable true if edit buttons should be shown
 * @param {?boolean} isProtected true if the protected edit buttons should be shown
 * @return {void}
 */

var setEditButtons = function setEditButtons(document) {
  var isEditable = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
  var isProtected = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : false;
  var classList = document.documentElement.classList;

  if (isEditable) {
    classList.remove(CLASS.PROTECTION.FORBIDDEN);
  } else {
    classList.add(CLASS.PROTECTION.FORBIDDEN);
  }

  if (isProtected) {
    classList.add(CLASS.PROTECTION.PROTECTED);
  } else {
    classList.remove(CLASS.PROTECTION.PROTECTED);
  }
};
/**
 * Enables header title icon buttons to be shown.
 * @param {!Document} document
 * @param {?boolean} isVisible true if the title icon should be shown
 * @return {void}
 */


var setTalkPageButton = function setTalkPageButton(document) {
  var isVisible = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;
  var header = document.getElementsByTagName('header')[0],
      rightWrapElem = header.getElementsByClassName(CLASS.HEADER_INNER_RIGHT)[0],
      isRendered = header.getElementsByClassName(CLASS.TITLE_TALK_BUTTON)[0] && header.getElementsByClassName(CLASS.TITLE_TALK_BUTTON_WRAPPER)[0];

  if (isVisible) {
    if (!isRendered) {
      var talkButtonWrapper = document.createElement('span'),
          talkButton = document.createElement('a');
      talkButton.setAttribute('href', '/');
      talkButton.classList.add(CLASS.TITLE_TALK_BUTTON);
      talkButtonWrapper.classList.add(CLASS.TITLE_TALK_BUTTON_WRAPPER);
      rightWrapElem.appendChild(talkButtonWrapper);
      talkButtonWrapper.appendChild(talkButton);
    }
  } else {
    if (isRendered) {
      header.getElementsByClassName(CLASS.TITLE_TALK_BUTTON_WRAPPER)[0].remove();
    }
  }
};
/**
 * Sets appropriate label for VoiceOver to read for edit buttons. Defaults to normal, so only need to check if it's protected.
 * @param {!HTMLDocument} document
 * @return {void}
 */


var setARIAEditButtons = function setARIAEditButtons(document) {
  if (document.documentElement.classList.contains(CLASS.PROTECTION.PROTECTED)) {
    Array.from(document.getElementsByClassName(CLASS.LINK)).forEach(function (link) {
      return link.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["ARIA"].LABELED_BY, IDS.ARIA_EDIT_PROTECTED);
    });
  }
};
/**
 * @param {!Document} document
 * @param {!number} index The zero-based index of the section.
 * @param {!string} href The href for the link
 * @return {!HTMLAnchorElement}
 */


var newEditSectionLink = function newEditSectionLink(document, index) {
  var href = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
  var link = document.createElement('a');
  link.href = href;
  link.setAttribute(DATA_ATTRIBUTE.SECTION_INDEX, index);
  link.setAttribute(DATA_ATTRIBUTE.ACTION, ACTION_EDIT_SECTION);
  link.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["ARIA"].LABELED_BY, IDS.ARIA_EDIT_NORMAL);
  link.classList.add(CLASS.LINK);
  return link;
};
/**
 * @param {!Document} document
 * @param {!number} index The zero-based index of the section.
 * @param {!HTMLElement} link The link element
 * @param {?string} normalAriaLabel
 * @param {?string} protectedAriaLabel
 * @return {!HTMLSpanElement}
 */


var newEditSectionButton = function newEditSectionButton(document, index, link, normalAriaLabel, protectedAriaLabel) {
  var container = document.createElement('span');
  container.classList.add(CLASS.LINK_CONTAINER);

  if (document.getElementById(IDS.ARIA_EDIT_NORMAL) === null && normalAriaLabel) {
    var ariaDescriptionNormal = document.createElement('span');
    ariaDescriptionNormal.setAttribute('id', IDS.ARIA_EDIT_NORMAL);
    ariaDescriptionNormal.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["ARIA"].LABEL, normalAriaLabel);
    container.appendChild(ariaDescriptionNormal);
  }

  if (document.getElementById(IDS.ARIA_EDIT_PROTECTED) === null && protectedAriaLabel) {
    var ariaDescriptionProtected = document.createElement('span');
    ariaDescriptionProtected.setAttribute('id', IDS.ARIA_EDIT_PROTECTED);
    ariaDescriptionProtected.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["ARIA"].LABEL, protectedAriaLabel);
    container.appendChild(ariaDescriptionProtected);
  }

  var actualLink = link;

  if (!actualLink) {
    actualLink = newEditSectionLink(document, index);
  }
  /* DOM sink status: safe - content transform with no user interference */


  container.appendChild(actualLink);
  return container;
};
/**
 * @param {!Document} document
 * @param {!number} index The zero-based index of the section.
 * @return {!HTMLDivElement}
 */


var newEditSectionWrapper = function newEditSectionWrapper(document, index) {
  var element = document.createElement('div');
  element.classList.add(CLASS.SECTION_HEADER);
  element.classList.add('v2');
  return element;
};
/**
 * @param {!HTMLDivElement} wrapper
 * @param {!HTMLElement} header The header element.
 * @return {void}
 */


var appendEditSectionHeader = function appendEditSectionHeader(wrapper, header) {
  header.classList.add(CLASS.TITLE);
  /* DOM sink status: safe - content transform with no user interference */

  wrapper.appendChild(header);
};
/**
 * @param {!Document} document
 * @param {!number} index The zero-based index of the section.
 * @param {!number} level The *one-based* header or table of contents level.
 * @param {?string} titleHTML Title of this section header.
 * @return {!HTMLElement}
 */


var newEditSectionHeader = function newEditSectionHeader(document, index, level, titleHTML) {
  var element = newEditSectionWrapper(document, index);
  var title = document.createElement("h".concat(level));
  /* DOM sink status: safe - Displaytitle is sanitized in CoreParserHooks::displaytitle by MW
    OBS: if titleHTML is escaped it will cause a regression of T242028 */

  title.innerHTML = titleHTML || '';
  title.setAttribute(DATA_ATTRIBUTE.SECTION_INDEX, index);
  appendEditSectionHeader(element, title);
  return element;
};
/**
 * Elements needed to show or add page title description.
 * @param {!Document} document
 * @param {?string} titleDescription Page title description.
 * @param {?string} titleDescriptionSource
 * @param {?string} wikidataEntityID
 * @param {?string} addTitleDescriptionString Localized string e.g. 'Add title description'.
 * @param {?boolean} isTitleDescriptionEditable Whether title description is editable.
 * @return {?HTMLElement}
 */


var titleDescriptionElements = function titleDescriptionElements(document, titleDescription, titleDescriptionSource, wikidataEntityID, addTitleDescriptionString, isTitleDescriptionEditable) {
  var descriptionExists = titleDescription !== undefined && titleDescription.length > 0;

  if (descriptionExists) {
    var p = document.createElement('p');
    p.setAttribute(DATA_ATTRIBUTE.DESCRIPTION_SOURCE, titleDescriptionSource);
    p.setAttribute(DATA_ATTRIBUTE.WIKIDATA_ENTITY_ID, wikidataEntityID);
    p.id = IDS.TITLE_DESCRIPTION;
    p.innerHTML = titleDescription;
    return p;
  }

  if (isTitleDescriptionEditable) {
    var a = document.createElement('a');
    a.href = '#';
    a.setAttribute(DATA_ATTRIBUTE.ACTION, ACTION_ADD_TITLE_DESCRIPTION);

    var _p = document.createElement('p');

    _p.id = IDS.ADD_TITLE_DESCRIPTION;
    _p.innerHTML = addTitleDescriptionString;
    a.appendChild(_p);
    return a;
  }

  return null;
};
/**
 * Adds page title, description, and optional pronunciation. The description can be editable.
 * @param {!Document} document
 * @param {?string} pageDisplayTitle Page display title.
 * @param {?string} titleDescription Page title description.
 * @param {?string} titleDescriptionSource Page title description source - "central" or "local".
 * @param {?string} wikidataEntityID wikidata entity ID
 * @param {?string} addTitleDescriptionString Localized string e.g. 'Add title description'.
 * @param {?boolean} isTitleDescriptionEditable Whether title description is editable.
 * @param {?string} pronunciationURL URL for the pronunciation - will show the speaker when provided.
 * @return {!HTMLElement}
 */


var newPageHeader = function newPageHeader(document, pageDisplayTitle, titleDescription, titleDescriptionSource, wikidataEntityID, addTitleDescriptionString, isTitleDescriptionEditable, pronunciationURL) {
  var container = document.createDocumentFragment();
  var header = newEditSectionHeader(document, 0, 1, pageDisplayTitle);

  if (pronunciationURL) {
    var a = document.createElement('a');
    a.setAttribute(DATA_ATTRIBUTE.ACTION, ACTION_TITLE_PRONUNCIATION);
    a.setAttribute(DATA_ATTRIBUTE.PRONUNCIATION_URL, pronunciationURL);
    a.id = IDS.PRONUNCIATION;
    header.querySelector('h1').appendChild(a);
  }

  container.appendChild(header);
  var leftWrapElem = document.createElement('div'),
      rightWrapElem = document.createElement('div');
  leftWrapElem.classList.add(CLASS.HEADER_INNER_LEFT);
  rightWrapElem.classList.add(CLASS.HEADER_INNER_RIGHT);
  var headerTitle = header.getElementsByTagName('h1')[0];
  headerTitle.parentNode.insertBefore(leftWrapElem, headerTitle);
  leftWrapElem.appendChild(headerTitle);
  header.appendChild(rightWrapElem);
  var descriptionElements = titleDescriptionElements(document, titleDescription, titleDescriptionSource, wikidataEntityID, addTitleDescriptionString, isTitleDescriptionEditable);

  if (descriptionElements) {
    leftWrapElem.appendChild(descriptionElements);
  }

  var divider = document.createElement('hr');
  divider.id = IDS.DIVIDER;
  leftWrapElem.appendChild(divider);
  return container;
};

/* harmony default export */ __webpack_exports__["default"] = ({
  appendEditSectionHeader: appendEditSectionHeader,
  CLASS: CLASS,
  IDS: IDS,
  DATA_ATTRIBUTE: DATA_ATTRIBUTE,
  setEditButtons: setEditButtons,
  setTalkPageButton: setTalkPageButton,
  setARIAEditButtons: setARIAEditButtons,
  newEditSectionHeader: newEditSectionHeader,
  newEditSectionButton: newEditSectionButton,
  newEditSectionWrapper: newEditSectionWrapper,
  newEditSectionLink: newEditSectionLink,
  newPageHeader: newPageHeader
});

/***/ }),

/***/ "./src/transform/EditTransform.less":
/*!******************************************!*\
  !*** ./src/transform/EditTransform.less ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/ElementGeometry.js":
/*!******************************************!*\
  !*** ./src/transform/ElementGeometry.js ***!
  \******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "default", function() { return ElementGeometry; });
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @babel/runtime/helpers/createClass */ "./node_modules/@babel/runtime/helpers/createClass.js");
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__);


// separate this out so it is only compiled once
var styleRegex = /(-?\d*\.?\d*)(\D+)?/;
/** CSS length value and unit of measure. */

var DimensionUnit = /*#__PURE__*/function () {
  /**
   * @param {!string} value
   * @param {?string} unit Defaults to pixels.
   */
  function DimensionUnit(value, unit) {
    _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, DimensionUnit);

    this._value = Number(value);
    this._unit = unit || 'px';
  }
  /** @return {!number} NaN if unknown. */


  _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default()(DimensionUnit, [{
    key: "value",
    get: function get() {
      return this._value;
    }
    /** @return {!string} */

  }, {
    key: "unit",
    get: function get() {
      return this._unit;
    }
    /** @return {!string} */

  }, {
    key: "toString",
    value: function toString() {
      return isNaN(this.value) ? '' : "".concat(this.value).concat(this.unit);
    }
  }], [{
    key: "fromElement",
    value:
    /**
     * Returns the dimension and units of an Element, usually width or height, as specified by inline
     * style or attribute. This is a pragmatic not bulletproof implementation.
     * @param {!Element} element
     * @param {!string} property
     * @return {?DimensionUnit}
     */
    function fromElement(element, property) {
      return element.style.getPropertyValue(property) && DimensionUnit.fromStyle(element.style.getPropertyValue(property)) || element.hasAttribute(property) && new DimensionUnit(element.getAttribute(property)) || undefined;
    }
    /**
     * This is a pragmatic not bulletproof implementation.
     * @param {!string} property
     * @return {!DimensionUnit}
     */

  }, {
    key: "fromStyle",
    value: function fromStyle(property) {
      var matches = property.match(styleRegex) || [];
      return new DimensionUnit(matches[1], matches[2]);
    }
  }]);

  return DimensionUnit;
}();
/** Element width and height dimensions and units. */


var ElementGeometry = /*#__PURE__*/function () {
  /**
   * @param {?DimensionUnit} width
   * @param {?DimensionUnit} height
   */
  function ElementGeometry(width, height) {
    _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, ElementGeometry);

    this._width = width;
    this._height = height;
  }
  /**
   * @return {?DimensionUnit}
   */


  _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default()(ElementGeometry, [{
    key: "width",
    get: function get() {
      return this._width;
    }
    /** @return {!number} NaN if unknown. */

  }, {
    key: "widthValue",
    get: function get() {
      return this._width && !isNaN(this._width.value) ? this._width.value : NaN;
    }
    /** @return {!string} */

  }, {
    key: "widthUnit",
    get: function get() {
      return this._width && this._width.unit || 'px';
    }
    /**
     * @return {?DimensionUnit}
     */

  }, {
    key: "height",
    get: function get() {
      return this._height;
    }
    /** @return {!number} NaN if unknown. */

  }, {
    key: "heightValue",
    get: function get() {
      return this._height && !isNaN(this._height.value) ? this._height.value : NaN;
    }
    /** @return {!string} */

  }, {
    key: "heightUnit",
    get: function get() {
      return this._height && this._height.unit || 'px';
    }
  }], [{
    key: "from",
    value:
    /**
     * @param {!Element} element
     * @return {!ElementGeometry}
     */
    function from(element) {
      return new ElementGeometry(DimensionUnit.fromElement(element, 'width'), DimensionUnit.fromElement(element, 'height'));
    }
  }]);

  return ElementGeometry;
}();



/***/ }),

/***/ "./src/transform/ElementUtilities.js":
/*!*******************************************!*\
  !*** ./src/transform/ElementUtilities.js ***!
  \*******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _Polyfill__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js");
 // todo: drop ancestor consideration and move to Polyfill.closest().

/**
 * Returns closest ancestor of element which matches selector.
 * Similar to 'closest' methods as seen here:
 *  https://api.jquery.com/closest/
 *  https://developer.mozilla.org/en-US/docs/Web/API/Element/closest
 * @param  {!Element} el        Element
 * @param  {!string} selector   Selector to look for in ancestors of 'el'
 * @return {?HTMLElement}       Closest ancestor of 'el' matching 'selector'
 */

var findClosestAncestor = function findClosestAncestor(el, selector) {
  var parentElement;

  for (parentElement = el.parentElement; parentElement && !_Polyfill__WEBPACK_IMPORTED_MODULE_0__["default"].matchesSelector(parentElement, selector); parentElement = parentElement.parentElement) {// Intentionally empty.
  }

  return parentElement;
};
/**
 * @param {?Element} element
 * @param {!string} property
 * @param {?string} value
 * @return {?Element} The inclusive first element with an inline style (and optional value) or
 * undefined.
 */


var closestInlineStyle = function closestInlineStyle(element, property, value) {
  for (var el = element; el; el = el.parentElement) {
    var thisValue = void 0; // Wrap in a try-catch block to avoid Domino crashing on a malformed style declaration.
    // T229521

    try {
      thisValue = el.style[property];
    } catch (e) {
      continue;
    }

    if (thisValue) {
      if (value === undefined) {
        return el;
      }

      if (value === thisValue) {
        return el;
      }
    }
  }

  return undefined;
};
/**
 * Determines if element has a table ancestor.
 * @param  {!Element}  el   Element
 * @return {!boolean}       Whether table ancestor of 'el' is found
 */


var isNestedInTable = function isNestedInTable(el) {
  return Boolean(findClosestAncestor(el, 'table'));
};
/**
 * @param {!HTMLElement} element
 * @return {!boolean} true if element affects layout, false otherwise.
 */


var isVisible = function isVisible(element) {
  return (// https://github.com/jquery/jquery/blob/305f193/src/css/hiddenVisibleSelectors.js#L12
    Boolean(element.offsetWidth || element.offsetHeight || element.getClientRects().length)
  );
};
/**
 * Copy existing attributes from source to destination as data-* attributes.
 * @param {!HTMLElement} source
 * @param {!HTMLElement} destination
 * @param {!Array.<string>} attributes
 * @return {void}
 */


var copyAttributesToDataAttributes = function copyAttributesToDataAttributes(source, destination, attributes) {
  attributes.filter(function (attribute) {
    return source.hasAttribute(attribute);
  }).forEach(function (attribute) {
    return destination.setAttribute("data-".concat(attribute), source.getAttribute(attribute));
  });
};
/**
 * Copy existing data-* attributes from source to destination as attributes.
 * @param {!HTMLElement} source
 * @param {!HTMLElement} destination
 * @param {!Array.<string>} attributes
 * @return {void}
 */


var copyDataAttributesToAttributes = function copyDataAttributesToAttributes(source, destination, attributes) {
  attributes.filter(function (attribute) {
    return source.hasAttribute("data-".concat(attribute));
  }).forEach(function (attribute) {
    return destination.setAttribute(attribute, source.getAttribute("data-".concat(attribute)));
  });
};

/* harmony default export */ __webpack_exports__["default"] = ({
  findClosestAncestor: findClosestAncestor,
  isNestedInTable: isNestedInTable,
  closestInlineStyle: closestInlineStyle,
  isVisible: isVisible,
  copyAttributesToDataAttributes: copyAttributesToDataAttributes,
  copyDataAttributesToAttributes: copyDataAttributesToAttributes
});

/***/ }),

/***/ "./src/transform/FooterContainer.js":
/*!******************************************!*\
  !*** ./src/transform/FooterContainer.js ***!
  \******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _FooterContainer_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./FooterContainer.less */ "./src/transform/FooterContainer.less");
/* harmony import */ var _FooterContainer_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_FooterContainer_less__WEBPACK_IMPORTED_MODULE_0__);

/**
 * Returns a fragment containing structural footer html which may be inserted where needed.
 * @param {!Document} document
 * @return {!DocumentFragment}
 */

var containerFragment = function containerFragment(document) {
  var containerFragment = document.createDocumentFragment();
  var menuSection = document.createElement('section');
  menuSection.id = 'pcs-footer-container-menu';
  menuSection.className = 'pcs-footer-section';
  menuSection.innerHTML = "<h2 id='pcs-footer-container-menu-heading'></h2>\n   <div id='pcs-footer-container-menu-items'></div>";
  /* DOM sink status: risk? */

  containerFragment.appendChild(menuSection);
  var readMoreSection = document.createElement('section');
  readMoreSection.id = 'pcs-footer-container-readmore';
  readMoreSection.className = 'pcs-footer-section';
  readMoreSection.style.display = 'none';
  readMoreSection.innerHTML = "<h2 id='pcs-footer-container-readmore-heading'></h2>\n   <div id='pcs-footer-container-readmore-pages'></div>";
  /* DOM sink status: risk? */

  containerFragment.appendChild(readMoreSection);
  var legalSection = document.createElement('section');
  legalSection.id = 'pcs-footer-container-legal';
  /* DOM sink status: risk? */

  containerFragment.appendChild(legalSection);
  return containerFragment;
};
/**
 * Indicates whether container is has already been added.
 * @param {!Document} document
 * @return {boolean}
 */


var isContainerAttached = function isContainerAttached(document) {
  return Boolean(document.querySelector('#pcs-footer-container'));
};

/* harmony default export */ __webpack_exports__["default"] = ({
  containerFragment: containerFragment,
  isContainerAttached: isContainerAttached // todo: rename isAttached()?

});

/***/ }),

/***/ "./src/transform/FooterContainer.less":
/*!********************************************!*\
  !*** ./src/transform/FooterContainer.less ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/FooterLegal.js":
/*!**************************************!*\
  !*** ./src/transform/FooterLegal.js ***!
  \**************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _FooterLegal_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./FooterLegal.less */ "./src/transform/FooterLegal.less");
/* harmony import */ var _FooterLegal_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_FooterLegal_less__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../transform/HTMLUtilities */ "./src/transform/HTMLUtilities.ts");


/**
 * @typedef {function} FooterLegalClickCallback
 * @return {void}
 */

/**
  * @typedef {function} FooterBrowserClickCallback
  * @return {void}
  */

/**
 * @param {!string} licenseString
 * @param {?string} linkText
 * @return {!string}
 */

var buildLicenseHtml = function buildLicenseHtml(licenseString, linkText) {
  var halves = licenseString.split('$1');
  /* DOM sink status: sanitized - content can be changed by users */

  return "".concat(_transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].escape(halves[0]), "<a class=\"external text\" rel=\"mw:ExtLink\" href=\"https://creativecommons.org/licenses/by-sa/3.0/\">").concat(_transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].escape(linkText), "</a>").concat(_transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].escape(halves[1]));
};
/**
 * Adds legal footer html to 'containerID' element.
 * @param {!Element} content
 * @param {?string} licenseString
 * @param {?string} licenseSubstitutionString
 * @param {!string} containerID
 * @param {!string} viewInBrowserString
 * @param {!FooterBrowserClickCallback} browserLinkClickHandler
 * @return {void}
 */


var add = function add(content, licenseString, licenseSubstitutionString, containerID, viewInBrowserString, browserLinkClickHandler) {
  // todo: don't manipulate the selector. The client can make this an ID if they want it to be.
  var container = content.querySelector("#".concat(containerID));
  /* DOM sink status: sanitized - content can be changed by users */
  // pcs-footer-browser-link anchor tag: href w/ fake content so iOS's VoiceOver reads it as an unvisited link

  container.innerHTML = "<div class='pcs-footer-legal-contents'>\n    <hr class='pcs-footer-legal-divider'>\n    <span class='pcs-footer-legal-license'>\n    ".concat(buildLicenseHtml(licenseString, licenseSubstitutionString), "\n    <br>\n      <div class=\"pcs-footer-browser\">\n        <a class='pcs-footer-browser-link' href='N/A'>\n          ").concat(_transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].escape(viewInBrowserString), "\n        </a>\n      </div>\n    </span>\n  </div>");
  container.querySelector('.pcs-footer-browser-link').addEventListener('click', function () {
    browserLinkClickHandler();
  });
};

/* harmony default export */ __webpack_exports__["default"] = ({
  add: add
});

/***/ }),

/***/ "./src/transform/FooterLegal.less":
/*!****************************************!*\
  !*** ./src/transform/FooterLegal.less ***!
  \****************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/FooterMenu.js":
/*!*************************************!*\
  !*** ./src/transform/FooterMenu.js ***!
  \*************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @babel/runtime/helpers/createClass */ "./node_modules/@babel/runtime/helpers/createClass.js");
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _FooterMenu_less__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./FooterMenu.less */ "./src/transform/FooterMenu.less");
/* harmony import */ var _FooterMenu_less__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_FooterMenu_less__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _CollectionUtilities__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./CollectionUtilities */ "./src/transform/CollectionUtilities.js");
/* harmony import */ var _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../transform/HTMLUtilities */ "./src/transform/HTMLUtilities.ts");





/**
 * @typedef {function} FooterMenuItemClickCallback
 * @param {!Array.<string>} payload Important - should return empty array if no payload strings.
 * @return {void}
 */

/**
 * @typedef {string} MenuItemType
 */

/**
 * Type representing kinds of menu items.
 * IMPORTANT: Don't change these values without considering it a breaking change.
 * Existing clients rely on using the string values so that they are serializable.
 * @enum {MenuItemType}
 */

var MenuItemType = {
  lastEdited: 'lastEdited',
  pageIssues: 'pageIssues',
  disambiguation: 'disambiguation',
  coordinate: 'coordinate',
  talkPage: 'talkPage'
};
/**
 * Menu item model.
 */

var MenuItem = /*#__PURE__*/function () {
  /**
   * MenuItem constructor.
   * @param {!string} title
   * @param {?string} subtitle
   * @param {!MenuItemType} itemType
   * @param {FooterMenuItemClickCallback} clickHandler
   */
  function MenuItem(title, subtitle, itemType, clickHandler) {
    _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, MenuItem);

    this.title = title;
    this.subtitle = subtitle;
    this.itemType = itemType;
    this.clickHandler = clickHandler;
    this.payload = [];
  }
  /**
   * Returns icon CSS class for this menu item based on its type.
   * @return {!string}
   */


  _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default()(MenuItem, [{
    key: "iconClass",
    value: function iconClass() {
      switch (this.itemType) {
        case MenuItemType.lastEdited:
          return 'pcs-footer-menu-icon-last-edited';

        case MenuItemType.talkPage:
          return 'pcs-footer-menu-icon-talk-page';

        case MenuItemType.pageIssues:
          return 'pcs-footer-menu-icon-page-issues';

        case MenuItemType.disambiguation:
          return 'pcs-footer-menu-icon-disambiguation';

        case MenuItemType.coordinate:
          return 'pcs-footer-menu-icon-coordinate';

        default:
          return '';
      }
    }
    /**
     * Extracts array of page issues, disambiguation titles, etc from element.
     * @typedef {function} PayloadExtractor
     * @param {!Document} document
     * @param {?Element} element
     * @return {!Array.<string>} Return empty array if nothing is extracted
     */

    /**
     * Returns reference to function for extracting payload when this menu item is tapped.
     * @return {?PayloadExtractor}
     */

  }, {
    key: "payloadExtractor",
    value: function payloadExtractor() {
      switch (this.itemType) {
        case MenuItemType.pageIssues:
          return _CollectionUtilities__WEBPACK_IMPORTED_MODULE_3__["default"].collectPageIssues;

        case MenuItemType.disambiguation:
          return _CollectionUtilities__WEBPACK_IMPORTED_MODULE_3__["default"].collectHatnotes;

        default:
          return undefined;
      }
    }
  }]);

  return MenuItem;
}();
/**
 * Makes document fragment for a menu item.
 * @param {!MenuItem} menuItem
 * @param {!Document} document
 * @return {!DocumentFragment}
 */


var documentFragmentForMenuItem = function documentFragmentForMenuItem(menuItem, document) {
  var item = document.createElement('div');
  item.className = 'pcs-footer-menu-item';
  item.role = 'menuitem';
  var containerAnchor = document.createElement('a');
  containerAnchor.addEventListener('click', function () {
    menuItem.clickHandler(menuItem.payload);
  });
  item.appendChild(containerAnchor);

  if (menuItem.title) {
    var title = document.createElement('div');
    title.className = 'pcs-footer-menu-item-title';
    /* DOM sink status: sanitized - headingString can be changed by clients */

    title.textContent = menuItem.title;
    containerAnchor.title = menuItem.title;
    containerAnchor.appendChild(title);
  }

  if (menuItem.subtitle) {
    var subtitle = document.createElement('div');
    subtitle.className = 'pcs-footer-menu-item-subtitle';
    /* DOM sink status: sanitized - headingString can be changed by clients */

    subtitle.textContent = menuItem.subtitle;
    containerAnchor.appendChild(subtitle);
  }

  var iconClass = menuItem.iconClass();

  if (iconClass) {
    item.classList.add(iconClass);
  }

  return document.createDocumentFragment().appendChild(item);
};
/**
 * Adds a MenuItem to a container.
 * @param {!MenuItem} menuItem
 * @param {!string} containerID
 * @param {!Document} document
 * @return {void}
 */


var addItem = function addItem(menuItem, containerID, document) {
  document.getElementById(containerID).appendChild(documentFragmentForMenuItem(menuItem, document));
};
/**
 * Conditionally adds a MenuItem to a container.
 * @param {!string} title
 * @param {!string} subtitle
 * @param {!MenuItemType} itemType
 * @param {!string} containerID
 * @param {FooterMenuItemClickCallback} clickHandler
 * @param {!Document} document
 * @return {void}
 */


var maybeAddItem = function maybeAddItem(title, subtitle, itemType, containerID, clickHandler, document) {
  if (title === '') {
    return;
  }

  var item = new MenuItem(title, subtitle, itemType, clickHandler); // Items are not added if they have a payload extractor which fails to extract anything.

  var extractor = item.payloadExtractor();

  if (extractor) {
    item.payload = extractor(document);

    if (item.payload.length === 0) {
      return;
    }
  }

  addItem(item, containerID, document);
};
/**
 * Sets heading element string.
 * @param {!string} headingString
 * @param {!string} headingID
 * @param {!Document} document
 * @return {void}
 */


var setHeading = function setHeading(headingString, headingID, document) {
  var headingElement = document.getElementById(headingID);
  /* DOM sink status: sanitized - headingString can be changed by clients */

  headingElement.textContent = headingString;
  headingElement.title = _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_4__["default"].escape(headingString);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  MenuItemType: MenuItemType,
  // todo: rename to just ItemType?
  setHeading: setHeading,
  maybeAddItem: maybeAddItem
});

/***/ }),

/***/ "./src/transform/FooterMenu.less":
/*!***************************************!*\
  !*** ./src/transform/FooterMenu.less ***!
  \***************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/FooterReadMore.js":
/*!*****************************************!*\
  !*** ./src/transform/FooterReadMore.js ***!
  \*****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _FooterReadMore_less__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./FooterReadMore.less */ "./src/transform/FooterReadMore.less");
/* harmony import */ var _FooterReadMore_less__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_FooterReadMore_less__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../transform/HTMLUtilities */ "./src/transform/HTMLUtilities.ts");



/**
 * Display fetched read more pages.
 * @typedef {function} ShowReadMorePagesHandler
 * @param {!Array.<object>} pages
 * @param {!string} heading
 * @param {!string} sectionContainerId
 * @param {!string} pageContainerId
 * @param {!Document} document
 * @return {void}
 */

/**
 * Removes parenthetical enclosures from string.
 * @param {!string} string
 * @param {!string} opener
 * @param {!string} closer
 * @return {!string}
 */

var safelyRemoveEnclosures = function safelyRemoveEnclosures(string, opener, closer) {
  var enclosureRegex = new RegExp("\\s?[".concat(opener, "][^").concat(opener).concat(closer, "]+[").concat(closer, "]"), 'g');
  var counter = 0;
  var safeMaxTries = 30;
  var stringToClean = string;
  var previousString = '';

  do {
    previousString = stringToClean;
    stringToClean = stringToClean.replace(enclosureRegex, '');
    counter++;
  } while (previousString !== stringToClean && counter < safeMaxTries);

  return stringToClean;
};
/**
 * Removes '(...)' and '/.../' parenthetical enclosures from string.
 * @param {!string} string
 * @return {!string}
 */


var cleanExtract = function cleanExtract(string) {
  var stringToClean = string;
  stringToClean = safelyRemoveEnclosures(stringToClean, '(', ')');
  stringToClean = safelyRemoveEnclosures(stringToClean, '/', '/');
  return stringToClean;
};
/**
 * Read more page model.
 */


var ReadMorePage =
/**
 * ReadMorePage constructor.
 * @param {!string} title
 * @param {!string} displayTitle
 * @param {?string} thumbnail
 * @param {?string} description
 * @param {?string} extract
 */
function ReadMorePage(title, displayTitle, thumbnail, description, extract) {
  _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, ReadMorePage);

  this.title = title;
  this.displayTitle = displayTitle;
  this.thumbnail = thumbnail;
  this.description = description;
  this.extract = extract;
};

var schemeRegex = /^[a-z]+:/;
/**
 * Makes document fragment for a read more page.
 * @param {!ReadMorePage} readMorePage
 * @param {!number} index
 * @param {!Document} document
 * @return {!DocumentFragment}
 */

var documentFragmentForReadMorePage = function documentFragmentForReadMorePage(readMorePage, index, document) {
  var outerAnchorContainer = document.createElement('a');
  outerAnchorContainer.id = index;
  outerAnchorContainer.className = 'pcs-footer-readmore-page';
  var globalLoadImages = document.pcsSetupSettings ? document.pcsSetupSettings.loadImages : true;
  var hasImage = readMorePage.thumbnail && readMorePage.thumbnail.source;

  if (hasImage && globalLoadImages) {
    var image = document.createElement('div');
    image.style.backgroundImage = "url(".concat(readMorePage.thumbnail.source.replace(schemeRegex, ''), ")");
    image.classList.add('pcs-footer-readmore-page-image');
    outerAnchorContainer.appendChild(image);
  }

  var innerDivContainer = document.createElement('div');
  innerDivContainer.classList.add('pcs-footer-readmore-page-container');
  outerAnchorContainer.appendChild(innerDivContainer);
  outerAnchorContainer.setAttribute('title', readMorePage.title);
  outerAnchorContainer.setAttribute('data-pcs-source', 'read-more');
  outerAnchorContainer.href = "./".concat(encodeURI(readMorePage.title));
  var titleToShow;

  if (readMorePage.displayTitle) {
    titleToShow = readMorePage.displayTitle;
  } else if (readMorePage.title) {
    titleToShow = readMorePage.title;
  }

  if (titleToShow) {
    var title = document.createElement('div');
    title.id = index;
    title.className = 'pcs-footer-readmore-page-title';
    /* DOM sink status: safe - content transform with no user interference */

    title.innerHTML = titleToShow.replace(/_/g, ' ');
    outerAnchorContainer.title = readMorePage.title.replace(/_/g, ' ');
    innerDivContainer.appendChild(title);
  }

  var description;

  if (readMorePage.description) {
    description = readMorePage.description;
  }

  if ((!description || description.length < 10) && readMorePage.extract) {
    description = cleanExtract(readMorePage.extract);
  }

  if (description) {
    var descriptionEl = document.createElement('div');
    descriptionEl.id = index;
    descriptionEl.className = 'pcs-footer-readmore-page-description';
    /* DOM sink status: safe - content from read more query endpoint */

    descriptionEl.innerHTML = description;
    innerDivContainer.appendChild(descriptionEl);
  }

  return document.createDocumentFragment().appendChild(outerAnchorContainer);
}; // eslint-disable-next-line valid-jsdoc

/**
 * @type {ShowReadMorePagesHandler}
 */


var showReadMorePages = function showReadMorePages(pages, heading, sectionContainerId, pageContainerId, document) {
  var sectionContainer = document.getElementById(sectionContainerId);
  var pageContainer = document.getElementById(pageContainerId);
  setHeading(heading, 'pcs-footer-container-readmore-heading', document);
  pages.forEach(function (page, index) {
    var title = page.titles.normalized;
    var pageModel = new ReadMorePage(title, page.titles.display, page.thumbnail, page.description, page.extract);
    var pageFragment = documentFragmentForReadMorePage(pageModel, index, document);
    pageContainer.appendChild(pageFragment);
  });
  sectionContainer.style.display = 'block';
};
/**
 * URL for retrieving 'Read more' pages for a given title.
 * Leave 'baseURL' null if you don't need to deal with proxying.
 * @param {!string} title
 * @param {!number} count Number of `Read more` items to fetch for this title
 * @param {?string} baseURL
 * @return {!string}
 */


var readMoreQueryURL = function readMoreQueryURL(title, count, baseURL) {
  return "".concat(baseURL || '', "/page/related/").concat(title);
};
/**
 * Fetches 'Read more' pages and adds them if found.
 * @param {!string} title
 * @param {!string} heading
 * @param {!number} count
 * @param {!string} sectionContainerId
 * @param {!string} pageContainerId
 * @param {?string} baseURL
 * @param {!Document} document
 * @return {void}
 */


var fetchAndAdd = function fetchAndAdd(title, heading, count, sectionContainerId, pageContainerId, baseURL, document) {
  var xhr = new XMLHttpRequest(); // eslint-disable-line no-undef

  xhr.open('GET', readMoreQueryURL(title, count, baseURL), true);

  xhr.onload = function () {
    var pages;

    try {
      pages = JSON.parse(xhr.responseText).pages;
    } catch (e) {}

    if (!(pages && pages.length)) {
      return;
    }

    var results;

    if (pages.length > count) {
      var rand = Math.floor(Math.random() * Math.floor(pages.length - count));
      results = pages.slice(rand, rand + count);
    } else {
      results = pages;
    }

    showReadMorePages(results, heading, sectionContainerId, pageContainerId, document);
  };

  xhr.send();
};
/**
 * Sets heading element string.
 * @param {!string} headingString
 * @param {!string} headingID
 * @param {!Document} document
 * @return {void}
 */


var setHeading = function setHeading(headingString, headingID, document) {
  var headingElement = document.getElementById(headingID);
  /* DOM sink status: sanitized - headingString can be changed by clients */

  headingElement.textContent = headingString;
  headingElement.title = _transform_HTMLUtilities__WEBPACK_IMPORTED_MODULE_2__["default"].escape(headingString);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  fetchAndAdd: fetchAndAdd,
  setHeading: setHeading,
  test: {
    cleanExtract: cleanExtract,
    safelyRemoveEnclosures: safelyRemoveEnclosures
  }
});

/***/ }),

/***/ "./src/transform/FooterReadMore.less":
/*!*******************************************!*\
  !*** ./src/transform/FooterReadMore.less ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/HTMLUtilities.ts":
/*!****************************************!*\
  !*** ./src/transform/HTMLUtilities.ts ***!
  \****************************************/
/*! exports provided: ARIA, default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ARIA", function() { return ARIA; });
var ARIA = {
  LABEL: 'aria-label',
  LABELED_BY: 'aria-labelledby'
};

var escapeCallback = function escapeCallback(s) {
  switch (s) {
    case '\'':
      return '&#039;';

    case '"':
      return '&quot;';

    case '<':
      return '&lt;';

    case '>':
      return '&gt;';

    case '&':
      return '&amp;';

    default:
      return '';
  }
};

var escape = function escape(input) {
  return input && input.replace(/['"<>&]/g, escapeCallback);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  escape: escape
});

/***/ }),

/***/ "./src/transform/LazyLoadTransform.less":
/*!**********************************************!*\
  !*** ./src/transform/LazyLoadTransform.less ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/LazyLoadTransform.ts":
/*!********************************************!*\
  !*** ./src/transform/LazyLoadTransform.ts ***!
  \********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _LazyLoadTransform_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./LazyLoadTransform.less */ "./src/transform/LazyLoadTransform.less");
/* harmony import */ var _LazyLoadTransform_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_LazyLoadTransform_less__WEBPACK_IMPORTED_MODULE_0__);
 // todo: use imports when other modules are TypeScript.

var ElementGeometry = __webpack_require__(/*! ./ElementGeometry */ "./src/transform/ElementGeometry.js").default;

var ElementUtilities = __webpack_require__(/*! ./ElementUtilities */ "./src/transform/ElementUtilities.js").default;

var Polyfill = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js").default; // CSS classes used to identify and present lazily loaded images. Placeholders are members of
// PLACEHOLDER_CLASS and one state class: pending, loading, or error. Images are members of either
// loading or loaded state classes. Class names should match those in LazyLoadTransform.css.


var PLACEHOLDER_CLASS = 'pcs-lazy-load-placeholder';
var PLACEHOLDER_PENDING_CLASS = 'pcs-lazy-load-placeholder-pending'; // Download pending.

var PLACEHOLDER_LOADING_CLASS = 'pcs-lazy-load-placeholder-loading'; // Download started.

var PLACEHOLDER_ERROR_CLASS = 'pcs-lazy-load-placeholder-error'; // Download failure.

var IMAGE_LOADING_CLASS = 'pcs-lazy-load-image-loading'; // Download started.

var IMAGE_LOADED_CLASS = 'pcs-lazy-load-image-loaded'; // Download completed.

var NO_LAZY_LOAD = 'pcs-no-lazy-load'; // Helper class to prevent lazy loading.

var CLASSES = {
  PLACEHOLDER_CLASS: PLACEHOLDER_CLASS,
  PLACEHOLDER_PENDING_CLASS: PLACEHOLDER_PENDING_CLASS,
  PLACEHOLDER_LOADING_CLASS: PLACEHOLDER_LOADING_CLASS,
  PLACEHOLDER_ERROR_CLASS: PLACEHOLDER_ERROR_CLASS,
  IMAGE_LOADING_CLASS: IMAGE_LOADING_CLASS,
  IMAGE_LOADED_CLASS: IMAGE_LOADED_CLASS,
  NO_LAZY_LOAD: NO_LAZY_LOAD
}; // Attributes copied from images to placeholders via data-* attributes for later restoration. The
// image's classes and dimensions are also set on the placeholder.
// The 3 data-* items are used by iOS.

var COPY_ATTRIBUTES = ['class', 'style', 'src', 'srcset', 'width', 'height', 'alt', 'usemap', 'data-file-width', 'data-file-height', 'data-image-gallery']; // Small images, especially icons, are quickly downloaded and may appear in many places. Lazily
// loading these images degrades the experience with little gain. Always eagerly load these images.
// Example: flags in the medal count for the "1896 Summer Olympics medal table."
// https://en.m.wikipedia.org/wiki/1896_Summer_Olympics_medal_table?oldid=773498394#Medal_count

var UNIT_TO_MINIMUM_LAZY_LOAD_SIZE = {
  px: 50,
  // https://phabricator.wikimedia.org/diffusion/EMFR/browse/master/includes/MobileFormatter.php;c89f371ea9e789d7e1a827ddfec7c8028a549c12$22
  ex: 10,
  // ''
  em: 5 // 1ex ≈ .5em; https://developer.mozilla.org/en-US/docs/Web/CSS/length#Units

};
/**
 * Replace an image with a placeholder.
 * @param {!Document} document
 * @param {!HTMLImageElement} image The image to be replaced.
 * @return {!HTMLSpanElement} The placeholder replacing image.
 */

var convertImageToPlaceholder = function convertImageToPlaceholder(document, image) {
  // There are a number of possible implementations for placeholders including:
  //
  // - [MobileFrontend] Replace the original image with a span and replace the span with a new
  //   downloaded image.
  //   This option has a good fade-in but has some CSS concerns for the placeholder, particularly
  //   `max-width`, and causes significant reflows when used with image widening.
  //
  // - [Previous] Replace the original image with a span and append a new downloaded image to the
  //   span.
  //   This option has the best cross-fading and extensibility but makes duplicating all the CSS
  //   rules for the appended image impractical.
  //
  // - [Previous] Replace the original image's source with a transparent image and update the source
  //   from a new downloaded image.
  //   This option has a good fade-in and minimal CSS concerns for the placeholder and image but
  //   causes significant reflows when used with image widening.
  //
  // - [Current] Replace the original image with a couple spans and replace the spans with a new
  //   downloaded image.
  //   This option is about the same as MobileFrontend but supports image widening without reflows.
  // Create the root placeholder.
  var placeholder = document.createElement('span'); // Copy the image's classes and append the placeholder and current state (pending) classes.

  if (image.hasAttribute('class')) {
    placeholder.setAttribute('class', image.getAttribute('class') || '');
  }

  placeholder.classList.add(PLACEHOLDER_CLASS);
  placeholder.classList.add(PLACEHOLDER_PENDING_CLASS); // Match the image's width, if specified. If image widening is used, this width will be overridden
  // by !important priority.

  var geometry = ElementGeometry.from(image);

  if (geometry.width) {
    placeholder.style.setProperty('width', "".concat(geometry.width));
  } // Save the image's attributes to data-* attributes for later restoration.


  ElementUtilities.copyAttributesToDataAttributes(image, placeholder, COPY_ATTRIBUTES); // Create a spacer and match the aspect ratio of the original image, if determinable. If image
  // widening is used, this spacer will scale with the width proportionally.

  var spacing = document.createElement('span');

  if (geometry.width && geometry.height) {
    // Assume units are identical.
    var ratio = geometry.heightValue / geometry.widthValue;
    spacing.style.setProperty('padding-top', "".concat(ratio * 100, "%"));
  } // Append the spacer to the placeholder and replace the image with the placeholder.

  /* DOM sink status: safe - content transform with no user interference */


  placeholder.appendChild(spacing);
  /* DOM sink status: safe - content from parsoid output  */

  if (image.parentNode) image.parentNode.replaceChild(placeholder, image);
  return placeholder;
};
/**
 * @param {!HTMLImageElement} image The image to be considered.
 * @return {!boolean} true if image download can be deferred, false if image should be eagerly
 *                    loaded.
 */


var isLazyLoadable = function isLazyLoadable(image) {
  var geometry = ElementGeometry.from(image);

  if (!geometry.width || !geometry.height) {
    return true;
  }

  var minWidth = UNIT_TO_MINIMUM_LAZY_LOAD_SIZE[geometry.widthUnit] || Infinity;
  var minHeight = UNIT_TO_MINIMUM_LAZY_LOAD_SIZE[geometry.heightUnit] || Infinity;
  return geometry.widthValue >= minWidth && geometry.heightValue >= minHeight;
};
/**
 * @param {!Element} element
 * @return {!Array.<HTMLImageElement>} Convertible images descendent from but not including element.
 */


var queryLazyLoadableImages = function queryLazyLoadableImages(element) {
  return Polyfill.querySelectorAll(element, 'img').filter(function (image) {
    return isLazyLoadable(image);
  });
};
/**
 * Convert images with placeholders. The transformation is inverted by calling loadImage().
 * @param {!Document} document
 * @param {!Array.<HTMLImageElement>} images The images to lazily load.
 * @return {!Array.<HTMLSpanElement>} The placeholders replacing images.
 */


var convertImagesToPlaceholders = function convertImagesToPlaceholders(document, images) {
  return images.map(function (image) {
    return convertImageToPlaceholder(document, image);
  });
};
/**
 * Start downloading image resources associated with a given placeholder and replace the placeholder
 * with a new image element when the download is complete.
 * @param {!Document} document
 * @param {!HTMLSpanElement} placeholder
 * @return {!HTMLImageElement} A new image element.
 */


var loadPlaceholder = function loadPlaceholder(document, placeholder) {
  placeholder.classList.add(PLACEHOLDER_LOADING_CLASS);
  placeholder.classList.remove(PLACEHOLDER_PENDING_CLASS);
  var image = document.createElement('img');

  var retryListener = function retryListener(event) {
    // eslint-disable-line require-jsdoc
    image.setAttribute('src', image.getAttribute('src') || '');
    event.stopPropagation();
    event.preventDefault();
  };
  /**
   * T271566 - Check if image has usemap attribute to prevent adding lazy load classes
   * @param {HTMLImageElement} image
   * @return {boolean}
   */


  var isUsemapImage = function isUsemapImage(image) {
    return image.hasAttribute('usemap');
  }; // Add the download listener prior to setting the src attribute to avoid missing the load event.


  image.addEventListener('load', function () {
    placeholder.removeEventListener('click', retryListener);
    /* DOM sink status: safe - content from parsoid output */

    if (placeholder.parentNode) placeholder.parentNode.replaceChild(image, placeholder);
    var imageWidth = image.getAttribute('width');
    var divWrapper = document.createElement('div');

    if (image.className && image.className.includes('pcs-widen-image-override')) {
      divWrapper.classList.add('pcs-widen-image-wrapper');
    } else if (isUsemapImage(image)) {
      return;
    } else {
      divWrapper.classList.add('pcs-image-wrapper');
    }

    var imageParent = image.parentNode;
    divWrapper.appendChild(image);
    divWrapper.setAttribute('style', "width: ".concat(imageWidth, "px;"));
    imageParent ? imageParent.appendChild(divWrapper) : null;
    var nestedImage = divWrapper.querySelector('img');

    if (nestedImage) {
      nestedImage.classList.add(IMAGE_LOADED_CLASS);
      nestedImage.classList.remove(IMAGE_LOADING_CLASS);
    }
  }, {
    once: true
  });
  image.addEventListener('error', function () {
    placeholder.classList.add(PLACEHOLDER_ERROR_CLASS);
    placeholder.classList.remove(PLACEHOLDER_LOADING_CLASS);
    placeholder.addEventListener('click', retryListener);
  }, {
    once: true
  }); // Set src and other attributes, triggering a download.

  ElementUtilities.copyDataAttributesToAttributes(placeholder, image, COPY_ATTRIBUTES); // Append to the class list after copying over any preexisting classes.

  if (!isUsemapImage(image)) {
    image.classList.add(IMAGE_LOADING_CLASS);
  }

  return image;
};
/**
 * Set 'pcs-no-lazy-load' class for given images inside element
 * @param {!Element} element
 * @return {void}
 */


var addImageNoLazyLoadClass = function addImageNoLazyLoadClass(element) {
  var images = Polyfill.querySelectorAll(element, 'img');
  images.forEach(function (image) {
    image.classList.add(NO_LAZY_LOAD);
  });
};

/* harmony default export */ __webpack_exports__["default"] = ({
  CLASSES: CLASSES,
  PLACEHOLDER_CLASS: PLACEHOLDER_CLASS,
  isLazyLoadable: isLazyLoadable,
  queryLazyLoadableImages: queryLazyLoadableImages,
  convertImagesToPlaceholders: convertImagesToPlaceholders,
  convertImageToPlaceholder: convertImageToPlaceholder,
  loadPlaceholder: loadPlaceholder,
  addImageNoLazyLoadClass: addImageNoLazyLoadClass
});

/***/ }),

/***/ "./src/transform/LazyLoadTransformer.js":
/*!**********************************************!*\
  !*** ./src/transform/LazyLoadTransformer.js ***!
  \**********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "default", function() { return _default; });
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @babel/runtime/helpers/createClass */ "./node_modules/@babel/runtime/helpers/createClass.js");
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _CollapseTable__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./CollapseTable */ "./src/transform/CollapseTable.js");
/* harmony import */ var _ElementUtilities__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./ElementUtilities */ "./src/transform/ElementUtilities.js");
/* harmony import */ var _LazyLoadTransform__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./LazyLoadTransform */ "./src/transform/LazyLoadTransform.ts");
/* harmony import */ var _Polyfill__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js");
/* harmony import */ var _Throttle__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./Throttle */ "./src/transform/Throttle.js");







var EVENT_TYPES = ['scroll', 'resize', _CollapseTable__WEBPACK_IMPORTED_MODULE_2__["default"].SECTION_TOGGLED_EVENT_TYPE];
var THROTTLE_PERIOD_MILLISECONDS = 100;
/**
 * This class subscribes to key page events, applying lazy load transforms or inversions as
 * applicable. It has external dependencies on the section-toggled custom event and the following
 * standard browser events: resize, scroll.
 */

var _default = /*#__PURE__*/function () {
  /**
   * @param {!Window} window
   * @param {!number} loadDistanceMultiplier Images within this multiple of the screen height are
   *                                         loaded in either direction.
   */
  function _default(window, loadDistanceMultiplier) {
    var _this = this;

    _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, _default);

    this._window = window;
    this._loadDistanceMultiplier = loadDistanceMultiplier;
    this._placeholders = [];
    this._registered = false;
    this._throttledLoadPlaceholders = _Throttle__WEBPACK_IMPORTED_MODULE_6__["default"].wrap(window, THROTTLE_PERIOD_MILLISECONDS, function () {
      return _this._loadPlaceholders();
    });
  }
  /**
   * Convert images with placeholders. Calling this function may register this instance to listen to
   * page events.
   * @param {!Element} element
   * @return {void}
   */


  _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default()(_default, [{
    key: "convertImagesToPlaceholders",
    value: function convertImagesToPlaceholders(element) {
      var images = _LazyLoadTransform__WEBPACK_IMPORTED_MODULE_4__["default"].queryLazyLoadableImages(element);
      var placeholders = _LazyLoadTransform__WEBPACK_IMPORTED_MODULE_4__["default"].convertImagesToPlaceholders(this._window.document, images);
      this._placeholders = this._placeholders.concat(placeholders);

      this._register();
    }
    /**
     * Searches for existing placeholders in the DOM Document.
     * This is an alternative to #convertImagesToPlaceholders if that was already done server-side.
     * @param {!Element} element root element to start searching for placeholders
     * @return {void}
     */

  }, {
    key: "collectExistingPlaceholders",
    value: function collectExistingPlaceholders(element) {
      var placeholders = _Polyfill__WEBPACK_IMPORTED_MODULE_5__["default"].querySelectorAll(element, ".".concat(_LazyLoadTransform__WEBPACK_IMPORTED_MODULE_4__["default"].PLACEHOLDER_CLASS));
      this._placeholders = this._placeholders.concat(placeholders);

      this._register();
    }
    /**
     * Manually trigger a load images check. Calling this function may deregister this instance from
     * listening to page events.
     * @return {void}
     */

  }, {
    key: "loadPlaceholders",
    value: function loadPlaceholders() {
      this._throttledLoadPlaceholders();
    }
    /**
     * This method may be safely called even when already unregistered. This function clears the
     * record of placeholders.
     * @return {void}
     */

  }, {
    key: "deregister",
    value: function deregister() {
      var _this2 = this;

      if (!this._registered) {
        return;
      }

      EVENT_TYPES.forEach(function (eventType) {
        return _this2._window.removeEventListener(eventType, _this2._throttledLoadPlaceholders);
      });

      this._throttledLoadPlaceholders.reset();

      this._placeholders = [];
      this._registered = false;
    }
    /**
     * This method may be safely called even when already registered.
     * @return {void}
     */

  }, {
    key: "_register",
    value: function _register() {
      var _this3 = this;

      if (this._registered || !this._placeholders.length) {
        return;
      }

      this._registered = true;
      EVENT_TYPES.forEach(function (eventType) {
        return _this3._window.addEventListener(eventType, _this3._throttledLoadPlaceholders);
      });
    }
    /** @return {void} */

  }, {
    key: "_loadPlaceholders",
    value: function _loadPlaceholders() {
      var _this4 = this;

      this._placeholders = this._placeholders.filter(function (placeholder) {
        var pending = true;

        if (_this4._isPlaceholderEligibleToLoad(placeholder)) {
          _LazyLoadTransform__WEBPACK_IMPORTED_MODULE_4__["default"].loadPlaceholder(_this4._window.document, placeholder);
          pending = false;
        }

        return pending;
      });

      if (this._placeholders.length === 0) {
        this.deregister();
      }
    }
    /**
     * @param {!HTMLSpanElement} placeholder
     * @return {!boolean}
     */

  }, {
    key: "_isPlaceholderEligibleToLoad",
    value: function _isPlaceholderEligibleToLoad(placeholder) {
      return _ElementUtilities__WEBPACK_IMPORTED_MODULE_3__["default"].isVisible(placeholder) && this._isPlaceholderWithinLoadDistance(placeholder);
    }
    /**
     * @param {!HTMLSpanElement} placeholder
     * @return {!boolean}
     */

  }, {
    key: "_isPlaceholderWithinLoadDistance",
    value: function _isPlaceholderWithinLoadDistance(placeholder) {
      var bounds = placeholder.getBoundingClientRect();
      var range = this._window.innerHeight * this._loadDistanceMultiplier;
      return !(bounds.top > range || bounds.bottom < -range);
    }
  }]);

  return _default;
}();



/***/ }),

/***/ "./src/transform/NodeUtilities.js":
/*!****************************************!*\
  !*** ./src/transform/NodeUtilities.js ***!
  \****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
// Node is undefined in Node.js
var NODE_TYPE = {
  ELEMENT_NODE: 1,
  TEXT_NODE: 3
};
/**
 * Determines if node is either an element or text node.
 * @param  {!Node} node
 * @return {!boolean}
 */

var isNodeTypeElementOrText = function isNodeTypeElementOrText(node) {
  return node.nodeType === NODE_TYPE.ELEMENT_NODE || node.nodeType === NODE_TYPE.TEXT_NODE;
};
/**
 * Get node's bounding rect as a plain object.
 * @param {!Node} node
 * @return {!Object<string, number>}
 */


var getBoundingClientRectAsPlainObject = function getBoundingClientRectAsPlainObject(node) {
  var rect = node.getBoundingClientRect();
  return {
    top: rect.top,
    right: rect.right,
    bottom: rect.bottom,
    left: rect.left,
    width: rect.width,
    height: rect.height,
    x: rect.x,
    y: rect.y
  };
};

/* harmony default export */ __webpack_exports__["default"] = ({
  isNodeTypeElementOrText: isNodeTypeElementOrText,
  getBoundingClientRectAsPlainObject: getBoundingClientRectAsPlainObject,
  NODE_TYPE: NODE_TYPE
});

/***/ }),

/***/ "./src/transform/PlatformTransform.js":
/*!********************************************!*\
  !*** ./src/transform/PlatformTransform.js ***!
  \********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
var CLASS_PREFIX = 'pcs-platform-';
var VERSION_PREFIX = 'pcs-v';
var CLASS = {
  ANDROID: "".concat(CLASS_PREFIX, "android"),
  IOS: "".concat(CLASS_PREFIX, "ios")
}; // Regular expressions from https://phabricator.wikimedia.org/diffusion/EMFR/browse/master/resources/mobile.startup/browser.js;c89f371ea9e789d7e1a827ddfec7c8028a549c12.

/**
 * @param {!Window} window
 * @return {!boolean} true if the user agent is Android, false otherwise.
 */

var isAndroid = function isAndroid(window) {
  return /android/i.test(window.navigator.userAgent);
};
/**
 * @param {!Window} window
 * @return {!boolean} true if the user agent is iOS, false otherwise.
 */


var isIOs = function isIOs(window) {
  return /ipad|iphone|ipod/i.test(window.navigator.userAgent);
};
/**
 * @param {!HTMLDocument} document
 * @param {!string} platform one of the values in CLASS
 * @return {void}
 */


var setPlatform = function setPlatform(document, platform) {
  if (!document || !document.documentElement) {
    return;
  }

  document.documentElement.classList.add(platform);
};
/**
 * Configures the page for a given version. If the client passes in an old version, ensure
 * features that would not work for that client are hidden.
 * @param {!HTMLDocument} document
 * @param {?string} version callback
 * @return {void}
 */


var setVersion = function setVersion(document, version) {
  if (!document || !document.documentElement) {
    return;
  } // <IMPORTANT>
  // When new versions are added here, update the profile version for mobile-html in
  // lib/mobileutil in the mobileapps repo to match the latest version and add
  // information about new features in docs/pcs.md. Only major and minor versions are
  // supported.
  // </IMPORTANT>


  var currentVersion = 2;
  var supportedVersion = version || 1; // Add every supported version class to the documentElement

  for (var _version = 1; _version <= currentVersion; _version++) {
    document.documentElement.classList.add(VERSION_PREFIX + _version);

    if (_version === supportedVersion) {
      break;
    }
  }
};
/**
 * @param {!Window} window
 * @return {void}
 */


var classify = function classify(window) {
  var html = window.document.documentElement;

  if (isAndroid(window)) {
    html.classList.add(CLASS.ANDROID);
  }

  if (isIOs(window)) {
    html.classList.add(CLASS.IOS);
  }
};

/* harmony default export */ __webpack_exports__["default"] = ({
  CLASS: CLASS,
  CLASS_PREFIX: CLASS_PREFIX,
  classify: classify,
  setPlatform: setPlatform,
  setVersion: setVersion
});

/***/ }),

/***/ "./src/transform/Polyfill.js":
/*!***********************************!*\
  !*** ./src/transform/Polyfill.js ***!
  \***********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/**
 * Polyfill function that tells whether a given element matches a selector.
 * @param {!Element} el Element
 * @param {!string} selector Selector to look for
 * @return {!boolean} Whether the element matches the selector
 */
var matchesSelector = function matchesSelector(el, selector) {
  if (el.matches) {
    return el.matches(selector);
  }

  if (el.matchesSelector) {
    return el.matchesSelector(selector);
  }

  if (el.webkitMatchesSelector) {
    return el.webkitMatchesSelector(selector);
  }

  return false;
};
/**
 * @param {!Element} element
 * @param {!string} selector
 * @return {!Array.<Element>}
 */


var querySelectorAll = function querySelectorAll(element, selector) {
  return Array.prototype.slice.call(element.querySelectorAll(selector));
}; // https://developer.mozilla.org/en-US/docs/Web/API/CustomEvent/CustomEvent#Polyfill
// Required by Android API 16 AOSP Nexus S emulator.
// eslint-disable-next-line no-undef


var CustomEvent = typeof window !== 'undefined' && window.CustomEvent || function (type) {
  var parameters = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {
    bubbles: false,
    cancelable: false,
    detail: undefined
  };
  // eslint-disable-next-line no-undef
  var event = document.createEvent('CustomEvent');
  event.initCustomEvent(type, parameters.bubbles, parameters.cancelable, parameters.detail);
  return event;
};

/* harmony default export */ __webpack_exports__["default"] = ({
  matchesSelector: matchesSelector,
  querySelectorAll: querySelectorAll,
  CustomEvent: CustomEvent
});

/***/ }),

/***/ "./src/transform/ReferenceCollection.js":
/*!**********************************************!*\
  !*** ./src/transform/ReferenceCollection.js ***!
  \**********************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _ElementUtilities__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./ElementUtilities */ "./src/transform/ElementUtilities.js");
/* harmony import */ var _NodeUtilities__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./NodeUtilities */ "./src/transform/NodeUtilities.js");
/* harmony import */ var _Polyfill__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js");




var REFERENCE_SELECTOR = '.reference, .mw-ref';
var CITE_FRAGMENT_PREFIX = '#cite_note-';
var BACK_LINK_FRAGMENT_PREFIX = '#pcs-ref-back-link-';
var BACK_LINK_ATTRIBUTE = 'pcs-back-links';
var CLASS = {
  BACK_LINK_ANCHOR: 'pcs-ref-back-link',
  BACK_LINK_CONTAINER: 'pcs-ref-backlink-container',
  BODY: 'pcs-ref-body',
  BODY_HEADER: 'pcs-ref-body-header',
  BODY_CONTENT: 'pcs-ref-body-content',
  REF: 'pcs-ref'
};
/**
 * Does this have the proper fragment prefix?
 * @param {!string} href of the anchor
 * @param {!string} fragmentPrefix to look for. For example in './Dog#Cite-test',
 * 'Cite-' is the prefix.
 * @param {?string} pageTitle to check for before the fragment if it's not a relative fragment.
 * It should be encoded for links. A relative fragment is a href without a path prefix. For example
 * '#cite-test' will match no matter the title, but for './Dog/#cite-test' the title must be 'Dog'.
 * @return {!boolean}
 */

var isForSamePageTitleAndHasFragmentPrefix = function isForSamePageTitleAndHasFragmentPrefix(href, fragmentPrefix, pageTitle) {
  var decodedHref = decodeURIComponent(href);
  var decodedFragment = decodeURIComponent(fragmentPrefix);

  if (pageTitle !== undefined && href[0] !== '#') {
    var decodedPageTitle = decodeURIComponent(pageTitle);
    var relativePath = "./".concat(decodedPageTitle);
    return decodedHref.indexOf(relativePath) === 0 && href.indexOf(decodedFragment) === relativePath.length;
  }

  return decodedHref.indexOf(decodedFragment) > -1;
};
/**
 * Is Citation.
 * @param {!string} href
 * @param {!string} pageTitle - assumed to be encoded for links
 * @return {!boolean}
 */


var isCitation = function isCitation(href, pageTitle) {
  return isForSamePageTitleAndHasFragmentPrefix(href, CITE_FRAGMENT_PREFIX, pageTitle);
};
/**
 * Is Back Link.
 * @param {!string} href
 * @param {!string} pageTitle - assumed to be encoded for links
 * @return {!boolean}
 */


var isBackLink = function isBackLink(href, pageTitle) {
  return isForSamePageTitleAndHasFragmentPrefix(href, BACK_LINK_FRAGMENT_PREFIX, pageTitle);
};
/**
 * Determines if node is a text node containing only whitespace.
 * @param {!Node} node
 * @return {!boolean}
 */


var isWhitespaceTextNode = function isWhitespaceTextNode(node) {
  return Boolean(node) && node.nodeType === Node.TEXT_NODE && Boolean(node.textContent.match(/^\s+$/));
};
/**
 * Checks if element has a child anchor with a citation link.
 * @param {!Element} element
 * @return {!boolean}
 */


var hasCitationLink = function hasCitationLink(element) {
  var anchor = element.querySelector('a');
  return anchor && isCitation(anchor.hash);
};
/**
 * Get the reference text container.
 * @param {!Document} document
 * @param {!Element} source
 * @return {?HTMLElement}
 */


var getRefTextContainer = function getRefTextContainer(document, source) {
  var refTextContainerID = source.querySelector('A').getAttribute('href').split('#')[1];
  var refTextContainer = document.getElementById(refTextContainerID) || document.getElementById(decodeURIComponent(refTextContainerID));
  return refTextContainer;
};
/**
 * Extract reference text free of backlinks.
 * @param {!Document} document
 * @param {!Element} source
 * @return {!string}
 */


var collectRefText = function collectRefText(document, source) {
  var refTextContainer = getRefTextContainer(document, source);

  if (!refTextContainer) {
    return '';
  } // span.reference-text is for action=mobileview output


  var refTextSpan = refTextContainer.querySelector('span.mw-reference-text,span.reference-text');

  if (!refTextSpan) {
    return '';
  }

  return refTextSpan.innerHTML.trim();
};
/**
 * Get closest element to node which has class `reference`. If node itself has class `reference`
 * returns the node.
 * @param {!Node} sourceNode
 * @return {?HTMLElement}
 */


var closestReferenceClassElement = function closestReferenceClassElement(sourceNode) {
  if (_Polyfill__WEBPACK_IMPORTED_MODULE_3__["default"].matchesSelector(sourceNode, REFERENCE_SELECTOR)) {
    return sourceNode;
  }

  return _ElementUtilities__WEBPACK_IMPORTED_MODULE_1__["default"].findClosestAncestor(sourceNode, REFERENCE_SELECTOR);
};
/**
 * Reference item model.
 */


var ReferenceItem =
/**
 * ReferenceItem constructor.
 * @param {!string} id
 * @param {!DOMRect} rect
 * @param {?string} text
 * @param {?string} html
 * @param {?string} href
 */
function ReferenceItem(id, rect, text, html, href) {
  _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, ReferenceItem);

  this.id = id;
  this.rect = rect;
  this.text = text;
  this.html = html;
  this.href = href;
};
/**
 * Reference item model.
 */


var ReferenceLinkItem =
/**
 * ReferenceLinkItem construtor.
 * @param {!string} href
 * @param {?string} text
 */
function ReferenceLinkItem(href, text) {
  _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, ReferenceLinkItem);

  this.href = href;
  this.text = text;
};
/**
 * Converts node to ReferenceItem.
 * @param {!Document} document
 * @param {!Node} node
 * @return {!ReferenceItem}
 */


var referenceItemForNode = function referenceItemForNode(document, node) {
  return new ReferenceItem(closestReferenceClassElement(node).id, _NodeUtilities__WEBPACK_IMPORTED_MODULE_2__["default"].getBoundingClientRectAsPlainObject(node), node.textContent, collectRefText(document, node), node.querySelector('A').getAttribute('href'));
};
/**
 * Converts node to ReferenceLinkItem.
 * @param {!Document} document
 * @param {!Node} node
 * @return {!ReferenceItem}
 */


var referenceLinkItemForNode = function referenceLinkItemForNode(document, node) {
  return new ReferenceLinkItem(node.querySelector('A').getAttribute('href'), node.textContent);
};
/**
 * Container for nearby references including the index of the selected reference.
 */


var NearbyReferences =
/**
 * @param {!number} selectedIndex
 * @param {!Array.<ReferenceItem>} referencesGroup
 * @return {!NearbyReferences}
 */
function NearbyReferences(selectedIndex, referencesGroup) {
  _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, NearbyReferences);

  this.selectedIndex = selectedIndex;
  this.referencesGroup = referencesGroup;
};
/**
 * Closure around a node for getting previous or next sibling.
 *
 * @typedef SiblingGetter
 * @param {!Node} node
 * @return {?Node}
 */

/**
  * Closure around `collectedNodes` for collecting reference nodes.
  *
  * @typedef Collector
  * @param {!Node} node
  * @return {void}
  */

/**
 * Get adjacent non-whitespace node.
 * @param {!Node} node
 * @param {!SiblingGetter} siblingGetter
 * @return {?Node}
 */


var adjacentNonWhitespaceNode = function adjacentNonWhitespaceNode(node, siblingGetter) {
  var currentNode = node;

  do {
    currentNode = siblingGetter(currentNode);
  } while (isWhitespaceTextNode(currentNode));

  return currentNode;
};
/**
 * Collect adjacent reference nodes. The starting node is not collected.
 * @param {!Node} node
 * @param {!SiblingGetter} siblingGetter
 * @param {!Collector} nodeCollector
 * @return {void}
 */


var collectAdjacentReferenceNodes = function collectAdjacentReferenceNodes(node, siblingGetter, nodeCollector) {
  var currentNode = node;

  while (true) {
    currentNode = adjacentNonWhitespaceNode(currentNode, siblingGetter);

    if (!currentNode || currentNode.nodeType !== Node.ELEMENT_NODE || !hasCitationLink(currentNode)) {
      break;
    }

    nodeCollector(currentNode);
  }
};
/* eslint-disable valid-jsdoc */

/** @type {SiblingGetter} */


var prevSiblingGetter = function prevSiblingGetter(node) {
  return node.previousSibling;
};
/** @type {SiblingGetter} */


var nextSiblingGetter = function nextSiblingGetter(node) {
  return node.nextSibling;
};
/* eslint-enable valid-jsdoc */

/**
 * Collect nearby reference nodes.
 * @param {!Node} sourceNode
 * @return {!Array.<Node>}
 */


var collectNearbyReferenceNodes = function collectNearbyReferenceNodes(sourceNode) {
  var collectedNodes = [sourceNode];
  /* eslint-disable require-jsdoc */
  // These are `Collector`s.

  var collectedNodesUnshifter = function collectedNodesUnshifter(node) {
    return collectedNodes.unshift(node);
  };

  var collectedNodesPusher = function collectedNodesPusher(node) {
    return collectedNodes.push(node);
  };
  /* eslint-enable require-jsdoc */


  collectAdjacentReferenceNodes(sourceNode, prevSiblingGetter, collectedNodesUnshifter);
  collectAdjacentReferenceNodes(sourceNode, nextSiblingGetter, collectedNodesPusher);
  return collectedNodes;
};
/**
 * Reads the BACK_LINK_ATTRIBUTE and returns a list of back link hrefs
 * @param {Element} element to read the back links from
 * @return {Array.<string>} hrefs of the back links
 */


var getBackLinks = function getBackLinks(element) {
  var backLinksJSON = element.getAttribute(BACK_LINK_ATTRIBUTE);

  if (!backLinksJSON) {
    return [];
  }

  return JSON.parse(backLinksJSON);
};
/**
 * Collect nearby reference nodes.
 * @param {!Document} document
 * @param {!Element} target
 * @param {!string} href
 * @return {!{referenceId, referenceText, backLinks, href}}
 */


var collectReferencesForBackLink = function collectReferencesForBackLink(document, target, href) {
  var backLinkHrefs = getBackLinks(target);

  if (!backLinkHrefs || backLinkHrefs.length === 0) {
    return {};
  }

  var referenceId = href.split(BACK_LINK_FRAGMENT_PREFIX)[1];
  var referenceText;
  var backLinks = []; // Used as fallback. Send the href of the first back link as the event href

  var firstBackLinkHref = backLinkHrefs[0];

  for (var i = 0; i < backLinkHrefs.length; i++) {
    var backLinkHref = backLinkHrefs[i];
    var id = backLinkHref.split('#')[1];
    var element = document.getElementById(id);

    if (!element) {
      continue;
    }

    if (!referenceText) {
      referenceText = element.textContent.trim();
    } // Use an object with id to allow for adding more properties in the future


    backLinks.push({
      id: id
    });
  }

  return {
    referenceId: referenceId,
    referenceText: referenceText,
    backLinks: backLinks,
    href: firstBackLinkHref
  };
};
/**
 * Collect nearby references.
 * @param {!Document} document
 * @param {!Node} referenceElement
 * @return {!NearbyReferences}
 */


var collectNearbyReferenceForReferenceElement = function collectNearbyReferenceForReferenceElement(document, referenceElement) {
  var referenceNodes = collectNearbyReferenceNodes(referenceElement);
  var selectedIndex = referenceNodes.indexOf(referenceElement);
  var referencesGroup = referenceNodes.map(function (node) {
    return referenceItemForNode(document, node);
  });
  return new NearbyReferences(selectedIndex, referencesGroup);
};
/**
 * Collect nearby references.
 * @param {!Document} document
 * @param {!Node} sourceNode
 * @return {!NearbyReferences}
 */


var collectNearbyReferences = function collectNearbyReferences(document, sourceNode) {
  var sourceNodeParent = sourceNode.parentElement; // reference is the parent of the <a> tag

  return collectNearbyReferenceForReferenceElement(document, sourceNodeParent);
};
/**
 * Collect nearby references.
 * @param {!Document} document
 * @param {!Node} sourceNode
 * @return {!NearbyReferences}
 */


var collectNearbyReferencesAsText = function collectNearbyReferencesAsText(document, sourceNode) {
  var sourceNodeParent = sourceNode.parentElement;
  var referenceNodes = collectNearbyReferenceNodes(sourceNodeParent);
  var selectedIndex = referenceNodes.indexOf(sourceNodeParent);
  var referencesGroup = referenceNodes.map(function (node) {
    return referenceLinkItemForNode(document, node);
  });
  return new NearbyReferences(selectedIndex, referencesGroup);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  collectNearbyReferences: collectNearbyReferences,
  collectNearbyReferencesAsText: collectNearbyReferencesAsText,
  collectReferencesForBackLink: collectReferencesForBackLink,
  isBackLink: isBackLink,
  isCitation: isCitation,
  CLASS: CLASS,
  BACK_LINK_FRAGMENT_PREFIX: BACK_LINK_FRAGMENT_PREFIX,
  BACK_LINK_ATTRIBUTE: BACK_LINK_ATTRIBUTE,
  test: {
    adjacentNonWhitespaceNode: adjacentNonWhitespaceNode,
    closestReferenceClassElement: closestReferenceClassElement,
    collectAdjacentReferenceNodes: collectAdjacentReferenceNodes,
    collectNearbyReferenceNodes: collectNearbyReferenceNodes,
    collectRefText: collectRefText,
    getRefTextContainer: getRefTextContainer,
    hasCitationLink: hasCitationLink,
    isWhitespaceTextNode: isWhitespaceTextNode,
    nextSiblingGetter: nextSiblingGetter,
    prevSiblingGetter: prevSiblingGetter
  }
});

/***/ }),

/***/ "./src/transform/SectionUtilities.ts":
/*!*******************************************!*\
  !*** ./src/transform/SectionUtilities.ts ***!
  \*******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _HTMLUtilities__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./HTMLUtilities */ "./src/transform/HTMLUtilities.ts");


var Polyfill = __webpack_require__(/*! ./Polyfill */ "./src/transform/Polyfill.js").default;
/**
 * get Section Offsets object to handle quick scrolling in the table of contents
 * @param  {!HTMLBodyElement} body HTML body element DOM object.
 * @return {!object} section offsets object
 */


var getSectionOffsets = function getSectionOffsets(body) {
  var sections = Polyfill.querySelectorAll(body, 'section');
  return {
    sections: sections.reduce(function (results, section) {
      var id = section.getAttribute('data-mw-section-id');
      var heading = section && section.firstElementChild && section.firstElementChild.querySelector('.pcs-edit-section-title');

      if (id && parseInt(id) >= 1) {
        results.push({
          heading: heading && heading.innerHTML,
          id: parseInt(id),
          yOffset: section.offsetTop
        });
      }

      return results;
    }, [])
  };
};
/**
 * Get section of a given element
 * @param  {!Element} element
 * @return {!Element} section
 */


var getSectionOfElement = function getSectionOfElement(element) {
  var current = element;

  while (current) {
    if (isMediaWikiSectionElement(current)) {
      return current;
    }

    current = current.parentElement;
  }

  return null;
};
/**
 * Get section id of a given element
 * @param  {!Element} element
 * @return {!Element} section
 */


var getSectionIDOfElement = function getSectionIDOfElement(element) {
  var section = getSectionOfElement(element);
  return section && section.getAttribute('data-mw-section-id');
};
/**
 * Get lead paragraph text
 * @param  {!Document} document object.
 * @return {!string} lead paragraph text
 */


var getLeadParagraphText = function getLeadParagraphText(document) {
  var firstParagraphInASection = document.querySelector('#content-block-0>p');
  return firstParagraphInASection && firstParagraphInASection.innerText || '';
};
/**
 * @param {!Element} element - element to test
 * @return {boolean} true if this is a element that represents a MediaWiki section
 */


var isMediaWikiSectionElement = function isMediaWikiSectionElement(element) {
  if (!element) {
    return false;
  } // mobile-html output has `data-mw-section-id` attributes on section tags


  if (element.tagName === 'SECTION' && element.getAttribute('data-mw-section-id')) {
    return true;
  }

  return false;
};

var CLASS = {
  CONTROL: {
    BASE: 'pcs-section-control',
    SHOW: 'pcs-section-control-show',
    HIDE: 'pcs-section-control-hide'
  },
  SECTION: {
    HIDE: 'pcs-section-hidden'
  },
  HEADER: {
    HIDEABLE: 'pcs-section-hideable-header'
  }
};
var ID = {
  PREFIX: {
    CONTENT: 'pcs-section-content-',
    CONTROL: 'pcs-section-control-'
  },
  ARIA_COLLAPSE: 'pcs-section-aria-collapse',
  ARIA_EXPAND: 'pcs-section-aria-expand'
};

var getControlIdForSectionId = function getControlIdForSectionId(sectionId) {
  return ID.PREFIX.CONTROL + sectionId;
};

var getContentIdForSectionId = function getContentIdForSectionId(sectionId) {
  return ID.PREFIX.CONTENT + sectionId;
};
/**
 * @param {!Document} document - document for the control
 * @param {!string} sectionId - sectionId for the control
 * @return {Element} the control element
 */


var getControl = function getControl(document, sectionId) {
  var control = document.createElement('span');
  control.id = getControlIdForSectionId(sectionId);
  control.classList.add(CLASS.CONTROL.BASE);
  control.classList.add(CLASS.CONTROL.SHOW);
  return control;
};

var prepareForHiding = function prepareForHiding(document, sectionId, section, headerWrapper, header, expandText, collapseText) {
  var control = getControl(document, sectionId);

  if (document.getElementById(ID.ARIA_EXPAND) === null) {
    var ariaDescriptionExpand = document.createElement('span');
    ariaDescriptionExpand.setAttribute('id', ID.ARIA_EXPAND);
    ariaDescriptionExpand.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_0__["ARIA"].LABEL, expandText);
    control.appendChild(ariaDescriptionExpand);
  }

  if (document.getElementById(ID.ARIA_COLLAPSE) === null) {
    var ariaDescriptionCollapse = document.createElement('span');
    ariaDescriptionCollapse.setAttribute('id', ID.ARIA_COLLAPSE);
    ariaDescriptionCollapse.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_0__["ARIA"].LABEL, collapseText);
    control.appendChild(ariaDescriptionCollapse);
  }

  control.setAttribute('role', 'button');
  control.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_0__["ARIA"].LABELED_BY, ID.ARIA_EXPAND);

  if (headerWrapper && control) {
    headerWrapper.appendChild(control);
    headerWrapper.classList.add(CLASS.HEADER.HIDEABLE);
    headerWrapper.setAttribute('onclick', "pcs.c1.Sections.setHidden('".concat(sectionId, "', false);"));
  }

  var el = section.firstElementChild;
  var div = document.createElement('div');

  while (el) {
    var toRemove = el;
    el = el.nextElementSibling;

    if (toRemove === header) {
      continue;
    }

    section.removeChild(toRemove);
    div.appendChild(toRemove);
  }

  div.id = getContentIdForSectionId(sectionId);
  div.classList.add(CLASS.SECTION.HIDE);
  section.appendChild(div);
};

var setHidden = function setHidden(document, sectionId) {
  var hidden = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : true;
  var controlId = getControlIdForSectionId(sectionId);
  var contentId = getContentIdForSectionId(sectionId);
  var control = document.getElementById(controlId);
  var content = document.getElementById(contentId);

  if (!control || !content) {
    return;
  }

  if (hidden) {
    control.classList.remove(CLASS.CONTROL.HIDE);
    control.classList.add(CLASS.CONTROL.SHOW);
    content.classList.add(CLASS.SECTION.HIDE);
    control.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_0__["ARIA"].LABELED_BY, ID.ARIA_EXPAND);
  } else {
    control.classList.remove(CLASS.CONTROL.SHOW);
    control.classList.add(CLASS.CONTROL.HIDE);
    content.classList.remove(CLASS.SECTION.HIDE);
    control.setAttribute(_HTMLUtilities__WEBPACK_IMPORTED_MODULE_0__["ARIA"].LABELED_BY, ID.ARIA_COLLAPSE);
  }

  var header = control.parentElement;

  if (!header) {
    return;
  }

  header.setAttribute('onclick', "pcs.c1.Sections.setHidden('".concat(sectionId, "', ").concat(!hidden, ");"));
};

var getTopLevelSectionIdForElement = function getTopLevelSectionIdForElement(element) {
  var parent = element;

  while (parent = parent.parentElement) {
    if (parent.tagName !== 'SECTION') {
      continue;
    }

    if (!parent.parentElement || parent.parentElement.id !== 'pcs') {
      continue;
    }

    var sectionId = parent.getAttribute('data-mw-section-id');

    if (!sectionId) {
      continue;
    }

    return sectionId;
  }

  return;
};

var expandCollapsedSectionIfItContainsElement = function expandCollapsedSectionIfItContainsElement(document, element) {
  var sectionId = getTopLevelSectionIdForElement(element);

  if (!sectionId) {
    return;
  }

  setHidden(document, sectionId, false);
}; // Adds a HR before the indicated section
// Used to separate article content from collapsed sections


var createFoldHR = function createFoldHR(document, section) {
  if (!section.parentElement) {
    return;
  }

  var hr = document.createElement('hr');
  hr.classList.add('pcs-fold-hr');
  section.parentElement.insertBefore(hr, section);
};

/* harmony default export */ __webpack_exports__["default"] = ({
  createFoldHR: createFoldHR,
  expandCollapsedSectionIfItContainsElement: expandCollapsedSectionIfItContainsElement,
  getSectionIDOfElement: getSectionIDOfElement,
  getLeadParagraphText: getLeadParagraphText,
  getSectionOffsets: getSectionOffsets,
  prepareForHiding: prepareForHiding,
  setHidden: setHidden,
  getControlIdForSectionId: getControlIdForSectionId,
  isMediaWikiSectionElement: isMediaWikiSectionElement
});

/***/ }),

/***/ "./src/transform/ThemeTransform.js":
/*!*****************************************!*\
  !*** ./src/transform/ThemeTransform.js ***!
  \*****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _ThemeTransform_less__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./ThemeTransform.less */ "./src/transform/ThemeTransform.less");
/* harmony import */ var _ThemeTransform_less__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_ThemeTransform_less__WEBPACK_IMPORTED_MODULE_0__);

var CLASS_PREFIX = 'pcs-theme-'; // Theme to CSS classes.

var THEME = {
  DEFAULT: "".concat(CLASS_PREFIX, "default"),
  DARK: "".concat(CLASS_PREFIX, "dark"),
  SEPIA: "".concat(CLASS_PREFIX, "sepia"),
  BLACK: "".concat(CLASS_PREFIX, "black")
};
/**
 * @param {?Element} el element
 * @param {!string} theme
 * @return {void}
 */

var setThemeOnElement = function setThemeOnElement(el, theme) {
  if (!el) {
    return;
  } // Set the new theme.


  el.classList.add(theme); // Clear any previous theme.

  for (var key in THEME) {
    if (Object.prototype.hasOwnProperty.call(THEME, key) && THEME[key] !== theme) {
      el.classList.remove(THEME[key]);
    }
  }
};
/**
 * @param {!Document} document
 * @param {!string} theme
 * @return {void}
 */


var setTheme = function setTheme(document, theme) {
  var body = document.body;
  setThemeOnElement(body, theme); // the pcs element is necessary to allow
  // template editors to theme templates by
  // declaring styles for .themeclass .templateclass {
  // TemplateStyles are scoped to .mw-parser-outpt by parsoid
  // so without an intermediate div with the theme class,
  // the styles aren't applied

  var pcs = document.getElementById('pcs');
  setThemeOnElement(pcs, theme);
};
/**
 * Set a custom font-family onto the document body. For example, setting a font-family
 * of "serif" will use the default serif font on the current platform.
 * @param {!Document} document
 * @param {!string} font
 * @return {void}
 */


var setBodyFont = function setBodyFont(document, font) {
  document.body.style.fontFamily = font;
};

/* harmony default export */ __webpack_exports__["default"] = ({
  THEME: THEME,
  CLASS_PREFIX: CLASS_PREFIX,
  setTheme: setTheme,
  setBodyFont: setBodyFont
});

/***/ }),

/***/ "./src/transform/ThemeTransform.less":
/*!*******************************************!*\
  !*** ./src/transform/ThemeTransform.less ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ }),

/***/ "./src/transform/Throttle.js":
/*!***********************************!*\
  !*** ./src/transform/Throttle.js ***!
  \***********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "default", function() { return Throttle; });
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @babel/runtime/helpers/classCallCheck */ "./node_modules/@babel/runtime/helpers/classCallCheck.js");
/* harmony import */ var _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @babel/runtime/helpers/createClass */ "./node_modules/@babel/runtime/helpers/createClass.js");
/* harmony import */ var _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1__);



/** Function rate limiter. */
var Throttle = /*#__PURE__*/function () {
  /**
   * @param {!Window} window
   * @param {!number} period The nonnegative minimum number of milliseconds between function
   *                         invocations.
   * @param {!NotThrottledFunction} funktion
   */
  function Throttle(window, period, funktion) {
    _babel_runtime_helpers_classCallCheck__WEBPACK_IMPORTED_MODULE_0___default()(this, Throttle);

    this._window = window;
    this._period = period;
    this._function = funktion; // The upcoming invocation's context and arguments.

    this._context = undefined;
    this._arguments = undefined; // The previous invocation's result, timeout identifier, and last run timestamp.

    this._result = undefined;
    this._timeout = 0;
    this._timestamp = 0;
  }
  /**
   * The return value of the initial run is always undefined. The return value of subsequent runs is
   * always a previous result. The context and args used by a future invocation are always the most
   * recently supplied. Invocations, even if immediately eligible, are dispatched.
   * @param {?any} context
   * @param {?any} args The arguments passed to the underlying function.
   * @return {?any} The cached return value of the underlying function.
   */


  _babel_runtime_helpers_createClass__WEBPACK_IMPORTED_MODULE_1___default()(Throttle, [{
    key: "queue",
    value: function queue(context, args) {
      var _this = this;

      // Always update the this and arguments to the latest supplied.
      this._context = context;
      this._arguments = args;

      if (!this.pending()) {
        // Queue a new invocation.
        this._timeout = this._window.setTimeout(function () {
          _this._timeout = 0;
          _this._timestamp = Date.now();
          _this._result = _this._function.apply(_this._context, _this._arguments);
        }, this.delay());
      } // Always return the previous result.


      return this.result;
    }
    /** @return {?any} The cached return value of the underlying function. */

  }, {
    key: "result",
    get: function get() {
      return this._result;
    }
    /** @return {!boolean} true if an invocation is queued. */

  }, {
    key: "pending",
    value: function pending() {
      return Boolean(this._timeout);
    }
    /**
     * @return {!number} The nonnegative number of milliseconds until an invocation is eligible to
     *                   run.
     */

  }, {
    key: "delay",
    value: function delay() {
      if (!this._timestamp) {
        return 0;
      }

      return Math.max(0, this._period - (Date.now() - this._timestamp));
    }
    /**
     * Clears any pending invocation but doesn't clear time last invoked or prior result.
     * @return {void}
     */

  }, {
    key: "cancel",
    value: function cancel() {
      if (this._timeout) {
        this._window.clearTimeout(this._timeout);
      }

      this._timeout = 0;
    }
    /**
     * Clears any pending invocation, time last invoked, and prior result.
     * @return {void}
     */

  }, {
    key: "reset",
    value: function reset() {
      this.cancel();
      this._result = undefined;
      this._timestamp = 0;
    }
  }], [{
    key: "wrap",
    value:
    /**
     * The function to invoke when not throttled.
     *
     * @callback NotThrottledFunction
     */

    /**
     * A function wrapped in a Throttle.
     *
     * @callback WrappedFunction
     */

    /**
     * Wraps a function in a Throttle.
     * @param {!Window} window
     * @param {!number} period The nonnegative minimum number of milliseconds between function
     *                         invocations.
     * @param {!NotThrottledFunction} funktion
     * @return {!WrappedFunction}
     */
    function wrap(window, period, funktion) {
      var throttle = new Throttle(window, period, funktion);

      var throttled = function Throttled() {
        return throttle.queue(this, arguments);
      };

      throttled.result = function () {
        return throttle.result;
      };

      throttled.pending = function () {
        return throttle.pending();
      };

      throttled.delay = function () {
        return throttle.delay();
      };

      throttled.cancel = function () {
        return throttle.cancel();
      };

      throttled.reset = function () {
        return throttle.reset();
      };

      return throttled;
    }
  }]);

  return Throttle;
}();



/***/ })

/******/ })["default"];
});
//# sourceMappingURL=wikimedia-page-library-pcs.js.map