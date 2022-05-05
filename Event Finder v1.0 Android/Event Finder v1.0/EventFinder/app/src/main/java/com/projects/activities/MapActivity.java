package com.projects.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.config.Config;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.libraries.utilities.MGUtilities;
import com.models.Event;
import com.projects.eventfinder.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Event event;
    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.event_location);

        event = (Event) this.getIntent().getSerializableExtra("event");

        Handler h = new Handler();
        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                finalizeMapRenderer();
            }
        }, Config.DELAY_SHOW_ANIMATION + 500);
    }

    private void finalizeMapRenderer() {
        FragmentManager fManager = getSupportFragmentManager();
        SupportMapFragment supportMapFragment = ((SupportMapFragment) fManager.findFragmentById(R.id.googleMap));
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        googleMap = _googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        createMarker();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        // Handle action bar actions click
        switch (item.getItemId()) {
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        // if nav drawer is opened, hide the action items
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private Marker createMarker() {
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title( MGUtilities.getStringFromResource(this, R.string.event_location) );
        markerOptions.snippet( MGUtilities.getStringFromResource(this, R.string.drag_to_move_around));

        markerOptions.position(new LatLng(event.getLat(), event.getLon()));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_pin));
        markerOptions.draggable(true);

        Marker mark = googleMap.addMarker(markerOptions);
        mark.setInfoWindowAnchor(Config.MAP_INFO_WINDOW_X_OFFSET, 0);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(Config.MAP_ZOOM_LEVEL);
        googleMap.moveCamera(zoom);

        CameraUpdate center = CameraUpdateFactory.newLatLng( new LatLng(event.getLat(), event.getLon()));
        googleMap.animateCamera(center);
        return mark;
    }
}
