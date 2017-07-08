package com.bignerdranch.android.locatr;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.Manifest;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by sonalisahu on 5/8/17.
 */

public class MapAct extends SupportMapFragment {
    private static final String TAG = "MapFragment";
    private Location mCurrentLocation;
    private GoogleMap mMap;

    private static final LatLng LIBRARY = new LatLng(37.655621, -122.056668);
    private static final LatLng RAW = new LatLng(37.654568, -122.053514);
    private static final LatLng STADIUM = new LatLng(37.657082, -122.060401);
    private static final LatLng UNIVERSITY_VILLGE = new LatLng(37.659703, -122.063931);
    private static final LatLng UNIVERSITY_THEATER = new LatLng(37.659550, -122.057655);
    private static final LatLng BUS = new LatLng(37.658647,-122.061142);

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    //private ImageView mImageView;
    private GoogleApiClient mClient;

    public static MapAct newInstance() {
        return new MapAct();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_locatr, container, false);

        mImageView = (ImageView) v.findViewById(R.id.image);
        return v;
    }*/

    @Override
    public void onStart() {
        super.onStart();

        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_locatr, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(mClient.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                if (hasLocationPermission()) {
                    findImage();
                } else {
                    requestPermissions(LOCATION_PERMISSIONS,
                            REQUEST_LOCATION_PERMISSIONS);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    findImage();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void findImage() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(TAG, "Got a fix: " + location);
                        new SearchTask().execute(location);
                    }
                });
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private class SearchTask extends AsyncTask<Location,Void,Void> {
        private Location mLocation;

        @Override
        protected Void doInBackground(Location... params) {
            mLocation = params[0];
            FlickrFetchr fetchr = new FlickrFetchr();
            List<GalleryItem> items = fetchr.searchPhotos(params[0]);

            if (items.size() == 0) {
                return null;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // mImageView.setImageBitmap(mBitmap);
            mCurrentLocation = mLocation;

            updateUI();
        }
    }
    private void updateUI() {

       // LatLng myPoint = new LatLng(
             //   mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());


      //  MarkerOptions myMarker = new MarkerOptions()
            //    .position(myPoint);

        mMap.clear();
       // mMap.addMarker(myMarker);
        mMap.addMarker(new MarkerOptions()
                .position(LIBRARY)
                .title("LIBRARY")
                .snippet("ETA: 2,074,200"));

        mMap.addMarker(new MarkerOptions()
                .position(RAW)
                .title("RAW")
                .snippet("ETA: 4,627,300"));

        mMap.addMarker(new MarkerOptions()
                .position(UNIVERSITY_VILLGE)
                .title("UNIVERSITY_VILLGE")
                .snippet("ETA: 4,137,400"));

        mMap.addMarker(new MarkerOptions()
                .position(UNIVERSITY_THEATER)
                .title("UNIVERSITY_THEATER")
                .snippet("ETA: 1,738,800"));

        mMap.addMarker(new MarkerOptions()
                .position(STADIUM)
                .title("STADIUM")
                .snippet("ETA: 1,213,000"));

        LatLngBounds bounds = new LatLngBounds.Builder()
                //.include(myPoint)
                .include(LIBRARY)
                .include(RAW)
                .include(STADIUM)
                .include(UNIVERSITY_VILLGE)
                .include(UNIVERSITY_THEATER)
                .build();

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        mMap.animateCamera(update);
    }
}