package com.haven.hckp.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.haven.hckp.R;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class TopIndicatorForState extends LinearLayout {
    private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();
    private List<View> mViewList = new ArrayList<View>();
    private CharSequence[] mLabels = new CharSequence[]{"详情", "状态"};
    private int mScreenWidth;
    private int mUnderLineWidth;
    private View mUnderLine;
    private int mUnderLineFromX = 0;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TopIndicatorForState(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TopIndicatorForState(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopIndicatorForState(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        setOrientation(LinearLayout.VERTICAL);
        this.setBackgroundColor(Color.rgb(250, 250, 250));

        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mUnderLineWidth = mScreenWidth / mLabels.length;

        mUnderLine = new View(context);
        mUnderLine.setBackgroundColor(Color.rgb(247, 88, 123));
        //LayoutParams underLineParams = new LayoutParams(mUnderLineWidth, 5);

        LinearLayout topLayout = new LinearLayout(context);
        LayoutParams topLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        topLayout.setOrientation(LinearLayout.HORIZONTAL);
        topLayout.setBackgroundColor(getResources().getColor(R.color.white));

        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;

        int size = mLabels.length;
        for (int i = 0; i < size; i++) {

            final int index = i;

            final View view = inflater.inflate(R.layout.top_indicator_item2,null);

            final CheckedTextView itemName = (CheckedTextView) view.findViewById(R.id.item_name);
            itemName.setCompoundDrawablePadding(10);
            itemName.setText(mLabels[i]);
            itemName.setWidth(mUnderLineWidth);
            topLayout.addView(view, params);

            itemName.setTag(index);

            mCheckedList.add(itemName);
            mViewList.add(view);

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != mTabListener) {
                        mTabListener.onIndicatorSelected(index);
                    }
                }
            });

            if (i == 0) {
                itemName.setChecked(true);
                itemName.setBackgroundColor(getResources().getColor(R.color.white));
                itemName.setTextColor(getResources().getColor(R.color.black));
            } else {
                itemName.setChecked(false);
                itemName.setBackgroundColor(getResources().getColor(R.color.graywhite));
                itemName.setTextColor(getResources().getColor(R.color.gray));
            }

        }
        this.addView(topLayout, topLayoutParams);
        //this.addView(mUnderLine, underLineParams);
    }

    /**
     */
    public void setTabsDisplay(Context context, int index) {
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++) {
            CheckedTextView checkedTextView = mCheckedList.get(i);
            if ((Integer) (checkedTextView.getTag()) == index) {
                checkedTextView.setChecked(true);
                checkedTextView.setBackgroundColor(getResources().getColor(R.color.white));
                checkedTextView.setTextColor(getResources().getColor(R.color.black));
            } else {
                checkedTextView.setChecked(false);
                checkedTextView.setBackgroundColor(getResources().getColor(R.color.graywhite));
                checkedTextView.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    }

    private OnTopIndicatorListener mTabListener;

    public interface OnTopIndicatorListener {
        void onIndicatorSelected(int index);
    }

    public void setOnTopIndicatorListener(OnTopIndicatorListener listener) {
        this.mTabListener = listener;
    }

}
