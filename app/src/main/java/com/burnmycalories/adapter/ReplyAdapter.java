package com.burnmycalories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burnmycalories.R;
import com.burnmycalories.model.Article;
import com.burnmycalories.model.Reply;
import com.burnmycalories.ui.activities.ArticleActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    public Context context;
    private List<Reply> replyList;

    public ReplyAdapter(Context context, List<Reply> replies) {
        replyList=replies;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reply_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
//        holder.replyView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position =holder.getAdapterPosition();
//                Reply reply=replyList.get(position);
//
//                int articleId=1;
//
//
//                Toast.makeText(v.getContext(),"你点击了"+article.getTitle(),Toast.LENGTH_SHORT).show();
//
//                ArticleActivity.actionStart(v.getContext(),articleId);
//            }
//        });

        //
        holder.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo  点击图片相应示例
                int position =holder.getAdapterPosition();
                Reply reply=replyList.get(position);
                Toast.makeText(v.getContext(),"Picture: "+reply.getAuthor()+" has been clicked",Toast.LENGTH_SHORT).show();
            }
        });

        //
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reply reply=replyList.get(position);


        Glide.with(context)
                .load(reply.getHeadImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.mytest).error(R.drawable.mytest).dontAnimate().centerCrop())
                .into(holder.headImage);

                holder.author.setText(reply.getAuthor());
                holder.content.setText(reply.getContent());
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        View replyView;
        ImageView headImage;
        TextView author;
        TextView content;
//        TextView recommendPoint;

        public ViewHolder(View view){
            super(view);
            replyView=view;
            headImage=(CircleImageView)view.findViewById(R.id.circle_image_view_head);
            author=(TextView)view.findViewById(R.id.author);
            content=(TextView)view.findViewById(R.id.content);;
        }
    }




}
