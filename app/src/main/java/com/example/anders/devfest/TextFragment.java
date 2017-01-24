package com.example.anders.devfest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.example.anders.devfest.HomeActivity.Rooms;


public class TextFragment extends Fragment {

    private RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter = null;

    public TextFragment() {}

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.floatingActionButton).setVisibility(View.GONE);

        ArrayList<Room> inner = new ArrayList<Room>();
        double latitude=0;
        double longitude=0;

        GpsInfo gps=new GpsInfo(getContext());
        if (gps.isGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            //경도 위도 출력력
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
            Log.d("alert","22222222222");
        }

        for(int i=0;i<Rooms.size();i++){

            float distance;
            Room r;

            Location loc1=new Location("loc1");
            Location loc2=new Location("loc2");
            r = Rooms.get(i);


            loc1.setLatitude(r.getLatitude());
            loc1.setLongitude(r.getLongitude());

            loc2.setLatitude(latitude);
            loc2.setLongitude(longitude);

            distance=loc1.distanceTo(loc2);

            Log.d("distance",Float.toString(distance));
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
            int finalized = sharedPreferences.getInt("distance", 50);
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(distance / 1000 <= finalized && !r.getMembers().contains(uid)) {
                inner.add(r);
            }
        }

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapter(inner, R.layout.card_item2, TextFragment.this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void onClickHandler(Room room) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ViewRoomActivity2.class);
        intent.putExtra("Room", room);
        startActivity(intent);
    }
}
