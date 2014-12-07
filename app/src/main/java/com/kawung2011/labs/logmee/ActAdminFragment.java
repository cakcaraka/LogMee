package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.lib.FloatingActionButton;

import java.util.List;

public class ActAdminFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TYPE = "type";
    public static final String TYPE_ALL = "";
    public static final String TYPE_ONGOING = "0";
    public static final String TYPE_DONE = "1";

    // TODO: Rename and change types of parameters
    private RecyclerView recList;
    private FloatingActionButton fabButton;
    private static String query = "";
   public static ActAdminFragment newInstance(String param1,Toolbar toolbar) {
        ActAdminFragment fragment = new ActAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, param1);
        fragment.setArguments(args);
        if(toolbar != null){
            String title = "";
            if(param1.equals(TYPE_ALL)){
                title = "LogMee - All";
            }else if(param1.equals(TYPE_ONGOING)){
                title = "LogMee - Ongoing";
            }else if(param1.equals(TYPE_DONE)){
                title = "LogMee - Finished";
            }
            toolbar.setTitle(title);
        }

        return fragment;
    }

    public ActAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach(){
        super.onDetach();
        if(fabButton != null) {
            fabButton.setVisibility(View.INVISIBLE);
        }

    }
    @Override
    public void onAttach(Activity act){
        super.onAttach(act);
        if(fabButton == null && !getArguments().getString(ARG_TYPE).equals("1")) {
            fabButton = new FloatingActionButton.Builder(getActivity())
                    .withDrawable(getResources().getDrawable(R.drawable.float_add))
                    .withButtonColor(Color.rgb(233, 30, 99))
                    .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                    .withButtonSize(64)
                    .withMargins(0, 16, 16, 0)
                    .create();

            fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity().getApplicationContext(), ActCreateActivity.class);
                    startActivity(i);
                }
            });
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        updateList(query,getArguments().getString(ARG_TYPE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.act_admin_fragment, container, false);


        recList = (RecyclerView) v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        updateList("",getArguments().getString(ARG_TYPE));

        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        sv.setQueryHint("search..");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String query) {
                ActAdminFragment.query = query;
                updateList(query,getArguments().getString(ARG_TYPE));
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void updateList(String query,String status)
    {
        DBHandler db = new DBHandler(getActivity().getApplicationContext(),null);

        List<Activities> acts;
        if(query.equals("")) {
                acts =db.fetchAllActivities(status);

        }else{
            acts = db.findActivityByName(query, status);
        }
        ActAdapter actAdapter = new ActAdapter(acts,getActivity());
        recList.setAdapter(actAdapter);

    }
}
