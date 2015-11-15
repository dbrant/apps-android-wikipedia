package org.wikipedia.page.lite;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Sprite;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngZoom;
import com.mapbox.mapboxsdk.views.MapView;

import org.wikipedia.R;
import org.wikipedia.page.PageFragmentBase;

public class PageStoryGeo extends PageStoryItem {
    private double latitude;
    private double longitude;
    private static Sprite markerIconPassive;


    public PageStoryGeo(PageFragmentBase parent, double lat, double lon) {
        super(parent);
        this.latitude = lat;
        this.longitude = lon;
    }

    @Override
    public View renderIntoView(LayoutInflater inflater, ViewGroup container, LinkMovementMethod lmm) {
        View rootView = inflater.inflate(R.layout.item_page_story_geo, container, false);
        MapView mapView = (MapView) rootView.findViewById(R.id.mapview);

        mapView.setAccessToken(inflater.getContext().getString(R.string.mapbox_public_token));
        mapView.onCreate(null);
        mapView.setStyleUrl("asset://mapstyle.json");

        if (markerIconPassive == null) {
            markerIconPassive = mapView.getSpriteFactory().fromResource(R.drawable.ic_map_marker);
        }

        LatLngZoom pos = new LatLngZoom(latitude, longitude, 10.0);
        mapView.setCenterCoordinate(pos, true);

        mapView.removeAllAnnotations();

        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("")
                .icon(markerIconPassive);
        mapView.addMarker(options);

        return rootView;
    }
}