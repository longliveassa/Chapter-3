package com.example.chapter3.homework.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.airbnb.lottie.LottieAnimationView;
import com.example.chapter3.homework.R;

public class FriendListFragment extends Fragment {

    private LottieAnimationView animationView;

    public FriendListFragment() {
        // Required empty public constructor
    }

    public static FriendListFragment newInstance() {
        return new FriendListFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);



        animationView = view.findViewById(R.id.animation_view);
        showLoadingAnimation();
        return view;
    }

    private void showLoadingAnimation() {


        animationView.setVisibility(View.VISIBLE);
        animationView.setAlpha(0f); // 初始设置为完全透明
        animationView.playAnimation();

        // 淡入动画
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(animationView, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(1000); // 设置动画持续时间为 1 秒
        fadeInAnimator.setInterpolator(new DecelerateInterpolator()); // 设置减速插值器
        fadeInAnimator.start(); // 启动淡入动画

        // 模拟加载数据，5s 后隐藏动画
        new Handler().postDelayed(() -> {
            // 淡出动画
            ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(animationView, "alpha", 1f, 0f);
            fadeOutAnimator.setDuration(1000); // 设置动画持续时间为 1 秒
            fadeOutAnimator.setInterpolator(new AccelerateInterpolator()); // 设置加速插值器
            fadeOutAnimator.start(); // 启动淡出动画

            // 设置动画结束后的回调
            fadeOutAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animationView.setVisibility(View.GONE); // 隐藏动画视图
                    // 设置动画结束后的回调
                    // TODO: 加载实际的好友列表数据
                    fillListViewWithData();

                        }




            });
        }, 5000);
    }

    private void fillListViewWithData() {
        ListView listView = getView().findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        // 填充列表数据
        adapter.add("Friend 1");
        adapter.add("Friend 2");
        // 继续添加其他好友数据
        listView.setAdapter(adapter);

        // 实现 ListView 的渐变显示效果
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(listView, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(1000); // 设置渐变动画的持续时间
        fadeInAnimator.start();
    }
}
