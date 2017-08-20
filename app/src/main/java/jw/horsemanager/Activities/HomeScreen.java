package jw.horsemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import jw.horsemanager.Fragments.HomeFragment;
import jw.horsemanager.Fragments.HorsesFragment;
import jw.horsemanager.Objects.Horse;
import jw.horsemanager.R;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeScreen";

    private FragmentManager fragmentManager;
    public static ArrayList<Horse> horseArrayList;
    public static String horseListFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_horse_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addHorses = new Intent(HomeScreen.this, AddHorse.class);
                addHorses.putExtra("mode", AddHorse.ADD);
                startActivity(addHorses);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //load horse lists
        horseListFilePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/HorseList";
        Log.i(TAG, "checking horse list file from: " + horseListFilePath);
        File horseListFile = new File(horseListFilePath);
        if (horseListFile.exists()){
            try {
                Log.i(TAG, "horse list file exists!");
                FileInputStream horseListFis = new FileInputStream(horseListFile);
                ObjectInputStream horseListOis = new ObjectInputStream(horseListFis);
                horseArrayList = (ArrayList<Horse>) horseListOis.readObject();
                horseListFis.close();
                horseListOis.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "horse list file does not exists!");
            try {
                horseListFile.createNewFile();
                horseArrayList = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.home_frame_layout, homeFragment);
            // Handle the camera action
        } else if (id == R.id.nav_horses) {
            HorsesFragment horsesFragment = new HorsesFragment();
            fragmentTransaction.replace(R.id.home_frame_layout, horsesFragment);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
