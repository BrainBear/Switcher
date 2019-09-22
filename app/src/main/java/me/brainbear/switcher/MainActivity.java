package me.brainbear.switcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import me.brainbear.Switcher;

public class MainActivity extends AppCompatActivity {

    {
        Switcher.initDefaultAdapter(new GlobalSwitcherAdapter());
    }

    private Switcher mSwitcher;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_load_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadEmpty();
            }
        });

        findViewById(R.id.btn_load_failed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadFailed();
            }
        });

        findViewById(R.id.btn_load_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadSuccess();
            }
        });

    }

    private final static int DELAY = 3000;

    private void showLoadFailed() {
        getSwitcher().showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwitcher().showFailed();
            }
        }, DELAY);
    }

    private void showLoadEmpty() {
        getSwitcher().showLoading();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSwitcher().showEmpty();
            }
        }, DELAY);
    }

    private void showLoadSuccess() {
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
                    showLoadSuccess();
                }
            });
        }

        return mSwitcher;
    }

}
