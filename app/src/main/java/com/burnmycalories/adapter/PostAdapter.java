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
import com.burnmycalories.model.Post;
import com.burnmycalories.ui.activities.PostActivity;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context context;
    private List<Post> postList;

    public PostAdapter(Context context,List<Post> posts) {
        postList=posts;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =holder.getAdapterPosition();
                Post post=postList.get(position);
                Toast.makeText(v.getContext(),post.getTitle()+" has been clicked",Toast.LENGTH_SHORT).show();

                PostActivity.actionStart(v.getContext(),1);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=postList.get(position);

        holder.postTitle.setText(post.getTitle());
        holder.postAuthor.setText(post.getAuthor());
        holder.replyNumber.setText(String.valueOf(post.getReplyNumber()));
        holder.lastReplyTime.setText(post.getLastReplyTime());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View postView;
        TextView postTitle;
        TextView postAuthor;
        TextView replyNumber;
        TextView lastReplyTime;

        public ViewHolder(View view){
            super(view);
            postView=view;
            postTitle=(TextView) view.findViewById(R.id.post_title);
            postAuthor=(TextView) view.findViewById(R.id.post_author_text);
            replyNumber=(TextView)view.findViewById(R.id.post_reply_number);
            lastReplyTime=(TextView)view.findViewById(R.id.last_reply_time);

        }
    }
}
