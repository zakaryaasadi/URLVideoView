package com.zak.urlvideoview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zak.video.OnCachedListener;
import com.zak.video.OnCompletionListener;
import com.zak.video.OnErrorListener;
import com.zak.video.URLVideoView;

public class MainActivity extends AppCompatActivity {

    private URLVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video);
        videoView
                .setOnCachedListener(new OnCachedListener() {
                    @Override
                    public void onCached(String path) {
                        int x =0;
                    }
                })
                .setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion() {
                        int x = 0;
                    }
                })
                .setOnErrorListener(new OnErrorListener() {
                    @Override
                    public void onError(Exception e) {
                        int x= 0;
                    }
                })
                .start("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4");
    }



    @Override
    protected void onStop() {
        if(!videoView.isPause()){
            videoView.pause();
        }
        super.onStop();
    }

    @Override
    protected void onRestart() {
        if(videoView != null){
            videoView.resume();
        }
        super.onRestart();
    }
}
