package com.example.anders.devfest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

/**
 * Created by q on 2017-01-20.
 */

public class MapFragment2 extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    private GoogleMap googleMap;
    private HashMap<Marker,Integer> mMarkers=new HashMap<>();

    public void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.map, container, false );


        mMapView=(MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        mMapView.getMapAsync(this);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void onMapReady(final GoogleMap map){

        googleMap=map;

        GpsInfo gps= new GpsInfo(getActivity());
        LatLng MyPlace=new LatLng(37.56,126.97);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            MyPlace=new LatLng(latitude,longitude);

            //경도 위도 출력력
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
            Log.d("alert","22222222222");
        }

        Marker m=googleMap.addMarker(new MarkerOptions().position(MyPlace).title("N1").snippet("menboong"));
        mMarkers.put(m,0);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(MyPlace));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

    }
}
