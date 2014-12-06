package com.kawung2011.labs.logmee;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView recList;
    private static String last_seen = "";
    String title = "Logmee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
        initDrawer();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ActAdminFragment.newInstance(this.last_seen,toolbar))
                    .commit();
        }

    }

    private void initDrawer() {
        //setup navigation drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.txt_open, R.string.txt_close) {
            private String lastTitle = "";
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(toolbar.getTitle().equals("Choose Action")){
                    toolbar.setTitle(lastTitle);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // when drawer open
                lastTitle = toolbar.getTitle().toString();
                toolbar.setTitle("Choose Action");
            }
        };

        // setDrawerlisterner
        drawerLayout.setDrawerListener(drawerToggle);

        LinearLayout set = (LinearLayout) findViewById(R.id.drawerSettings);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "abc", Toast.LENGTH_LONG).show();
                changeFragment(new PlaceholderFragment());
                drawerLayout.closeDrawers();
            }
        });
        ((LinearLayout) findViewById(R.id.viewAll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last_seen = ActAdminFragment.TYPE_ALL;
                changeFragment(ActAdminFragment.newInstance(ActAdminFragment.TYPE_ALL,toolbar));
                drawerLayout.closeDrawers();
            }
        });

        ((LinearLayout) findViewById(R.id.viewOngoing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last_seen = ActAdminFragment.TYPE_ONGOING;

                changeFragment(ActAdminFragment.newInstance(ActAdminFragment.TYPE_ONGOING,toolbar));
                drawerLayout.closeDrawers();
            }
        });

        ((LinearLayout) findViewById(R.id.viewDone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last_seen = ActAdminFragment.TYPE_DONE;

                changeFragment(ActAdminFragment.newInstance(ActAdminFragment.TYPE_DONE,toolbar));
                drawerLayout.closeDrawers();
            }
        });

    }

    private void changeFragment(Fragment fr){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fr)
                .commit();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
            return rootView;
        }
    }
}
