package me.brainbear.switcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import me.brainbear.Switcher;

public class SwitcherActivity extends AppCompatActivity {

    private Switcher mSwitcher;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);

    }

    private final static int DELAY = 3000;

    public void showLoadFailed(View v) {
        getSwitcher().showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwitcher().showFailed();
            }
        }, DELAY);
    }

    public void showLoadEmpty(View v) {
        getSwitcher().showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwitcher().showEmpty();
            }
        }, DELAY);
    }

    public void showLoadSuccess(View v) {
        getSwitcher().showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwitcher().showContent();
            }
        }, DELAY);
    }

    private Switcher getSwitcher() {
        if (null == mSwitcher) {
            mSwitcher = Switcher.with(this).setRetryTask(new Runnable() {
                @Override
                public void run() {
                    showLoadSuccess(null);
                }
            });
        }

        return mSwitcher;
    }

}
