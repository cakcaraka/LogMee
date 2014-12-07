package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;

import java.util.List;

/**
 * Created by Caraka Nur Azmi on 30/11/2014.
 */
public class ActAdapter extends RecyclerView.Adapter<ActAdapter.ActViewHolder> {

    private List<Activities> actList;
    private Activity act;
    private Context ctx;
    public ActAdapter(List<Activities> contactList,Activity act) {
        this.actList = contactList;
        this.act = act;
        this.ctx = act.getApplicationContext();
    }

    @Override
    public int getItemCount() {
        return actList.size();
    }

    @Override
    public void onBindViewHolder(ActViewHolder actViewHolder, int i) {
        final Activities activity = actList.get(i);
        actViewHolder.vName.setText(activity.get_name());
        actViewHolder.vDate.setText(activity.get_dateTime());
        actViewHolder.vTextDoc.setText(""+activity.get_count_logs_text());
        actViewHolder.vTextImage.setText(""+activity.get_count_logs_image());
        actViewHolder.vTextSound.setText(""+activity.get_count_logs_speech());
        Log.d("d",activity.toString());
        Bitmap bm = activity.getBitmap();
        if(bm != null){
            actViewHolder.vImage.setImageBitmap(bm);
        }else{
            int jj = activity.get_id() % 3;
            int drawable = 0;
            if(jj == 0){
                drawable = R.drawable.default_1;
            }else if(jj == 1){
                drawable = R.drawable.default_2;
            }else{
                drawable = R.drawable.default_3;
            }
            Drawable myDraw = act.getResources().getDrawable(drawable);
            actViewHolder.vImage.setImageDrawable(myDraw);
        }
        final int ii = i;
        actViewHolder.vView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ctx, ActViewActivity.class);
                Log.d("d", ii + "" + activity.get_id());
                intent.putExtra("_id", activity.get_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        actViewHolder.vView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                String set = "";
                if(activity.get_status().equals("0")){
                    set = "Set Done";
                }else{
                    set = "Set Ongoing";
                }
                final CharSequence[] options = { "Update",set ,"Delete", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setTitle("Action!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Update"))
                        {
                            Intent intent = new Intent(ctx, ActCreateActivity.class);
                            intent.putExtra("_id", activity.get_id());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);
                        }
                        else if (options[item].equals("Delete"))
                        {
                            DBHandler db = new DBHandler(ctx, null);
                            db.deleteActivity(activity);
                            Intent intent = new Intent(ctx, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ctx.startActivity(intent);
                        }else if (options[item].equals("Set Done"))
                        {
                            DBHandler db = new DBHandler(ctx, null);
                            activity.set_status("1");
                            db.updateActivity(activity);
                            Intent intent = new Intent(ctx, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ctx.startActivity(intent);
                        }else if (options[item].equals("Set Ongoing"))
                        {
                            DBHandler db = new DBHandler(ctx, null);
                            activity.set_status("0");
                            db.updateActivity(activity);
                            Intent intent = new Intent(ctx, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ctx.startActivity(intent);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public ActViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.act_card, viewGroup, false);
        return new ActViewHolder(itemView);
    }

    public static class ActViewHolder extends RecyclerView.ViewHolder  {
        protected TextView vName;
        protected TextView vTextDoc;
        protected TextView vTextImage;
        protected TextView vTextSound;

        protected ImageView vImage;
        protected TextView vDate;
        protected View vView;
        public ActViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vTextDoc = (TextView)  v.findViewById(R.id.textDocument);
            vTextImage = (TextView)  v.findViewById(R.id.textImage);
            vTextSound = (TextView)  v.findViewById(R.id.textSound);

            vImage = (ImageView) v.findViewById(R.id.imageAct);
            vDate = (TextView) v.findViewById(R.id.textDate);
            vView =v;
        }
    }
}
