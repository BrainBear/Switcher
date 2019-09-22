package me.brainbear.switcher;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import me.brainbear.Switcher;

/**
 * created by canxionglian on 2019-09-22
 */
public class SwitcherFragment extends Fragment {

    private Switcher mSwitcher;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_switcher, container, false);

        mSwitcher = Switcher.with(view).setRetryTask(new Runnable() {
            @Override
            public void run() {
                showLoadSuccess(null);
            }
        });
        return mSwitcher.getWrapView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_load_failed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadFailed(v);
            }
        });

        view.findViewById(R.id.btn_load_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadEmpty(v);
            }
        });

        view.findViewById(R.id.btn_load_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadSuccess(v);
            }
        });
    }

    private final static int DELAY = 3000;

    public void showLoadFailed(View v) {
        mSwitcher.showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwitcher.showFailed();
            }
        }, DELAY);
    }

    public void showLoadEmpty(View v) {
        mSwitcher.showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwitcher.showEmpty();
            }
        }, DELAY);
    }

    public void showLoadSuccess(View v) {
        mSwitcher.showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwitcher.showContent();
            }
        }, DELAY);
    }


}
