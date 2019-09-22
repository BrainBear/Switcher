package me.brainbear.switcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import me.brainbear.Switcher;

/**
 * created by canxionglian on 2019-09-22
 */
public class GlobalSwitcherAdapter implements Switcher.Adapter {


    @Override
    public View onCreateView(final Switcher switcher, Context context, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = null;
        switch (viewType) {
            case Switcher.VIEW_EMPTY:
                view = layoutInflater.inflate(R.layout.layout_empty, null);

                view.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switcher.retry();
                    }
                });
                break;
            case Switcher.VIEW_FAILED:
                view = layoutInflater.inflate(R.layout.layout_failed, null);
                view.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switcher.retry();
                    }
                });
                break;
            case Switcher.VIEW_LOADING:
                view = layoutInflater.inflate(R.layout.layout_loading, null);
                break;
        }

        return view;
    }
}
