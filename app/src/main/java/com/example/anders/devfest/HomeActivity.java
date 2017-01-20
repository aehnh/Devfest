package com.example.anders.devfest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView tv_name;
    private TextView tv_email;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
        } else {
            tv_name.setText(user.getDisplayName());
            tv_email.setText(user.getEmail());
        }
    }

    public void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                drawerLayout.closeDrawers();
                Fragment fragment = null;

                switch (id){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.browse:
                        fragment = new BrowseFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this, AuthenticationActivity.class));
                }
                if (fragment != null) {
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });

        navigationView.setCheckedItem(R.id.home);

        View header = navigationView.getHeaderView(0);
        tv_name = (TextView)header.findViewById(R.id.tv_name);
        tv_email = (TextView)header.findViewById(R.id.tv_email);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
