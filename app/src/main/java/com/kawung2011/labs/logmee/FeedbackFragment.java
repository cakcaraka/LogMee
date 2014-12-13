package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    public static FeedbackFragment newInstance(Toolbar toolbar) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        toolbar.setTitle("Feedback");
        return fragment;
    }

    public FeedbackFragment() {
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
        final View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        setHasOptionsMenu(true);

        Button button = (Button) v.findViewById(R.id.email_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Create the Intent */
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "numan.naufal@ui.ac.id" });


                EditText emailContentEditText = (EditText) v.findViewById(R.id.editTextEmailContent);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "LogMee - FeedBack");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailContentEditText.getText().toString());

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
