package com.example.anders.devfest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddRoomActivity extends AppCompatActivity {

    private EditText editText1;
    private Spinner spinner;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        setTitle("Create Room");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText1 = (EditText)findViewById(R.id.editText1);
        spinner = (Spinner)findViewById(R.id.spinner);
        editText2 = (EditText)findViewById(R.id.editText2);

        Integer[] items = new Integer[] {2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_create:
                String name = editText1.getText().toString();
                String description = editText2.getText().toString();
                Integer capacity = (Integer)spinner.getSelectedItem();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null) {
                    startActivity(new Intent(this, AuthenticationActivity.class));
                } else {
                    Room room = new Room(name, capacity, description, null, user.getUid());
                    // TODO instead of adding to HomeFragment.myRooms, add to firebase db
                    HomeFragment.myRooms.add(room);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
