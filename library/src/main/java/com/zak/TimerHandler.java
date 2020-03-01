package com.zak;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.devbrackets.android.exomedia.ui.widget.VideoView;

public class TimerHandler extends AsyncTask<Void, Integer, Void> {
    private final VideoView mVideoView;
    private long duration = 0;
    private long current = 0;
    private Progressbar mProgressBar;
    private boolean isStop, isPause = false;
    private boolean isStart = false;

    public TimerHandler(VideoView videoView, Progressbar progressBar) {
        mVideoView = videoView;
        mProgressBar = progressBar;
    }

    @SuppressLint("WrongThread")
    @Override
    protected Void doInBackground(Void... params) {
        duration = mVideoView.getDuration();
        while (true){
            if(isPause)
                continue;
            if(isStop)
                break;

            try {
                current = mVideoView.getCurrentPosition();
                publishProgress((int) (current * 100 / duration));
                Thread.sleep(100);
            } catch (Exception e) { }
        }
        return null;
    }

    public void start(){
        isStart = true;
        isPause = isStop = false;
        execute();

    }

    public void stop(){
        isStop = true;
        isPause = false;
        mProgressBar.resetProgressbar();
    }

    public void pause(){
        isPause = true;
        isStop = false;
    }

    public void resume(){
        isStop = isPause = false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
    }





    public boolean isPause() {
        return isPause;
    }

    public boolean isStart() {
        return isStart;
    }
}
