package com.angle.hshb.servicedemo.http;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by angle
 * 2017/11/28.
 */

public class DownLoadTask extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS = 0;

    public static final int TYPE_FAILED= 1;

    public static final int TYPE_PAUSED = 2;

    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled =false;

    private boolean isPaused = false;

    private int lastProgress;

    public DownLoadTask(DownloadListener listener) {
        this.listener = listener;
    }


    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadlength = 0;//记录已经下载的文件长度
            String downloadurl = params[0];

            String fileName = downloadurl.substring(downloadurl.lastIndexOf("/"));
            String dirtory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
            file = new File(dirtory + fileName);

            if (file.exists()){
                downloadlength = file.length();
            }
            long contentlength = getContentLength(downloadurl);
            if (contentlength == 0){
                return TYPE_FAILED;
            }else if (contentlength == downloadlength){
                //已下载字节和文件总字节相等，说明文件下载完成了
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes="+downloadlength)
                    .url(downloadurl)
                    .build();

            Response response = client.newCall(request).execute();
            if (response != null){
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file,"rw");
                saveFile.seek(downloadlength);

                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                    }
                    saveFile.write(b,0,len);
                    int progress = (int) ((total + downloadlength) * 100 /contentlength);
                    publishProgress(progress);
                }
            }
            response.body().close();
            return TYPE_SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (is != null)
                    is.close();
                if (saveFile != null)
                    saveFile.close();
                if (isCanceled && file != null)
                    file.delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress =values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPause();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled =true;
    }

    private long getContentLength(String downloadurl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadurl)
                .build();
        Response response = client.newCall(request).execute();
        if (response !=null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }

        return 0;
    }
}
