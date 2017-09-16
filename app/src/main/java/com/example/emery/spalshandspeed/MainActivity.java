package com.example.emery.spalshandspeed;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //通常会有个SplashActivity但是优化后，改造成SplashFragment,在第一时就将SplashFragment 展现给用户。并且
        //MainActivity的布局里会有一个Fragment的容器，和一个ViewStub（通过layout属性，引入MainActivity原来的布局）
        //目的就是将Fragment添加进MainActivity的时候，暂时不要加载MainActivity的布局，其实本来也没有必要加载，用户本来
        //就应该先看到SplashFragment,这个个时候MainActivity的布局没有必要加载。所以这里等到SplashFragment显示出来后
        //再通过ViewStub(只是在布局中看见，view tree 中没有，也是隐藏的，这样避免使用里 view.gone,因为即使使用里布局创建
        //的时候还是会被加载）加载MainActivity的布局，但是Fragment这个时候还没有被移除，当Fragment被移除的时候，用户就可以
        //看见通过ViewStub加载的布局。注意的是 ViewStub只能加载布局一次，就再也不能控制布局了。


        final SplashFragment splashFragment = SplashFragment.newInstance(new Bundle());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_splash, splashFragment).commit();

        //判断当前窗体加载完毕的时候执行，移除SplashFragment,
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                ViewStub viewStub = (ViewStub) findViewById(R.id.view_stub_main);
                View inflate = viewStub.inflate();
                inflate.findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "show", Toast.LENGTH_SHORT).show();
                    }
                });
                //延迟一段时间后再移除SplashFragment这段时间前可以做一些操作,如加载MainActivity的一些数据，
                // 延迟任务执行完后(这里模拟动画，或者加载数据），MainActivity的布局就应该显示出来了
                mHandler.postDelayed(new DelayRunnable(MainActivity.this, splashFragment), 2000);
            }
        });
    }

    static class DelayRunnable implements Runnable {
        private WeakReference<Context> mContexRef;
        private WeakReference<SplashFragment> mSplashFragmentRef;

        public DelayRunnable(Context context, SplashFragment f) {
            mContexRef = new WeakReference<Context>(context);
            mSplashFragmentRef = new WeakReference<SplashFragment>(f);
        }

        @Override
        public void run() {
            //移除fragment
            if (mContexRef != null) {
                FragmentActivity activity = (FragmentActivity) mContexRef.get();
                activity.getSupportFragmentManager().beginTransaction().remove(mSplashFragmentRef
                        .get()).commit();

            }
        }
    }
}
