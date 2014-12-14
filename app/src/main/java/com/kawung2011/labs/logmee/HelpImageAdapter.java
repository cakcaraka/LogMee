package com.kawung2011.labs.logmee;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by numan on 14/12/2014.
 */
public class HelpImageAdapter extends BaseAdapter
{
    private Context mContext;
    private Display display;


    private Integer[] mImageIds = {
            R.drawable.suzybae,
            R.drawable.suzy,
            R.drawable.suzybae,
            R.drawable.suzybae,
            R.drawable.suzybae,
            R.drawable.suzybae,
            R.drawable.suzybae,
            R.drawable.suzybae

    };

    public HelpImageAdapter(Context context, Display display)
    {
        mContext = context;
        this.display = display;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[index]);

        int width = display.getWidth();
        int height = display.getHeight();
        i.setLayoutParams(new Gallery.LayoutParams(width, height));

        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}