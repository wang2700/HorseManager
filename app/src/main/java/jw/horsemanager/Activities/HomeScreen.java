package jw.horsemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import jw.horsemanager.Fragments.HomeFragment;
import jw.horsemanager.Fragments.HorsesFragment;
import jw.horsemanager.Misc.SystemSetting;
import jw.horsemanager.Objects.FeedingSchedule;
import jw.horsemanager.Objects.Horse;
import jw.horsemanager.R;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeScreen";

    private FragmentManager fragmentManager;
    private static ArrayList<Horse> horseArrayList;
    private static String horseListFilePath;

    public static String getFeedingScheduleFilePath() {
        return feedingScheduleFilePath;
    }

    private static String feedingScheduleFilePath;

    public static ArrayList getFeedingScheduleList() {
        return feedingScheduleList;
    }

    private static ArrayList feedingScheduleList;

    public static ArrayList<Horse> getHorseArrayList() {
        return horseArrayList;
    }

    public static String getHorseListFilePath() {
        return horseListFilePath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

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
                FileOutputStream fos = new FileOutputStream(horseListFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(horseArrayList);
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //load feeding schedule list
        feedingScheduleFilePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/FeedingScheduleList";
        Log.i(TAG,"checking feeding schedule list file from:" + feedingScheduleFilePath);
        File feedingScheduleFile = new File(feedingScheduleFilePath);
        if (feedingScheduleFile.exists()){
            try{
                Log.i(TAG, "Feeding Schedule list file exists");
                FileInputStream feedingListFis = new FileInputStream(feedingScheduleFile);
                ObjectInputStream feedingListOis = new ObjectInputStream(feedingListFis);
                feedingScheduleList = (ArrayList) feedingListOis.readObject();
                feedingListFis.close();
                feedingListOis.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Feeding Schedule List file does not exists");
            try {
                feedingScheduleFile.createNewFile();
                feedingScheduleList = new ArrayList<FeedingSchedule>();
                FileOutputStream fos = new FileOutputStream(feedingScheduleFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(feedingScheduleList);
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //check system setting
        SystemSetting.setIs24HourFormat(DateFormat.is24HourFormat(getApplicationContext()));
        SystemSetting.setDateFormat(DateFormat.getDateFormat(getApplicationContext()));
        SystemSetting.setTimeFormat(DateFormat.getTimeFormat(getApplicationContext()));

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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
