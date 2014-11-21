package com.kawung2011.labs.logmee;

/**
 * Created by Caraka Nur Azmi on 14/11/2014.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;

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

        Button button2 = (Button) rootView.findViewById(R.id.btnViewActivity);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ctx, ActViewActivity.class);
                //Intent i = new Intent(ctx, TestAudioRecord.class);

                startActivity(i);
            }
        });
        DBHandler db = new DBHandler(this.getActivity(), null);
        List<Activities> list = db.getAllActivities();
        String activities = "";
        for(int i=0; i < list.size(); i++) {
            activities += list.get(i).toString() +"\n";
        }
        if(activities.equals("")) activities = "belum ada activity";
        TextView text = (TextView) rootView.findViewById(R.id.activities);
        text.setText(activities);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


}