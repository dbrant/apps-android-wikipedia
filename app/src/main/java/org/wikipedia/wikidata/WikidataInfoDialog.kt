package org.wikipedia.wikidata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.wikipedia.R
import org.wikipedia.WikipediaApp
import org.wikipedia.activity.FragmentUtil.getCallback
import org.wikipedia.databinding.DialogWikidataInfoBinding
import org.wikipedia.dataclient.Service
import org.wikipedia.dataclient.ServiceFactory
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.dataclient.wikidata.Entities
import org.wikipedia.dataclient.wikidata.Entities.LocationValue
import org.wikipedia.dataclient.wikidata.Entities.QuantityValue
import org.wikipedia.json.GsonUtil
import org.wikipedia.page.ExtendedBottomSheetDialogFragment
import org.wikipedia.page.PageTitle
import org.wikipedia.util.DateUtil
import org.wikipedia.util.FeedbackUtil
import org.wikipedia.util.ResourceUtil
import org.wikipedia.util.log.L
import org.wikipedia.views.CommonsDraweeView
import java.util.*

class WikidataInfoDialog : ExtendedBottomSheetDialogFragment() {
    interface Callback {
        fun wikidataInfoLinkClicked(title: PageTitle)
    }

    private class ListItem constructor(val p: Int, var value: String)

    private var _binding: DialogWikidataInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var pageTitle: PageTitle
    private val adapter = InfoAdapter()
    private val infoItems = mutableListOf<ListItem>()
    private val disposables = CompositeDisposable()

    private val listItemComparator = Comparator { lhs: ListItem, rhs: ListItem ->
        val pos1 = PropertiesPreferred.PREFERRED_PROPS.indexOf(lhs.p)
        val pos2 = PropertiesPreferred.PREFERRED_PROPS.indexOf(rhs.p)
        if (pos1 == -1 && pos2 >= 0) {
            return@Comparator 1
        } else if (pos1 >= 0 && pos2 == -1) {
            return@Comparator -1
        } else if (pos1 == -1 && pos2 == -1) {
            return@Comparator lhs.p.compareTo(rhs.p)
        }
        pos1.compareTo(pos2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitle = requireArguments().getParcelable("title")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogWikidataInfoBinding.inflate(inflater, container, false)

        binding.infoList.layoutManager = LinearLayoutManager(activity)
        binding.infoList.adapter = adapter
        FeedbackUtil.setButtonLongPressToast(binding.closeButton)
        binding.closeButton.setOnClickListener { dismiss() }

        binding.infoTitle.text = pageTitle.displayText
        binding.infoProgress.visibility = View.VISIBLE
        loadEntities()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposables.clear()
    }

    private fun loadEntities() {
        disposables.add(ServiceFactory.get(WikiSite(Service.WIKIDATA_URL)).getEntitiesByTitle(pageTitle.displayText, "enwiki")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { binding.infoProgress.visibility = View.GONE }
                .subscribe({ entities ->
                    infoItems.clear()
                    val entitiesToRetrieve = mutableListOf<String>()
                    val claims = entities.first!!.claims()
                    for (key in claims.keys) {
                        val claimList = if (claims[key] != null) claims[key] else emptyList()
                        for (claim in claimList!!) {
                            if (claim.mainsnak == null || claim.mainsnak!!.dataValue == null) {
                                continue
                            }
                            val prop = claim.mainsnak!!.property.replace("P", "").toInt()
                            val valueType = claim.mainsnak!!.dataValue!!.type
                            val infoVal = getDataValueString(GsonUtil.getDefaultGson(), claim.mainsnak!!.dataValue!!)
                            val maxEntities = 50
                            if (valueType == "wikibase-entityid" && entitiesToRetrieve.size < maxEntities) {
                                entitiesToRetrieve.add(infoVal)
                            }
                            infoItems.add(ListItem(prop, infoVal))
                        }
                    }
                    Collections.sort(infoItems, listItemComparator)
                    if (entitiesToRetrieve.isNotEmpty()) {
                        populateEntityLabels(entitiesToRetrieve)
                    } else {
                        adapter.notifyDataSetChanged()
                    }
                }) { L.e(it) })
    }

    private fun populateEntityLabels(entitiesToRetrieve: List<String>) {
        disposables.add(ServiceFactory.get(WikiSite(Service.WIKIDATA_URL)).getWikidataLabels(entitiesToRetrieve.joinToString("|"), WikipediaApp.getInstance().appOrSystemLanguageCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { binding.infoProgress.visibility = View.GONE }
                .subscribe({ entities ->
                    for (key in entities.entities().keys) {
                        for (item in infoItems) {
                            if (key == item.value) {
                                val label = entities.entities()[key]!!.getLabelForLang(WikipediaApp.getInstance().appOrSystemLanguageCode)
                                if (label.isNotEmpty()) {
                                    item.value = label
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }) { L.e(it) })
    }

    private fun getDataValueString(gson: Gson, dataValue: Entities.DataValue): String {
        val value = dataValue.value
        val valueType = dataValue.type
        var infoVal: String
        when (valueType) {
            "wikibase-entityid" -> {
                val entityVal = gson.fromJson(value, Entities.EntityIdValue::class.java)
                infoVal = "Q" + entityVal.numericId
            }
            "quantity" -> {
                val quantityVal = gson.fromJson(value, QuantityValue::class.java)
                infoVal = quantityVal.amount
                try {
                    infoVal = infoVal.toLong().toString()
                } catch (e: NumberFormatException) {
                    //
                }
            }
            "time" -> {
                val timeVal = gson.fromJson(value, Entities.TimeValue::class.java)
                infoVal = timeVal.time.replace("+", "")
                try {
                    infoVal = DateUtil.getShortDateString(DateUtil.iso8601DateParse(infoVal))
                } catch (e: Exception) {
                    //
                }
            }
            "globecoordinate" -> {
                val locationVal = gson.fromJson(value, LocationValue::class.java)
                infoVal = locationVal.latitude.toString() + ", " + locationVal.longitude
            }
            "monolingualtext" -> {
                val textVal = gson.fromJson(value, Entities.MonolingualTextValue::class.java)
                infoVal = textVal.text
            }
            "string" -> infoVal = gson.fromJson(value, String::class.java)
            else -> infoVal = value.toString()
        }
        return infoVal
    }

    private inner class InfoItemHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemPosition = 0
        private val propertyText = itemView.findViewById<TextView>(R.id.info_property)
        private val valueText = itemView.findViewById<TextView>(R.id.info_value)
        private val valueImage = itemView.findViewById<CommonsDraweeView>(R.id.info_image)

        fun bindItem(position: Int) {
            itemPosition = position
            val key = infoItems[position].p
            propertyText.text = (if (key < PropertyNames.NAMES.size) PropertyNames.NAMES[key] else "P$key").capitalize(Locale.getDefault())
            val infoValue = infoItems[position].value
            valueText.text = infoValue
            if (PropertiesPreferred.WIKILINK_PROPS.contains(key)) {
                valueText.setOnClickListener(wikiValueClickListener)
                valueText.setTextColor(ResourceUtil.getThemedColor(requireContext(), R.attr.colorAccent))
            } else {
                valueText.setOnClickListener(null)
                valueText.setTextColor(ResourceUtil.getThemedColor(requireContext(), R.attr.colorPrimaryDark))
            }

            // FIXME: HACK
            if (infoValue.toLowerCase(Locale.ROOT).endsWith(".jpg") ||
                    infoValue.toLowerCase(Locale.ROOT).endsWith(".png") ||
                    infoValue.toLowerCase(Locale.ROOT).endsWith(".svg") ||
                    infoValue.toLowerCase(Locale.ROOT).endsWith(".jpeg") ||
                    infoValue.toLowerCase(Locale.ROOT).endsWith(".tif") ||
                    infoValue.toLowerCase(Locale.ROOT).endsWith(".tiff")) {
                valueText.visibility = View.GONE
                valueImage.visibility = View.VISIBLE
                valueImage.loadImage("File:$infoValue")
            } else {
                valueText.visibility = View.VISIBLE
                valueImage.visibility = View.GONE
            }
            itemView.setBackgroundColor(ContextCompat.getColor(requireContext(), if (position % 2 == 0) R.color.base90 else android.R.color.transparent))
        }
    }

    private inner class InfoAdapter : RecyclerView.Adapter<InfoItemHolder>() {
        override fun getItemCount(): Int {
            return infoItems.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, pos: Int): InfoItemHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_wikidata_info_entry, parent, false)
            return InfoItemHolder(view)
        }

        override fun onBindViewHolder(holder: InfoItemHolder, pos: Int) {
            holder.bindItem(pos)
        }
    }

    private val wikiValueClickListener = View.OnClickListener { view ->
        val title = PageTitle((view as TextView).text.toString(), pageTitle.wikiSite)
        callback()?.wikidataInfoLinkClicked(title)
        dismiss()
    }

    private fun callback(): Callback? {
        return getCallback(this, Callback::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: PageTitle?): WikidataInfoDialog {
            return WikidataInfoDialog().apply { arguments = bundleOf("title" to title) }
        }
    }
}
