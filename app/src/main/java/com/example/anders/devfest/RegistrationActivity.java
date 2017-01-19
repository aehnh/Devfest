package com.example.anders.devfest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("user status", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("user status", "onAuthStateChanged:signed_out");
                }
            }
        };

        final EditText editText1 = (EditText)findViewById(R.id.editText1);
        final EditText editText2 = (EditText)findViewById(R.id.editText2);
        final EditText editText3 = (EditText)findViewById(R.id.editText3);
        final EditText editText4 = (EditText)findViewById(R.id.editText4);
        Button button = (Button)findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editText1.getText().toString();
                final String username = editText2.getText().toString();
                final String password = editText3.getText().toString();
                String password2 = editText4.getText().toString();

                if(!password.equals(password2)) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                } else if(password.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password must be at least 6 characters",
                            Toast.LENGTH_SHORT).show();
                } else if(username.length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Input username",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("user status", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Invalid email",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        Log.d("user status", "signInWithEmail:onComplete:" + task.isSuccessful());

                                                        if (!task.isSuccessful()) {
                                                            Log.d("user status", "signInWithEmail:failed", task.getException());
                                                            Toast.makeText(RegistrationActivity.this, "Authentication failed",
                                                                    Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                    .setDisplayName(username)
                                                                    .build();
                                                            Log.d("username", username);
                                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                            user.updateProfile(profileUpdates)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()) {
                                                                                FirebaseAuth.getInstance().signOut();
                                                                                mAuth.signInWithEmailAndPassword(email, password)
                                                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                if (!task.isSuccessful()) {
                                                                                                    Log.d("user status", "signInWithEmail:failed", task.getException());
                                                                                                    Toast.makeText(RegistrationActivity.this, "Authentication failed",
                                                                                                            Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
