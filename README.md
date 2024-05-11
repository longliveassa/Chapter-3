# Chapter-3
新加文件：

![image](https://github.com/longliveassa/Chapter-3/assets/117608033/9f3b1668-07b2-4b26-bc37-e8291ed10f60)

作业一实现效果：

![QQ录屏20240511183726](https://github.com/longliveassa/Chapter-3/assets/117608033/544bfa8e-1f55-4e80-8e55-42e970dedc70)

更改：

build.gradle：

 implementation 'androidx.viewpager2:viewpager2:1.0.0'
 implementation 'com.google.android.material:material:1.4.0'
 implementation 'com.airbnb.android:lottie:3.4.0'
 
gradle.properties：

android.useAndroidX=true
android.enableJetifier=true

Ch3Ex3Activity.java:


        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        
xml:

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

新建：

MyPagerAdapter.java:

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FriendListFragment.newInstance();
            case 1:
                return MyFriendListFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2; 
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

FriendListFragment.java:

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
        animationView.setAlpha(0f); 
        animationView.playAnimation();


        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(animationView, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(1000); 
        fadeInAnimator.setInterpolator(new DecelerateInterpolator()); 
        fadeInAnimator.start();


        new Handler().postDelayed(() -> {

            ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(animationView, "alpha", 1f, 0f);
            fadeOutAnimator.setDuration(1000);
            fadeOutAnimator.setInterpolator(new AccelerateInterpolator());
            fadeOutAnimator.start();


            fadeOutAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animationView.setVisibility(View.GONE); 
 
                    fillListViewWithData();

                        }




            });
        }, 5000);
    }

    private void fillListViewWithData() {
        ListView listView = getView().findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
 
        adapter.add("Friend 1");
        adapter.add("Friend 2");
        
        listView.setAdapter(adapter);

    
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(listView, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(1000); 
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


MyFriendListFragment.java:略

xml:略
