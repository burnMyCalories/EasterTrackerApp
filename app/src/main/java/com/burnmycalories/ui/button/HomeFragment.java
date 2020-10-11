package com.burnmycalories.ui.button;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.burnmycalories.R;
import com.burnmycalories.adapter.FmPagerAdapter;
import com.burnmycalories.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FmPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    private String[] titles = new String[]{"最新","热门","我的"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //todo  ToolBar 切换显示
//        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
//        Toolbar toolbar=(Toolbar) appCompatActivity.findViewById(R.id.toolbar_home);
//        appCompatActivity.setSupportActionBar(toolbar);
        super.onActivityCreated(savedInstanceState);


        initTab();
    }


    //初始化Tap顶部
    private void initTab() {

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);
        viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);

        String[] tabNames={"Hot Articles"};


        // 添加 tab item
        fragments.add(new com.burnmycalories.ui.button.RecommendHomeFragment());
        tabLayout.addTab(tabLayout.newTab());

//        fragments.add(new com.burnmycalories.ui.button.HotPostHomeFragment());
//        tabLayout.addTab(tabLayout.newTab());

//        fragments.add(new com.burnmycalories.ui.button.HotDanmakuHomeFragment());
//        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setupWithViewPager(viewPager,false);
        pagerAdapter = new FmPagerAdapter(getActivity().getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);


        //只有后添加Tab名才不显示空白
        for(int i=0;i<tabNames.length;i++){
            tabLayout.getTabAt(i).setText(tabNames[i]);
        }


    }


}
