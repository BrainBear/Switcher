package me.brainbear;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * created by canxionglian on 2019-09-18
 */
public class Switcher {

    private static final String TAG = "Switcher";

    public static final int VIEW_CONTENT = -1;
    public static final int VIEW_LOADING = -2;
    public static final int VIEW_FAILED = -3;
    public static final int VIEW_EMPTY = -4;


    private static boolean sDebug = true;

    private static Adapter sDefaultAdapter;
    private Adapter mAdapter;

    private Context mContext;
    private SparseArray<View> mViewsArray = new SparseArray<>();
    private Runnable mRetryTask;
    private int mCurrentViewType = VIEW_CONTENT;
    private FrameLayout mWarpViewGroup;

    private Switcher(Context context, View contentView) {
        mContext = context;
        mViewsArray.put(VIEW_CONTENT, contentView);
        mWarpViewGroup = new FrameLayout(context);

        mWarpViewGroup.setLayoutParams(contentView.getLayoutParams());

        ViewGroup parent = (ViewGroup) contentView.getParent();


        int index = parent.indexOfChild(contentView);
        parent.removeView(contentView);

        FrameLayout.LayoutParams newLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mWarpViewGroup.addView(contentView, newLp);
        parent.addView(mWarpViewGroup, index);

    }


    public static void initDefaultAdapter(@NonNull Adapter adapter) {
        sDefaultAdapter = adapter;
    }


    @NonNull
    public static Switcher with(@NonNull Activity activity) {
        FrameLayout parent = activity.findViewById(android.R.id.content);

        Switcher switcher = new Switcher(activity, parent.getChildAt(0));

        return switcher;
    }


    public Switcher setAdapter(@NonNull Adapter adapter) {
        this.mAdapter = adapter;
        return this;
    }

    public Switcher setRetryTask(Runnable retryTask) {
        this.mRetryTask = retryTask;
        return this;
    }


    public void retry() {
        if (null != mRetryTask) {
            mRetryTask.run();
        } else {
            log("retry task is null");
        }
    }


    private String getViewTypeName(int type) {
        switch (type) {
            case VIEW_CONTENT:
                return "content view";
            case VIEW_LOADING:
                return "loading view";
            case VIEW_FAILED:
                return "failed view";
            case VIEW_EMPTY:
                return "empty view";
            default:
                throw new IllegalArgumentException("invalid type:" + type);
        }
    }

    private void setCurrentView(int viewType) {
        log("set current view:" + viewType + " " + getViewTypeName(viewType));

        View lastView = mViewsArray.get(mCurrentViewType);

        if (null != lastView) {
            lastView.setVisibility(View.INVISIBLE);
            log(getViewTypeName(mCurrentViewType) + " set invisible");
        }

        mCurrentViewType = viewType;

        View currentView = mViewsArray.get(viewType);

        if (null == currentView) {

            Adapter adapter = sDefaultAdapter;

            if (null != mAdapter) {
                adapter = mAdapter;
            }

            if (null == adapter) {
                throw new NullPointerException("adapter can not be null");
            }


            currentView = adapter.onCreateView(this, mContext, viewType);

            if (null == currentView) {
                throw new NullPointerException(getViewTypeName(viewType) + " can not be null");
            }

            mWarpViewGroup.addView(currentView);
        }
        mViewsArray.put(viewType, currentView);

        currentView.setVisibility(View.VISIBLE);
        log(getViewTypeName(mCurrentViewType) + " set visible");

    }

    public void showLoading() {
        setCurrentView(VIEW_LOADING);
    }

    public void showEmpty() {
        setCurrentView(VIEW_EMPTY);
    }

    public void showFailed() {
        setCurrentView(VIEW_FAILED);
    }

    public void showContent() {
        setCurrentView(VIEW_CONTENT);
    }


    public static void setDebug(boolean debug) {
        sDebug = debug;
    }


    private static void log(String msg) {
        if (sDebug) {
            Log.i(TAG, msg);
        }
    }


    public interface Adapter {

        View onCreateView(Switcher switcher, Context context, int viewType);

    }


}
