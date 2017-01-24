package com.example.anders.devfest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;

    public HomeFragment() {}

    private FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference();
    public static ArrayList<Room> Rooms=new ArrayList<>();
    public static ArrayList<Room> myRooms=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Rooms=new ArrayList<>();
        myRooms=new ArrayList<>();
        databaseReference.child("RoomInfo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                Room room=dataSnapshot.getValue(Room.class);
                Rooms.add(room);

                if(room.getMembers().contains(uid)) {
                    myRooms.add(room);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {/*

                Room room=dataSnapshot.getValue(Room.class);
                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                for(int i=0;i<Rooms.size();i++){

                    if(Rooms.get(i).getName()==room.getName()) {

                        if(Rooms.get(i).getMembers().contains(uid) && !room.getMembers().contains(uid))
                            myRooms.remove(Rooms.get(i));
                        else if(!Rooms.get(i).getMembers().contains(uid) && room.getMembers().contains(uid))
                            myRooms.add(room);

                        Rooms.set(i,room);
                        break;
                    }
                }  */
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
               /* Room room=dataSnapshot.getValue(Room.class);

                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                for(int i=0;i<Rooms.size();i++){

                    if(Rooms.get(i).getName()==room.getName()) {

                        if(room.getMembers().contains(uid))
                            myRooms.remove(room);

                        Rooms.remove(i);
                        break;
                    }
                } */
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddRoomActivity.class));
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapter(myRooms, HomeFragment.this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("My Rooms");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onClickHandler() {
        startActivity(new Intent(getActivity().getApplicationContext(), ViewRoomActivity.class));
    }
}
