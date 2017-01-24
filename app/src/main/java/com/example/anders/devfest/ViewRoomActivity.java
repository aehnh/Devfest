package com.example.anders.devfest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewRoomActivity extends AppCompatActivity {

    private Room room;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        setTitle("View Room");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        room = (Room)intent.getSerializableExtra("Room");

        TextView textView1 = (TextView)findViewById(R.id.textView1);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("key", room.getKey());
                startActivity(intent);
            }
        });

        textView1.setText(room.getName());
        ArrayList<String> members = room.getMemberNames();
        String stringized = "";
        Boolean ownered = false;
        for(int i = 0; i < members.size(); i++) {
            String member = members.get(i);
            stringized += member;
            if(!ownered && member.equals(room.getOwnerName())) {
                stringized += " (owner)";
            }
            if(i != room.getSize() - 1) {
                stringized += ", ";
            }
        }
        textView2.setText(stringized);
        textView3.setText(room.getDescription());
        textView4.setText("Members (" + Integer.toString(room.getSize()) + " / " + Integer.toString(room.getCapacity()) + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(room.getOwner().equals(user.getUid())) {
            getMenuInflater().inflate(R.menu.menu_manage, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_view, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Disband")
                        .setMessage("Do you wish to disband this room?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("RoomInfo").child(room.getKey()).removeValue();
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            case R.id.action_leave:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                room.removeMember(user.getUid(), user.getDisplayName());
                databaseReference.child("RoomInfo").child(room.getKey()).setValue(room);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
