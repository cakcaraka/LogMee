package com.kawung2011.labs.logmee;

/**
 * Created by Caraka Nur Azmi on 14/11/2014.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;

import java.io.File;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActAdminFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ActAdminFragment newInstance(int sectionNumber) {
        ActAdminFragment fragment = new ActAdminFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ActAdminFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.act_admin_fragment, container, false);
        final Activity ctx = this.getActivity();

        Button button = (Button) rootView.findViewById(R.id.btnAddActivity);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ctx, ActCreateActivity.class);
                //Intent i = new Intent(ctx, TestAudioRecord.class);
                startActivity(i);
            }
        });
        displayListView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // ((MainActivity) activity).onSectionAttached(
       //         getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.listViewActivities) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("judul menu");
            menu.add(Menu.NONE, 0, 0, "update");
            menu.add(Menu.NONE, 1, 1, "delete");
        }
    }

    private void displayListView(final View rootView) {
/*            DBHandler db = new DBHandler(this.getActivity(),null);
            Cursor cursor = db.fetchAllActivitiesInMain();
            // The desired columns to be bound
            String[] columns = new String[] {
                    DBHandler.getaId(),
                    DBHandler.getaName(),
                    DBHandler.getaStatus(),
                    DBHandler.getaImg(),
                    DBHandler.getaDateTime(),
                    DBHandler.getaCountText(),
                    DBHandler.getaCountImage(),
                    DBHandler.getaCountSpeech()
            };

            // the XML defined views which the data will be bound to
            int[] to = new int[] {
                    R.id.textViewAId,
                    R.id.textViewAName,
                    R.id.textViewAStatus,
                    R.id.textViewAImage,
                    R.id.textViewADateTime,
                    R.id.textViewACText,
                    R.id.textViewACImage,
                    R.id.textViewACSpeech
            };

            // create the adapter using the cursor pointing to the desired data
            //as well as the layout information
            SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
                    this.getActivity(), R.layout.act_info,
                    cursor,
                    columns,
                    to,
                    0);
            ListView listView = (ListView) rootView.findViewById(R.id.listViewActivities);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);

            final View onClickView = rootView;
            Activity ctx = getActivity();
            final Intent intent = new Intent(ctx, ActViewActivity.class);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                    // Get the state's capital from this row in the database.
                    String idActivity = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                    intent.putExtra("_id", idActivity);
                    startActivity(intent);
                }
            });

            /*listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    if(v.getId() == R.id.listViewActivities) {
                        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
                        menu.setHeaderTitle("judul menu");
                        menu.add(Menu.NONE, 0, 0, "update");
                        menu.add(Menu.NONE, 1, 1, "delete");
                    }
                }

                @Override
                public boolean onContextItemSelected(MenuItem item) {
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                    int menuItemIndex = item.getItemId();
                    TextView text = (TextView) rootView.findViewById(R.id.textViewSelected);
                    text.setText(menuItemIndex);
                    return true;
                }


            });*/
    }
}

/* TRASH CODE
*
   DBHandler db = new DBHandler(this.getActivity(), null);
   Activities actitvity = db.findActivityByName("Tekmob");
   actitvity.set_name("Teknologi Mobil Balap");
   db.updateActivity(actitvity);
   Logs log = db.findLog(6);
   log.set_text("guerilla testing");
   db.updateLog(log);
*
*
* */