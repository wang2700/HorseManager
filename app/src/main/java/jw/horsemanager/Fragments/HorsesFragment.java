package jw.horsemanager.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import jw.horsemanager.Activities.AddHorse;
import jw.horsemanager.R;

/**
 * Created by jerry on 8/13/2017.
 */

public class HorsesFragment extends Fragment {

    private static final String TAG = "HorsesFragment";

    private Toolbar mToolBar;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.horses_fragment, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_horses_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_horse_menu_item){
            Intent addHorseIntent = new Intent(getContext(), AddHorse.class);
            addHorseIntent.putExtra("mode", AddHorse.ADD);
            startActivity(addHorseIntent);
            Log.i(TAG, "Start Activity: Add Horse");
        }
        return super.onOptionsItemSelected(item);
    }
}
