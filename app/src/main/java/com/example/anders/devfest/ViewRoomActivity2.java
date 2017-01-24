package com.example.anders.devfest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewRoomActivity2 extends AppCompatActivity {

    private Room room;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room2);

        setTitle("View Room");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        room = (Room)intent.getSerializableExtra("Room");

        TextView textView1 = (TextView)findViewById(R.id.textView1);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        TextView textView4 = (TextView)findViewById(R.id.textView4);

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
        getMenuInflater().inflate(R.menu.menu_view2, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_join:
                if(room.getSize() >= room.getCapacity()) {
                    Toast.makeText(this, "This room is full", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    room.addMember(user.getUid(), user.getDisplayName());
                    databaseReference.child("RoomInfo").child(room.getKey()).setValue(room);
                    Intent intent = new Intent(this, ViewRoomActivity.class);
                    intent.putExtra("Room", room);
                    startActivity(intent);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
