package com.burnmycalories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burnmycalories.R;
import com.burnmycalories.model.StreamMedia;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    public Context context;
    private List<StreamMedia> streamMediaList;

    public MediaAdapter(Context context, List<StreamMedia> streamMedia) {
        streamMediaList = streamMedia;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.mediaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =holder.getAdapterPosition();
                 StreamMedia streamMedia = streamMediaList.get(position);
                Toast.makeText(v.getContext(),"你点击了"+ streamMedia.getMediaName(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StreamMedia streamMedia = streamMediaList.get(position);

        holder.mediaName.setText(streamMedia.getMediaName());
        holder.mediaHotPoint.setText(String.valueOf(streamMedia.getMediaHotPoint()));


    }

    @Override
    public int getItemCount() {
        return streamMediaList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mediaView;
        TextView mediaName;
        TextView mediaHotPoint;

        public ViewHolder(View view){
            super(view);
            mediaView=view;
            mediaName=view.findViewById(R.id.media_name);
            mediaHotPoint=view.findViewById(R.id.media_hot_point);

        }
    }
}
