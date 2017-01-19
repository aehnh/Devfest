package com.example.anders.devfest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("WTF", "WTFFFFFFFFFFFFFFFFFFFFFFFF");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
        } else {
            TextView textView1 = (TextView) findViewById(R.id.textView1);
            TextView textView2 = (TextView) findViewById(R.id.textView2);
            Button button = (Button) findViewById(R.id.button5);

            textView1.setText(user.getDisplayName());
            textView2.setText(user.getEmail());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomeActivity.this, AuthenticationActivity.class));
                }
            });
        }
    }
}
