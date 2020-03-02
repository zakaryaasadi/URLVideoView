package com.zak.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.MultiplePulse;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.PulseRing;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;

public class URLVideoView extends RelativeLayout {
    private VideoView mVideoView;
    private Progressbar mProgressbar;
    private int mHeightProgress;
    private ImageView mSpeaker;
    private Style mStyle;
    private int mColor;
    private SpinKitView mProgressSpinner;
    private ImageView mRefresh;
    private VideoCore mVideoCore;

    public URLVideoView(Context context) {
        super(context);
    }


    @SuppressLint("ResourceAsColor")
    public URLVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.URLVideo);

        mHeightProgress = a.getDimensionPixelSize(R.styleable.URLVideo_progressbar_height, 2);
        mStyle = Style.values()[a.getInt(R.styleable.URLVideo_SpinnerStyle, 9)];
        mColor = a.getColor(R.styleable.URLVideo_SpinnerColor, R.color.colorAccent);

        createVideoView();
        createProgressbar();
        createProgressSpinner();
        createSpeaker();
        createRefreshButton();
        a.recycle();

        spinnerStyle();
        init();
    }

    private void createProgressSpinner() {
        LayoutParams params =
                new LayoutParams(dip2px(50f), dip2px(50f));
        params.addRule(CENTER_IN_PARENT);
        mProgressSpinner = new SpinKitView(getContext());
        mProgressSpinner.setLayoutParams(params);
        addView(mProgressSpinner);

    }

    private void createVideoView() {
        setBackgroundColor(getResources().getColor(R.color.colorBackground));
        mVideoView = new VideoView(getContext());
        LayoutParams params = new LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        mVideoView.setLayoutParams(params);

        addView(mVideoView);

    }

    private void createProgressbar() {
        LinearLayout linearLayout = new LinearLayout(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setWeightSum(100);

        mProgressbar = new Progressbar(getContext(), mHeightProgress, mColor);
        linearLayout.addView(mProgressbar.getView());

        addView(linearLayout);
    }

    private void createSpeaker() {
        mSpeaker = new ImageView(getContext());

        LayoutParams params =
                new LayoutParams(dip2px(60), dip2px(60));
        params.addRule(ALIGN_PARENT_BOTTOM);
        mSpeaker.setLayoutParams(params);
        mSpeaker.setPadding(dip2px(20), dip2px(20), dip2px(20), dip2px(20));


        addView(mSpeaker);
    }

    private void createRefreshButton() {

        mRefresh = new ImageView(getContext());
        mRefresh.setImageDrawable(getResources().getDrawable(R.drawable.ic_refresh));
        LayoutParams params =
                new LayoutParams(dip2px(40f), dip2px(40f));
        params.addRule(CENTER_IN_PARENT);
        mRefresh.setLayoutParams(params);

        addView(mRefresh);
    }

    private void spinnerStyle() {

        mProgressSpinner.setColor(mColor);
        Sprite style = new Circle();
        switch (mStyle) {
            case WAVE:
                style = new Wave();
                break;
            case PULSE:
                style = new Pulse();
                break;
            case CHASING_DOTS:
                style = new ChasingDots();
                break;
            case CIRCLE:
                style = new Circle();
                break;
            case CUBE_GRID:
                style = new CubeGrid();
                break;
            case DOUBLE_BOUNCE:
                style = new DoubleBounce();
                break;
            case FADING_CIRCLE:
                style = new FadingCircle();
                break;
            case FOLDING_CUBE:
                style = new FoldingCube();
                break;
            case MULTIPLE_PULSE:
                style = new MultiplePulse();
                break;
            case MULTIPLE_PULSE_RING:
                style = new MultiplePulseRing();
                break;
            case PULSE_RING:
                style = new PulseRing();
                break;
            case ROTATING_CIRCLE:
                style = new RotatingCircle();
                break;
            case ROTATING_PLANE:
                style = new RotatingPlane();
                break;
            case THREE_BOUNCE:
                style = new ThreeBounce();
                break;
            case WANDERING_CUBES:
                style = new WanderingCubes();
                break;
        }
        mProgressSpinner.setIndeterminateDrawable(style);
    }

    private void init() {
        mVideoCore = new VideoCore(getContext())
                .setLayout(this)
                .setRefresh(mRefresh)
                .setVideoView(mVideoView)
                .setSpeaker(mSpeaker)
                .setSpinner(mProgressSpinner)
                .setProgressbar(mProgressbar);
    }

    public void start(String url) {
        mVideoCore.setVideoPath(url);

    }


    public void pause() {
        mVideoCore.pause();
    }

    public void resume() {
        mVideoCore.resume();
    }

    public void stop(){
        mVideoCore.stop();
    }

    public boolean isPlaying() {
        return mVideoCore.isPlaying();
    }

    public void setThumbnail(String url){
        mVideoCore.setThumbnail(url);
    }

    public URLVideoView setOnErrorListener(OnErrorListener onErrorListener){
        mVideoCore.setOnErrorListener(onErrorListener);
        return this;
    }

    public URLVideoView setOnCompletionListener(OnCompletionListener onCompletionListener){
        mVideoCore.setOnCompletionListener(onCompletionListener);
        return this;
    }

    public URLVideoView setOnCachedListener(OnCachedListener onCachedListener){
        mVideoCore.setOnCachedListener(onCachedListener);
        return this;
    }



    private int dip2px(float dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        return (int) px;
    }

}

