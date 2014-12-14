package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

public class HelpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private static Context _context;
    private static Display _display;

    public static HelpFragment newInstance(Toolbar toolbar, Context context, Display display) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        toolbar.setTitle("Help");
        _context = context;
        _display = display;
        return fragment;
    }

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

    @Override
    public void onAttach(Activity act){
        super.onAttach(act);

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        setHasOptionsMenu(true);
        Gallery gallery = (Gallery) v.findViewById(R.id.help_gallery);
        gallery.setSpacing(1);
        gallery.setAdapter(new HelpImageAdapter(_context, _display));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
