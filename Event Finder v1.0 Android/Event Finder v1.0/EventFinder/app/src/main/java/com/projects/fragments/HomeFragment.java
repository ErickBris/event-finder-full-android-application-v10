package com.projects.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.application.GlobalApplication;;
import com.config.Config;
import com.config.UIConfig;
import com.db.Queries;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.libraries.asynctask.MGAsyncTaskNoDialog;
import com.libraries.dataparser.DataParser;
import com.libraries.helpers.DateTimeHelper;
import com.libraries.location.MGLocationManagerUtils;
import com.libraries.seekbar.MGVerticalSeekBar;
import com.libraries.sliding.MGSliding;
import com.libraries.usersession.UserAccessSession;
import com.libraries.utilities.MGUtilities;
import com.models.DataResponse;
import com.models.Event;
import com.models.Favorite;
import com.projects.activities.DetailActivity;
import com.projects.eventfinder.MainActivity;
import com.projects.eventfinder.R;;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by mg on 27/07/16.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener,
        View.OnClickListener, GlobalApplication.OnLocationListener {

    private View viewInflate;
    private GoogleMap googleMap;
    SwipeRefreshLayout swipeRefresh;
    private Location myLocation;
    private HashMap<String, Event> markers;
    private ArrayList<Marker> markerList;
    private MGSliding frameSliding;
    private Event selectedEvent;
    Queries q;
    MGAsyncTaskNoDialog task;
    MGVerticalSeekBar seekBarRadius;
    ArrayList<Event> arrayData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewInflate = inflater.inflate(R.layout.fragment_map, null);
        return viewInflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(task != null)
            task.cancel(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh = (SwipeRefreshLayout) viewInflate.findViewById(R.id.swipe_refresh);
        swipeRefresh.setClickable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            swipeRefresh.setProgressViewOffset(false, 0,100);
        }

        q = GlobalApplication.getQueriesInstance(getActivity());

        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        showRefresh(false);

        frameSliding = (MGSliding) viewInflate.findViewById(R.id.frameSliding);
        Animation animationIn = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_up2);
        Animation animationOut = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_down2);

        frameSliding.setInAnimation(animationIn);
        frameSliding.setOutAnimation(animationOut);
        frameSliding.setVisibility(View.GONE);

        Button btnCurrentLocation = (Button)viewInflate.findViewById(R.id.btnCurrentLocation);
        btnCurrentLocation.setOnClickListener(this);

        showRefresh(true);

        markers = new HashMap<String, Event>();
        markerList = new ArrayList<Marker>();
        arrayData = new ArrayList<Event>();

        FragmentManager fManager = getActivity().getSupportFragmentManager();
        SupportMapFragment supportMapFragment = ((SupportMapFragment) fManager.findFragmentById(R.id.googleMap));
        if(supportMapFragment == null) {
            fManager = getChildFragmentManager();
            supportMapFragment = ((SupportMapFragment) fManager.findFragmentById(R.id.googleMap));
        }
        supportMapFragment.getMapAsync(this);

        int radius = UserAccessSession.getInstance(getActivity()).getHomeRadius();
        if(radius == 0) {
            radius = Config.SLIDER_HOME_DEFAULT_MIN_VALUE_IN_KM;
            UserAccessSession.getInstance(getActivity()).setHomeRadius(radius);
        }

        seekBarRadius = (MGVerticalSeekBar) viewInflate.findViewById(R.id.seekBarRadius);
        seekBarRadius.setMax(Config.SLIDER_HOME_NEARBY_MAX_VALUE_IN_KM);
        seekBarRadius.setProgress(radius);
        updateSlider(seekBarRadius.getProgress());
        seekBarRadius.setOnSeekBarChangeListener(new MGVerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress > 0) {
                    updateSlider(progress);
                }
                else {
                    seekBarRadius.setProgress(1);
                    updateSlider(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getData();
            }
        });

        GlobalApplication app = (GlobalApplication)getActivity().getApplication();
        app.sendScreenView(Config.kGAIScreenNameHome);
    }

    public void updateSlider(int progress) {
        String val = String.format("%d %s",
                progress,
                MGUtilities.getStringFromResource(getActivity(), R.string.km));

        TextView tvRadius = (TextView) viewInflate.findViewById(R.id.tvRadius);
        tvRadius.setText(val);

        UserAccessSession.getInstance(getActivity()).setHomeRadius(progress);
    }

    @Override
    public void onMapReady(GoogleMap _googleMap) {
        googleMap = _googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        try {
            googleMap.setMyLocationEnabled(true);
        }
        catch (SecurityException e) { }

        googleMap.setOnMapClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showMarker(marker);
                return true;
            }
        });
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                // TODO Auto-generated method stub
                myLocation = location;
            }
        });
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if(frameSliding.getVisibility() == View.VISIBLE)
                    frameSliding.setVisibility(View.INVISIBLE);
            }
        });

        showRefresh(false);
        if(!MGUtilities.isLocationEnabled(getActivity()) && GlobalApplication.currentLocation == null) {
            MGLocationManagerUtils utils = new MGLocationManagerUtils();
            utils.setOnAlertListener(new MGLocationManagerUtils.OnAlertListener() {
                @Override
                public void onPositiveTapped() {
                    startActivityForResult(
                            new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            Config.PERMISSION_REQUEST_LOCATION_SETTINGS);
                }

                @Override
                public void onNegativeTapped() {
                    showRefresh(false);
                }
            });
            utils.showAlertView(
                    getActivity(),
                    R.string.location_error,
                    R.string.gps_not_on,
                    R.string.go_to_settings,
                    R.string.cancel);
        }
        else {
            refetch();
        }
    }

    public void refetch() {
        showRefresh(true);
        GlobalApplication app = (GlobalApplication) getActivity().getApplication();
        app.setOnLocationListener(this, getActivity());
    }

    public void showRefresh(boolean show) {
        swipeRefresh.setRefreshing(show);
        swipeRefresh.setEnabled(show);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // TODO Auto-generated method stub
        showMarker(marker);
    }

    @Override
    public void onMapClick(LatLng point) {
        // TODO Auto-generated method stub
        frameSliding.setVisibility(View.INVISIBLE);
    }

    private void checkFave(View view, Event event) {
        Favorite fave = q.getFavoriteByEventId(event.getEvent_id());
        if(fave != null) {
            q.deleteFavorite(event.getEvent_id());
            ((ToggleButton) view).setChecked(false);
        }
        else {
            fave = new Favorite();
            fave.setEvent_id(event.getEvent_id());
            q.insertFavorite(fave);
            ((ToggleButton) view).setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
            case R.id.btnCurrentLocation:
                getMyLocation();
                break;
        }
    }

    public void addStoreMarkers() {
        if(googleMap != null)
            googleMap.clear();

        try {
            markerList.clear();
            markers.clear();
            for(Event entry: arrayData) {
                if(entry.getLat() == 0 || entry.getLon() == 0)
                    continue;

                Marker mark = createMarker(entry);
                markerList.add(mark);
                markers.put(mark.getId(), entry);
            }
            showBoundedMap();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Event> getAllEvents() {
        UserAccessSession accessSession = UserAccessSession.getInstance(getActivity());
        float radius = accessSession.getFilterDistance();

        ArrayList<Event> arrayData1 = q.getEvents();
        ArrayList<Event> arrayData = new ArrayList<Event>();
        if(GlobalApplication.currentLocation != null) {
            for(Event event : arrayData1) {
                Location locStore = new Location("Event");
                locStore.setLatitude(event.getLat());
                locStore.setLongitude(event.getLon());
                double userDistanceFromStore = GlobalApplication.currentLocation.distanceTo(locStore) / 1000;
                event.setDistance(userDistanceFromStore);

                if(event.getDistance() <= radius)
                    arrayData.add(event);
            }

            Collections.sort(arrayData, new Comparator<Event>() {
                @Override
                public int compare(Event t2, Event t1) {
                    if (t2.getDistance() < t1.getDistance())
                        return -1;
                    if (t2.getDistance() > t1.getDistance())
                        return 1;
                    return 0;
                }
            });
        }

        return arrayData;
    }


    private void getMyLocation() {
        if(myLocation == null) {
            MGUtilities.showAlertView(
                    getActivity(),
                    R.string.location_error,
                    R.string.cannot_determine_location);

            return;
        }

        addStoreMarkers();
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(Config.MAP_ZOOM_LEVEL);
        googleMap.moveCamera(zoom);
        CameraUpdate center = CameraUpdateFactory.newLatLng(
                new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));

        googleMap.animateCamera(center);
    }


    private void showBoundedMap() {
        if(markerList == null && markerList.size() == 0 ) {
            MGUtilities.showNotifier(this.getActivity(), MainActivity.offsetY, R.string.failed_data);
            return;
        }

        if(markerList.size() > 0) {
            LatLngBounds.Builder bld = new LatLngBounds.Builder();
            for (int i = 0; i < markerList.size(); i++) {
                Marker marker = markerList.get(i);
                bld.include(marker.getPosition());
            }

            LatLngBounds bounds = bld.build();
            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(bounds,
                            this.getResources().getDisplayMetrics().widthPixels,
                            this.getResources().getDisplayMetrics().heightPixels,
                            70));
        }
        else {
            MGUtilities.showNotifier(this.getActivity(), MainActivity.offsetY, R.string.no_results_found);
            Location loc = GlobalApplication.currentLocation;
            if(loc != null) {
                googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 70));
            }
        }
    }

    private Marker createMarker(Event event) {
        final MarkerOptions markerOptions = new MarkerOptions();
        Spanned name = Html.fromHtml(event.getTitle());
        name = Html.fromHtml(name.toString());
        Spanned storeAddress = Html.fromHtml(event.getAddress());
        storeAddress = Html.fromHtml(storeAddress.toString());
        markerOptions.title( name.toString() );

        String address = storeAddress.toString();
        if(address.length() > 50)
            address = storeAddress.toString().substring(0,  50) + "...";

        markerOptions.snippet(address);
        markerOptions.position(new LatLng(event.getLat(), event.getLon()));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_pin));

        Marker mark = googleMap.addMarker(markerOptions);
        mark.setInfoWindowAnchor(Config.MAP_INFO_WINDOW_X_OFFSET, 0);
        return mark;
    }

    public void getData() {

        if(arrayData == null)
            arrayData = new ArrayList<Event>();

        arrayData.clear();

        showRefresh(true);
        task = new MGAsyncTaskNoDialog(getActivity());
        task.setMGAsyncTaskListener(new MGAsyncTaskNoDialog.OnMGAsyncTaskListenerNoDialog() {

            @Override
            public void onAsyncTaskProgressUpdate(MGAsyncTaskNoDialog asyncTask) { }

            @Override
            public void onAsyncTaskPreExecute(MGAsyncTaskNoDialog asyncTask) { }

            @Override
            public void onAsyncTaskPostExecute(MGAsyncTaskNoDialog asyncTask) {
                // TODO Auto-generated method stub
                showRefresh(false);
                addStoreMarkers();
            }

            @Override
            public void onAsyncTaskDoInBackground(MGAsyncTaskNoDialog asyncTask) {
                // TODO Auto-generated method stub
                if(GlobalApplication.currentLocation != null) {
                    try {
                        float radius = seekBarRadius.getProgress() * Config.KM_TO_MILES;
                        String strUrl = String.format("%s?api_key=%s&lat=%s&lon=%s&radius=%s",
                                Config.GET_EVENTS_JSON_URL,
                                Config.API_KEY,
                                String.valueOf(GlobalApplication.currentLocation.getLatitude()),
                                String.valueOf(GlobalApplication.currentLocation.getLongitude()),
                                String.valueOf(radius));

                        Log.d("URL", strUrl);
                        DataResponse data = DataParser.getJSONFromUrlWithPostRequest(strUrl, null);
                        if (data == null)
                            return;

                        if (data.getEvents() != null && data.getEvents().size() > 0) {
                            for (Event event : data.getEvents()) {
                                q.deleteEvent(event.getEvent_id());
                                q.insertEvent(event);
                                arrayData.add(event);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    arrayData = getAllEvents();
                }
            }
        });
        task.execute();
    }

    @Override
    public void onLocationChanged(Location prevLoc, Location currentLoc) {

        if(getActivity() == null)
            return;

        if(getActivity().getApplication() == null)
            return;

        GlobalApplication app = (GlobalApplication) getActivity().getApplication();
        app.setOnLocationListener(null, getActivity());
        getData();
    }


    @Override
    public void onLocationRequestDenied() {
        showRefresh(false);
        MGUtilities.showAlertView(getActivity(), R.string.permission_error, R.string.permission_error_details_location);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.PERMISSION_REQUEST_LOCATION_SETTINGS) {
            if(MGUtilities.isLocationEnabled(getActivity()))
                refetch();
            else {
                showRefresh(false);
                Toast.makeText(getActivity(), R.string.location_error_not_turned_on, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showMarker(Marker marker) {
        final Event event = markers.get(marker.getId());
        selectedEvent = event;

        TextView tvDistanceSliding = (TextView) viewInflate.findViewById(R.id.tvDistanceSliding);
        if(GlobalApplication.currentLocation != null) {
            myLocation = GlobalApplication.currentLocation;
            Location loc = new Location("marker");
            loc.setLatitude(marker.getPosition().latitude);
            loc.setLongitude(marker.getPosition().longitude);

            double meters = myLocation.distanceTo(loc);
            double miles = meters / 1000;
            String str = String.format("%.1f %s",
                    miles,
                    MGUtilities.getStringFromResource(getActivity(), R.string.km));

            tvDistanceSliding.setText(str);
        }
        else {
            String strDistance = String.format("%.1f %s",
                    event.getDistance(),
                    MGUtilities.getStringFromResource(getActivity(), R.string.km));

            tvDistanceSliding.setText(strDistance);
        }

        TextView tvDistance = (TextView) viewInflate.findViewById(R.id.tvDistance);
        String strDistance = String.format("%.1f %s",
                event.getDistance(),
                MGUtilities.getStringFromResource(getActivity(), R.string.km));

        tvDistance.setText(strDistance);

        frameSliding.setVisibility(View.VISIBLE);
        ImageView imgViewThumb = (ImageView) viewInflate.findViewById(R.id.imgViewThumb);
        if(event.getPhoto_url() != null) {
            GlobalApplication.getImageLoaderInstance(getActivity())
                    .displayImage(event.getPhoto_url(), imgViewThumb, GlobalApplication.getDisplayImageOptionsInstance());
        }
        else {
            imgViewThumb.setImageResource(UIConfig.SLIDER_PLACEHOLDER);
        }

        imgViewThumb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("event", event);
                startActivity(i);
            }
        });

        TextView tvTitle = (TextView) viewInflate.findViewById(R.id.tvTitle);
        TextView tvSubtitle = (TextView) viewInflate.findViewById(R.id.tvSubtitle);
        TextView tvGoing = (TextView) viewInflate.findViewById(R.id.tvGoing);

        tvTitle.setText(Html.fromHtml(event.getTitle()));
        tvSubtitle.setText(Html.fromHtml(event.getAddress()));

        String strGoing = String.format("%d %s",
                event.getAttendees_total(),
                MGUtilities.getStringFromResource(getActivity(), R.string.going));

        tvGoing.setText(strGoing);

        ToggleButton toggleButtonFave = (ToggleButton) viewInflate.findViewById(R.id.toggleButtonFave);
        toggleButtonFave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                checkFave(v, event);
            }
        });

        Favorite fave = q.getFavoriteByEventId(event.getEvent_id());
        toggleButtonFave.setChecked(true);
        if(fave == null)
            toggleButtonFave.setChecked(false);

        String dateStrDay= DateTimeHelper.formateDateFromString("yyyy-MM-dd hh:mm:ss", "E", event.getGmt_date_set());
        String dateStrMonth = DateTimeHelper.formateDateFromString("yyyy-MM-dd hh:mm:ss", "MMM", event.getGmt_date_set());
        String dateStrDayNum = DateTimeHelper.formateDateFromString("yyyy-MM-dd hh:mm:ss", "dd", event.getGmt_date_set());

        TextView tvDateDayNum = (TextView) viewInflate.findViewById(R.id.tvDateDayNum);
        tvDateDayNum.setText(dateStrDayNum);

        TextView tvDateDayText = (TextView) viewInflate.findViewById(R.id.tvDateDayText);
        tvDateDayText.setText(dateStrDay);

        TextView tvDateMonth = (TextView) viewInflate.findViewById(R.id.tvDateMonth);
        tvDateMonth.setText(dateStrMonth);

        ImageView imgViewFeatured = (ImageView) viewInflate.findViewById(R.id.imgViewFeatured);
        if(event.getIs_featured() == 0) {
            imgViewFeatured.setVisibility(View.GONE);
        }
    }
}
