package com.example.anders.devfest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by q on 2017-01-24.
 */

public class ChatActivity extends Activity {

    private ArrayAdapter adapter;
    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ArrayList<ChatData> item=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        final String key = intent.getStringExtra("key");

        listView = (ListView) findViewById(R.id.chat);
        editText = (EditText) findViewById(R.id.word);
        sendButton = (Button) findViewById(R.id.send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatData chatData=new ChatData(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),editText.getText().toString());
                databaseReference.child("ChatInfo").child(key).push().setValue(chatData);
                editText.setText("");
            }
        });

        //Todo: change message to Room key
        databaseReference.child("ChatInfo").child(key).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                item.add(chatData);  // adapter에 추가합니다.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        adapter=new chatAdapter(getApplicationContext(),R.layout.lv_item,item);
        listView.setAdapter(adapter);
    }
}
