package com.zak;

import android.content.Context;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Priority;
import com.downloader.Status;

public class CacheData implements OnDownloadListener {

    private final Context mContext;
    private int id;
    private OnSuccessListener mSuccessListener;
    private String path;
    private String fileName;

    public CacheData(Context context){
        mContext = context;
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(false)
                .build();
        PRDownloader.initialize(context, config);

    }

    public CacheData setOnSuccessListener(OnSuccessListener onSuccessedListener){
        mSuccessListener = onSuccessedListener;
        return this;
    }

    public void start(String url, String fileName){
        path = mContext.getCacheDir().getPath() + "/URLVideoView/";
        this.fileName = fileName;
        id = PRDownloader.download(url, path, fileName)
                .setPriority(Priority.LOW)
                .build()
                .start(this);
    }

    public boolean isLoading() {
        return PRDownloader.getStatus(id) == Status.RUNNING
                || PRDownloader.getStatus(id) == Status.QUEUED;
    }

    @Override
    public void onDownloadComplete() {
        if(mSuccessListener !=null){
            mSuccessListener.onSuccess(path + fileName);
        }
    }

    @Override
    public void onError(Error error) {

    }



    public interface OnSuccessListener {
        void onSuccess(String path);
    }
}
