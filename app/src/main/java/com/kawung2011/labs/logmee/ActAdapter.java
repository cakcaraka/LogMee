package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
        actViewHolder.vDescription.setText("Lorem Ipsum");
        Bitmap bm = activity.getBitmap();
        if(bm != null){
            actViewHolder.vImage.setImageBitmap(bm);
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
                final CharSequence[] options = { "Update", "Delete", "Set to Widget Access","Cancel" };
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
                        }
                        else if(options[item].equals("Set to Widget Access"))
                        {
                            DBHandler db = new DBHandler(ctx, null);
                            db.updateWidgetActivityId(activity.get_id());
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
        protected TextView vDescription;
        protected ImageView vImage;
        protected TextView vDate;
        protected View vView;
        public ActViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vDescription = (TextView)  v.findViewById(R.id.textDescription);
            vImage = (ImageView) v.findViewById(R.id.imageAct);
            vDate = (TextView) v.findViewById(R.id.textDate);
            vView =v;
        }
    }
}
