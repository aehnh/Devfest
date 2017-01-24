package com.example.anders.devfest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
            case R.id.action_kick:
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ViewRoomActivity.this);
                builderSingle.setTitle("Kick");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewRoomActivity.this, android.R.layout.simple_selectable_list_item);
                final ArrayList<String> members = room.getMembers();
                final ArrayList<String> memberNames = room.getMemberNames();
                for(int i = 1; i < memberNames.size(); i++) {
                    arrayAdapter.add(memberNames.get(i));
                }

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(ViewRoomActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Are you sure you want to kick this user?");
                        builderInner.setPositiveButton("Kick", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                room.removeMember(memberNames.get(which + 1), members.get(which + 1));
                                databaseReference.child("RoomInfo").child(room.getKey()).setValue(room);
                                dialog.dismiss();
                            }
                        });
                        builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
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
