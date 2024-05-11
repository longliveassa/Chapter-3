package com.example.chapter3.homework;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter3.homework.fragment.FriendListFragment;
import com.example.chapter3.homework.fragment.MyFriendListFragment;


public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                好友列表
                return FriendListFragment.newInstance();
            case 1:
//                我的好友
                return MyFriendListFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2; // 假设有 3 个页面
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "好友列表";
            case 1:
                return "我的好友";

            default:
                return null;
        }

    }
}
