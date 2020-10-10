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
import com.burnmycalories.model.MediaFile;
import com.burnmycalories.model.Post;

import java.util.List;

public class MediaFileAdapter extends RecyclerView.Adapter<MediaFileAdapter.ViewHolder> {

    public Context context;
    private List<MediaFile> mediaFileList;

    public MediaFileAdapter(Context context, List<MediaFile> mediaFiles) {
        mediaFileList=mediaFiles;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_file_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.mediaFileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =holder.getAdapterPosition();
                MediaFile mediaFile=mediaFileList.get(position);
                Toast.makeText(v.getContext(),mediaFile.getMediaFileName()+" has been clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaFile mediaFile=mediaFileList.get(position);

        holder.mediaFileName.setText(mediaFile.getMediaFileName());

    }

    @Override
    public int getItemCount() {
        return mediaFileList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mediaFileView;
        TextView mediaFileName;

        public ViewHolder(View view){
            super(view);
            mediaFileView=view;
            mediaFileName=(TextView) view.findViewById(R.id.media_file_name);
        }
    }
}
