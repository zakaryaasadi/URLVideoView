package com.zak;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devbrackets.android.exomedia.listener.OnBufferUpdateListener;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.listener.OnVideoSizeChangedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zak.R;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VideoCore implements OnCompletionListener, OnPreparedListener, OnErrorListener, OnVideoSizeChangedListener, OnBufferUpdateListener, CacheData.OnSuccessListener {
    private Context mContext;
    private Drawable mute, volume;
    private CacheData mCacheDate;
    private VideoView mVideoView;
    private TimerHandler mTimer;
    private Progressbar mProgressbar;
    private ImageView mSpeaker;
    private ImageView mRefresh;
    private SpinKitView mProgressSpinner;
    private URLVideoView mLayout;
    private String mUrl;
    private String mFileName;
    private OnCachedListener mCachedListener;
    private com.zak.OnCompletionListener mCompletion;
    private com.zak.OnErrorListener mError;

    public VideoCore(Context context){
        mContext = context;
        mute = mContext.getResources().getDrawable(R.drawable.ic_mute);
        volume = mContext.getResources().getDrawable(R.drawable.ic_sound);

        mCacheDate = new CacheData(context);
        mCacheDate.setOnSuccessListener(this);
    }

    public VideoCore setVideoView(VideoView videoView){
        mVideoView = videoView;
        mVideoView.setVolume(0);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnVideoSizedChangedListener(this);
        mVideoView.setOnBufferUpdateListener(this);
        return this;
    }


    public VideoCore setSpeaker(ImageView speaker){
        mSpeaker = speaker;
        mSpeaker.setImageDrawable(mute);
        mSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mVideoView.getVolume() == 0){
                    mVideoView.setVolume(100);
                    mSpeaker.setImageDrawable(volume);
                }else{
                    mVideoView.setVolume(0);
                    mSpeaker.setImageDrawable(mute);
                }
            }
        });
        return this;
    }


    public VideoCore setRefresh(ImageView refresh){
        mRefresh = refresh;
        mRefresh.setVisibility(GONE);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressSpinner();
                mRefresh.setVisibility(GONE);
                mVideoView.restart();
            }
        });
        return this;
    }

    public VideoCore setSpinner(SpinKitView spinner) {
        mProgressSpinner = spinner;
        mProgressSpinner.setVisibility(GONE);
        return this;
    }

    public VideoCore setProgressbar(Progressbar progressbar) {
        mProgressbar = progressbar;
        return this;
    }

    public VideoCore setOnCachedListener(OnCachedListener onCachedListener) {
        mCachedListener = onCachedListener;
        return this;
    }

    public VideoCore setOnCompletionListener(com.zak.OnCompletionListener onCompletion) {
        mCompletion = onCompletion;
        return this;
    }

    public VideoCore setOnErrorListener(com.zak.OnErrorListener onError) {
        mError = onError;
        return this;
    }


    public void setThumbnail(String url){
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mVideoView.setPreviewImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }


    public void setVideoPath(String url){
        showProgressSpinner();
        mFileName = getFileName(url);
        mUrl = url;

        if(getFile().exists()){
            mVideoView.setVideoPath(getFile().getPath());
        }else{
            mVideoView.setVideoPath(url);
        }
    }


    private void start(){
        mVideoView.start();
        mTimer = new TimerHandler(mVideoView, mProgressbar);
        mTimer.start();
    }


    public void stop(){
        dismissProgressSpinner();
        mVideoView.stopPlayback();
        mTimer.stop();
    }

    public void pause(){
        dismissProgressSpinner();
        mVideoView.pause();
        mTimer.pause();
    }

    public void resume(){
        dismissProgressSpinner();
        mVideoView.start();
        mTimer.resume();
    }

    public boolean isPause() {
        if(mTimer != null)
            return mTimer.isPause();
        return true;
    }

    public boolean isPlaying(){
        return mTimer.isStart();
    }


    @Override
    public void onPrepared() {
        dismissProgressSpinner();
        start();
    }

    @Override
    public void onCompletion() {
        showProgressSpinner();
        if(getFile().exists() && Uri.parse(getFile().getPath()) != mVideoView.getVideoUri()){
            mVideoView.setVideoPath(getFile().getPath());
        }else{
            mVideoView.restart();
        }

        if(mCompletion != null)
            mCompletion.onCompletion();
    }

    private void showProgressSpinner(){
        mProgressSpinner.setVisibility(VISIBLE);
    }

    private void dismissProgressSpinner(){
        mProgressSpinner.setVisibility(GONE);
    }

    @Override
    public boolean onError(Exception e) {
        dismissProgressSpinner();
        mRefresh.setVisibility(VISIBLE);
        if(mError != null)
            mError.onError(e);
        return false;
    }

    @Override
    public void onVideoSizeChanged(int intrinsicWidth, int intrinsicHeight, float pixelWidthHeightRatio) {

        int contentWidth = mLayout.getWidth();
        int contentHeight = mLayout.getHeight();

        float ratioHeightToWidth = intrinsicHeight / (float) intrinsicWidth;

        int newHeight = Math.round(ratioHeightToWidth *contentWidth);
        if(newHeight > contentHeight && ratioHeightToWidth > 1.0f)
            newHeight = contentHeight;

        ViewGroup.LayoutParams params = mLayout.getLayoutParams();
        params.height = newHeight;
        params.width = contentWidth;

        mLayout.setLayoutParams(params);
    }


    public VideoCore setLayout(URLVideoView layout) {
        mLayout = layout;
        return this;
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if(percent > 50 && !getFile().exists() && !mCacheDate.isLoading()){
            mCacheDate.start(mUrl, mFileName);
        }
    }


    private File getFile(){
        String path = mContext.getCacheDir().getPath() +"/URLVideoView/"+ mFileName;
        return new File(path);
    }

    private String getFileName(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onSuccess(String path) {
        if (mCachedListener != null){
            mCachedListener.onCached(path);
        }
    }
}
