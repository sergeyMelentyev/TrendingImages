package com.example.melentyev.sergey.trendingimages;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK }

class GetRawData extends AsyncTask<String, Void, String> {

    public static final String TAG = "GetRawData";

    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallBack;

    GetRawData(OnDownloadComplete mCallBack) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mCallBack = mCallBack;
    }

    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    @Override
    protected void onPostExecute(String data) {
        if (mCallBack != null)
            mCallBack.onDownloadComplete(data, mDownloadStatus);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (params == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //int responseCode = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line = reader.readLine()))
                result.append(line).append("\n");
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: " + e.getMessage());
        } catch(IOException e){
            Log.e(TAG, "doInBackground: IO exception: " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground security error: " + e.getMessage());
        } finally {
            if (connection != null && reader != null)
                try {
                    connection.disconnect();
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: " + e.getMessage());
                }
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
