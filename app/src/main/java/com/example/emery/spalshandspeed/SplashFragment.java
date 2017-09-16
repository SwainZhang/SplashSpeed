package com.example.emery.spalshandspeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by emery on 2017/3/30.
 */

public class SplashFragment extends android.support.v4.app.Fragment {
    public static SplashFragment newInstance(Bundle bundle) {


        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.header);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;


    }
}
