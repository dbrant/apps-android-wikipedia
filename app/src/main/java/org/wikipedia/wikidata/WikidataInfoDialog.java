package org.wikipedia.wikidata;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.activity.FragmentUtil;
import org.wikipedia.dataclient.Service;
import org.wikipedia.dataclient.ServiceFactory;
import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.dataclient.wikidata.Entities;
import org.wikipedia.json.GsonUtil;
import org.wikipedia.page.ExtendedBottomSheetDialogFragment;
import org.wikipedia.page.PageTitle;
import org.wikipedia.util.DateUtil;
import org.wikipedia.util.FeedbackUtil;
import org.wikipedia.util.ResourceUtil;
import org.wikipedia.util.log.L;
import org.wikipedia.views.CommonsDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WikidataInfoDialog extends ExtendedBottomSheetDialogFragment {
    public interface Callback {
        void wikidataInfoLinkClicked(@NonNull PageTitle title);
    }

    private PageTitle pageTitle;
    private InfoAdapter adapter;
    private ProgressBar progressBar;
    private List<ListItem> infoItems = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    private static class ListItem {
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
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_wikidata_info, container);
        progressBar = rootView.findViewById(R.id.info_progress);

        RecyclerView readingListView = rootView.findViewById(R.id.info_list);
        readingListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readingListView.setAdapter(adapter);

        View closeButton = rootView.findViewById(R.id.close_button);
        FeedbackUtil.setToolbarButtonLongPressToast(closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        TextView infoTitle = rootView.findViewById(R.id.info_title);
        infoTitle.setText(pageTitle.getDisplayText());

        progressBar.setVisibility(View.VISIBLE);
        getEntities();

        return rootView;
    }

    private void getEntities() {
        disposables.add(ServiceFactory.get(new WikiSite(Service.WIKIDATA_URL)).getEntitiesByTitle(pageTitle.getDisplayText(), "enwiki")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entities -> {
                    infoItems.clear();
                    List<String> entitiesToRetrieve = new ArrayList<>();
                    Map<String, List<Entities.Claim>> claims = entities.getFirst().claims();
                    for (String key : claims.keySet()) {
                        List<Entities.Claim> claimList = claims.get(key) != null ? claims.get(key) : Collections.emptyList();
                        for (Entities.Claim claim : claimList) {
                            if (claim.getMainsnak() == null || claim.getMainsnak().getDataValue() == null) {
                                continue;
                            }

                            int prop = Integer.parseInt(claim.getMainsnak().getProperty().replace("P", ""));

                            String valueType = claim.getMainsnak().getDataValue().getType();
                            String infoVal = getDataValueString(GsonUtil.getDefaultGson(), claim.getMainsnak().getDataValue());

                            final int maxEntities = 50;
                            if (valueType.equals("wikibase-entityid") && entitiesToRetrieve.size() < maxEntities) {
                                entitiesToRetrieve.add(infoVal);
                            }
                            infoItems.add(new ListItem(prop, infoVal));
                        }
                    }

                    Collections.sort(infoItems, listItemComparator);

                    if (!entitiesToRetrieve.isEmpty()) {
                        populateEntityLabels(entitiesToRetrieve);
                    } else {
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }, t -> {
                    L.e(t);
                    progressBar.setVisibility(View.GONE);
                }));
    }

    private void populateEntityLabels(List<String> entitiesToRetrieve) {
        disposables.add(ServiceFactory.get(new WikiSite(Service.WIKIDATA_URL)).getWikidataLabels(StringUtils.join(entitiesToRetrieve, '|'), WikipediaApp.getInstance().getAppOrSystemLanguageCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entities -> {
                    for (String key : entities.entities().keySet()) {
                        for (ListItem item : infoItems) {
                            if (key.equals(item.getValue())) {
                                String label = entities.entities().get(key).getLabelForLang(WikipediaApp.getInstance().getAppOrSystemLanguageCode());
                                if (!TextUtils.isEmpty(label)) {
                                    item.setValue(label);
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }, t -> {
                    L.e(t);
                    progressBar.setVisibility(View.GONE);
                }));
    }

    private String getDataValueString(Gson gson, @NonNull Entities.DataValue dataValue) {
        JsonElement value = dataValue.getValue();
        String valueType = dataValue.getType();
        String infoVal;

        switch (valueType) {
            case "wikibase-entityid":
                Entities.EntityIdValue entityVal = gson.fromJson(value, Entities.EntityIdValue.class);
                infoVal = "Q" + entityVal.getNumericId();
                break;
            case "quantity":
                Entities.QuantityValue quantityVal = gson.fromJson(value, Entities.QuantityValue.class);
                infoVal = quantityVal.getAmount();
                try {
                    infoVal = Long.toString(Long.parseLong(infoVal));
                } catch (NumberFormatException e) {
                    //
                }
                break;
            case "time":
                Entities.TimeValue timeVal = gson.fromJson(value, Entities.TimeValue.class);
                infoVal = timeVal.getTime().replace("+", "");
                try {
                    infoVal = DateUtil.getShortDateString(DateUtil.iso8601DateParse(infoVal));
                } catch (Exception e) {
                    //
                }
                break;
            case "globecoordinate":
                Entities.LocationValue locationVal = gson.fromJson(value, Entities.LocationValue.class);
                infoVal = locationVal.getLatitude() + ", " + locationVal.getLongitude();
                break;
            case "monolingualtext":
                Entities.MonolingualTextValue textVal = gson.fromJson(value, Entities.MonolingualTextValue.class);
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
            propertyText.setText(StringUtils.capitalize(key < PropertyNames.NAMES.length ? PropertyNames.NAMES[key] : "P" + key));
            String infoValue = infoItems.get(position).getValue();
            valueText.setText(infoValue);

            if (ArrayUtils.contains(PropertiesPreferred.WIKILINK_PROPS, key)) {
                valueText.setOnClickListener(wikiValueClickListener);
                valueText.setTextColor(ResourceUtil.getThemedColor(requireContext(), R.attr.colorAccent));
            } else {
                valueText.setOnClickListener(null);
                valueText.setTextColor(ResourceUtil.getThemedColor(requireContext(), R.attr.colorPrimaryDark));
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
        public InfoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
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
            if (callback() != null) {
                callback().wikidataInfoLinkClicked(title);
            }
            dismiss();
        }
    };

    @Nullable
    private Callback callback() {
        return FragmentUtil.getCallback(this, Callback.class);
    }
}
