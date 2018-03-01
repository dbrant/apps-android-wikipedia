package org.wikipedia.wikidata;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.wikipedia.R;
import org.wikipedia.history.HistoryEntry;
import org.wikipedia.page.ExtendedBottomSheetDialogFragment;
import org.wikipedia.page.PageActivity;
import org.wikipedia.page.PageTitle;
import org.wikipedia.server.wikidata.WdEntities;
import org.wikipedia.server.wikidata.WdEntityService;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.ResourceUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.views.CommonsDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WikidataInfoDialog extends ExtendedBottomSheetDialogFragment {
    private PageTitle pageTitle;
    private InfoAdapter adapter;
    private ProgressBar progressBar;
    private List<ListItem> infoItems = new ArrayList<>();

    private class ListItem {
        private int p;
        private String value;

        ListItem(int p, String value) {
            this.p = p;
            this.value = value;
        }
        public Integer getP() {
            return p;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }

    private Comparator<ListItem> listItemComparator = (lhs, rhs) -> {
        Integer pos1 = ArrayUtils.indexOf(PropertiesPreferred.PREFERRED_PROPS, lhs.getP());
        Integer pos2 = ArrayUtils.indexOf(PropertiesPreferred.PREFERRED_PROPS, rhs.getP());
        if (pos1 == -1 && pos2 >= 0) {
            return 1;
        } else if (pos1 >= 0 && pos2 == -1) {
            return -1;
        } else if (pos1 == -1 && pos2 == -1) {
            return lhs.getP().compareTo(rhs.getP());
        }
        return pos1.compareTo(pos2);
    };

    public static WikidataInfoDialog newInstance(PageTitle title) {
        WikidataInfoDialog dialog = new WikidataInfoDialog();
        Bundle args = new Bundle();
        args.putParcelable("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTitle = getArguments().getParcelable("title");
        adapter = new InfoAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_wikidata_info, container);
        progressBar = (ProgressBar) rootView.findViewById(R.id.info_progress);

        RecyclerView readingListView = (RecyclerView) rootView.findViewById(R.id.info_list);
        readingListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readingListView.setAdapter(adapter);

        View closeButton = rootView.findViewById(R.id.close_button);
        FeedbackUtil.setToolbarButtonLongPressToast(closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextView infoTitle = (TextView) rootView.findViewById(R.id.info_title);
        infoTitle.setText(pageTitle.getDisplayText());

        progressBar.setVisibility(View.VISIBLE);
        WdEntityService service = new WdEntityService();
        service.entitiesForTitle(pageTitle.getDisplayText(), onLoadCallback);

        return rootView;
    }


    private WdEntityService.EntityCallback onLoadCallback = new WdEntityService.EntityCallback() {
        @Override
        public void success(WdEntities entities) {
            if (!isAdded()) {
                return;
            }

            infoItems.clear();
            List<String> entitiesToRetrieve = new ArrayList<>();

            Gson gson = new Gson();
            Map<Object, WdEntities.Claim[]> claims = entities.getClaims();
            if (claims != null) {
                for (Object key : claims.keySet()) {
                    for (WdEntities.Claim claim : claims.get(key)) {
                        if (claim.getMainsnak() == null || claim.getMainsnak().getDataValue() == null
                                || claim.getMainsnak().getProperty() == null) {
                            continue;
                        }

                        int prop = Integer.parseInt(claim.getMainsnak().getProperty().replace("P", ""));

                        String valueType = claim.getMainsnak().getDataValue().getType();
                        String infoVal = getDataValueString(gson, claim.getMainsnak().getDataValue());

                        if (valueType.equals("wikibase-entityid")) {
                            entitiesToRetrieve.add(infoVal);
                        }
                        infoItems.add(new ListItem(prop, infoVal));
                    }
                }
            }

            Collections.sort(infoItems, listItemComparator);

            if (!entitiesToRetrieve.isEmpty()) {
                populateEntityLabels(entitiesToRetrieve);
            } else {
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void failure(Throwable error) {
            if (!isAdded()) {
                return;
            }
            L.e("Wikidata fetch error: " + error);
            progressBar.setVisibility(View.GONE);
        }
    };

    private void populateEntityLabels(List<String> entitiesToRetrieve) {

        WdEntityService service = new WdEntityService();
        service.entityLabelsForIds(entitiesToRetrieve, new WdEntityService.EntityCallback() {
            @Override
            public void success(WdEntities entities) {
                if (!isAdded() || entities == null || entities.getQItems() == null) {
                    return;
                }
                for (Object key : entities.getQItems().keySet()) {
                    String qid = entities.getQItems().get(key).getId();
                    for (ListItem item : infoItems) {
                        String label = entities.getQItems().get(key).getLabel("en");
                        if (qid != null && qid.equals(item.getValue()) && !TextUtils.isEmpty(label)) {
                            item.setValue(label);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(Throwable error) {
                if (!isAdded()) {
                    return;
                }
                L.e("Wikidata fetch error: " + error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private String getDataValueString(Gson gson, @NonNull WdEntities.DataValue dataValue) {
        JsonElement value = dataValue.getValue();
        String valueType = dataValue.getType();
        String infoVal;

        switch (valueType) {
            case "wikibase-entityid":
                WdEntities.EntityIdValue entityVal = gson.fromJson(value, WdEntities.EntityIdValue.class);
                infoVal = "Q" + entityVal.getNumericId();
                break;
            case "quantity":
                WdEntities.QuantityValue quantityVal = gson.fromJson(value, WdEntities.QuantityValue.class);
                infoVal = quantityVal.getAmount();
                break;
            case "time":
                WdEntities.TimeValue timeVal = gson.fromJson(value, WdEntities.TimeValue.class);
                infoVal = timeVal.getTime();
                break;
            case "globecoordinate":
                WdEntities.LocationValue locationVal = gson.fromJson(value, WdEntities.LocationValue.class);
                infoVal = locationVal.getLatitude() + ", " + locationVal.getLongitude();
                break;
            case "monolingualtext":
                WdEntities.MonolingualTextValue textVal = gson.fromJson(value, WdEntities.MonolingualTextValue.class);
                infoVal = textVal.getText();
                break;
            case "string":
                infoVal = gson.fromJson(value, String.class);
                break;
            default:
                infoVal = value.toString();
        }
        return infoVal;
    }

    private class InfoItemHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private int itemPosition;
        private TextView propertyText;
        private TextView valueText;
        private CommonsDraweeView valueImage;

        InfoItemHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            propertyText = itemView.findViewById(R.id.info_property);
            valueText = itemView.findViewById(R.id.info_value);
            valueImage = itemView.findViewById(R.id.info_image);
        }

        public void bindItem(int position) {
            this.itemPosition = position;
            int key = infoItems.get(position).getP();
            propertyText.setText(StringUtils.capitalize(key < PropertyNames.NAMES.length ? PropertyNames.NAMES[key] : "Unknown property"));
            String infoValue = infoItems.get(position).getValue();
            valueText.setText(infoValue);

            if (ArrayUtils.contains(PropertiesPreferred.WIKILINK_PROPS, key)) {
                valueText.setOnClickListener(wikiValueClickListener);
                valueText.setTextColor(getResources().getColor(ResourceUtil.getThemedAttributeId(getContext(), R.attr.colorAccent)));
            } else {
                valueText.setOnClickListener(null);
                valueText.setTextColor(getResources().getColor(ResourceUtil.getThemedAttributeId(getContext(), R.attr.colorPrimaryDark)));
            }

            // FIXME: HACK
            if (infoValue.toLowerCase().endsWith(".jpg")
                    || infoValue.toLowerCase().endsWith(".png")
                    || infoValue.toLowerCase().endsWith(".svg")
                    || infoValue.toLowerCase().endsWith(".jpeg")
                    || infoValue.toLowerCase().endsWith(".tif")
                    || infoValue.toLowerCase().endsWith(".tiff")) {
                valueText.setVisibility(View.GONE);
                valueImage.setVisibility(View.VISIBLE);
                valueImage.loadImage("File:" + infoValue);
            } else {
                valueText.setVisibility(View.VISIBLE);
                valueImage.setVisibility(View.GONE);
            }

            itemView.setBackgroundColor(getResources().getColor(position % 2 == 0 ? R.color.base90 : android.R.color.transparent));
        }
    }

    private final class InfoAdapter extends RecyclerView.Adapter<InfoItemHolder> {
        @Override
        public int getItemCount() {
            return infoItems.size();
        }

        @Override
        public InfoItemHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_wikidata_info_entry, parent, false);
            return new InfoItemHolder(view);
        }

        @Override
        public void onBindViewHolder(InfoItemHolder holder, int pos) {
            holder.bindItem(pos);
        }
    }

    private View.OnClickListener wikiValueClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PageTitle title = new PageTitle(((TextView) view).getText().toString(), pageTitle.getWikiSite());
            startActivity(PageActivity.newIntentForNewTab(getActivity(),
                    new HistoryEntry(title, HistoryEntry.SOURCE_INTERNAL_LINK), title));
            dismiss();
        }
    };
}
