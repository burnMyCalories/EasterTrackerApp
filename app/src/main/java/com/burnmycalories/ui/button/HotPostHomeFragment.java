package com.burnmycalories.ui.button;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burnmycalories.R;
import com.burnmycalories.adapter.PostAdapter;
import com.burnmycalories.base.BaseFragment;
import com.burnmycalories.model.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotPostHomeFragment extends BaseFragment {
    private List<Post> postList=new ArrayList<>();


    public HotPostHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_hot_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();


        //帖子UI处理
        initPosts();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.hot_posts);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        PostAdapter adapter=new PostAdapter(getActivity().getApplicationContext(),postList);
        recyclerView.setAdapter(adapter);

    }

    private void initPosts(){
        Post post1=new Post();
        post1.setTitle("Recommend Today");
        post1.setAuthor("Test Text");
        post1.setReplyNumber(200);
        post1.setLastReplyTime(getTime());
        postList.add(post1);


    }

    private String getTime(){
        Date date=new Date();

        String pattern="yy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);


        return simpleDateFormat.format(date);
    }
}
