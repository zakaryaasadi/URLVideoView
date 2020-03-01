package com.zak.video;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class Progressbar {

    private View mProgressbar;
    private LinearLayout.LayoutParams params;
    private Context mContext;
    private int mWidth, mHeight;

    public Progressbar(Context context, int mHeightProgress, int color){
        mContext = context;
        mProgressbar = new View(context);
        mHeight = mHeightProgress;
        params = new LinearLayout.LayoutParams(0, mHeight,0);
        mProgressbar.setLayoutParams(params);
        mProgressbar.setBackgroundColor(color);
    }

    public View getView(){
        return mProgressbar;
    }

    public void setProgress(int limit){

        if(limit < 0)
            limit = 0;

        mWidth = limit;
        params = new LinearLayout.LayoutParams(0, mHeight,limit);
        mProgressbar.setLayoutParams(params);
    }

    public  int getProgress(){
        return mWidth;
    }


    public void resetProgressbar(){
        setProgress(-1);
    }



}
