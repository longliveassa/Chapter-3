# Chapter-3
新加文件：

![image](https://github.com/longliveassa/Chapter-3/assets/117608033/9f3b1668-07b2-4b26-bc37-e8291ed10f60)

实现效果：

![QQ录屏20240511183726](https://github.com/longliveassa/Chapter-3/assets/117608033/544bfa8e-1f55-4e80-8e55-42e970dedc70)

更改：
 implementation 'androidx.viewpager2:viewpager2:1.0.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'com.airbnb.android:lottie:3.4.0'



import androidx.appcompat.app.AppCompatActivity;

Ch3Ex3Activity.java:

//        获取视图的id以便操作
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

//        adapter 数据容器绑定器
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
3.xml:

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.google.android.material.tabs.TabLayout
            android:id="@+id/tab_layout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tabGravity="fill"
            app:tabMode="fixed" />

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/view_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior" />

新建MyPagerAdapter.java:

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

新建FriendListFragment.java:

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

xml:
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.airbnb.lottie.LottieAnimationView
        android:id="@+id/animation_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        app:lottie_autoPlay="true"
        app:lottie_loop="true"
        app:lottie_rawRes="@raw/material_wave_loading" />

    <ListView
        android:id="@+id/list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"

        android:visibility="visible"
        tools:visibility="visible" />


</FrameLayout>

新建 MyFriendListFragment.java:
随意，略

xml:
略
