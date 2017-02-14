package com.example.melentyev.sergey.trendingimages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private Context mContext;
    private List<Photo> mPhotosList;

    FlickrRecyclerViewAdapter(Context mContext, List<Photo> mPhotosList) {
        this.mContext = mContext;
        this.mPhotosList = mPhotosList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate xml layout into view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return ((mPhotosList != null) && (mPhotosList.size() != 0) ? mPhotosList.size() : 0);
    }

    Photo getPhoto(int pos) {
        return ((mPhotosList != null) && (mPhotosList.size() != 0) ? mPhotosList.get(pos) : null);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        // called by the layout manager when it wants new data in an existing row
        Photo photoItem = mPhotosList.get(position);
        Picasso.with(mContext).load(photoItem.getmImage()).error(R.drawable.place_holder)
                .placeholder(R.drawable.place_holder).into(holder.thumbnail);
        holder.title.setText(photoItem.getmTitle());
    }

    void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    // describe each view holder, bind each field with items id from xml
    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail = null;
        TextView title = null;

        FlickrImageViewHolder(View itemView) {
            super(itemView);
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
