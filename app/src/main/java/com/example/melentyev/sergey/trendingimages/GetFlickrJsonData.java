package com.example.melentyev.sergey.trendingimages;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadComplete {

    private List<Photo> mPhotoList;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;
    private boolean runningInSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> photos, DownloadStatus status);
    }

    public GetFlickrJsonData(String mBaseUrl, OnDataAvailable mCallBack, String mLanguage, boolean mMatchAll) {
        this.mBaseUrl = mBaseUrl;
        this.mCallBack = mCallBack;
        this.mLanguage = mLanguage;
        this.mMatchAll = mMatchAll;
    }

    void executeOnSameThread(String searchCriteria) {
        runningInSameThread = true;
        String destinationUri = createUri(searchCriteria, mLanguage, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
    }

    @Override
    protected void onPostExecute(List<Photo> list) {
        if (mCallBack != null)
            mCallBack.onDataAvailable(list, DownloadStatus.OK);
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        String destinationUri = createUri(params[0], mLanguage, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return mPhotoList;
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll) {
        return Uri.parse(mBaseUrl).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1").build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPhoto = jsonArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");
                    String link = photoUrl.replaceFirst("_m.", "_h.");
                    Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                    mPhotoList.add(photoObject);
                }
            } catch (JSONException e) {
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if (runningInSameThread && mCallBack != null)
            mCallBack.onDataAvailable(mPhotoList, status);
    }
}
