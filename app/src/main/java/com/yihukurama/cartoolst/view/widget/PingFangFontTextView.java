package com.yihukurama.cartoolst.view.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dengshuai on 16/4/2.
 */
public class PingFangFontTextView extends TextView {
    public PingFangFontTextView(Context context) {
        super(context);
        initFont();
    }

    public PingFangFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public PingFangFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();

    }


    private void initFont(){
//        AssetManager assertMgr = getContext().getAssets();
//        Typeface font = Typeface.createFromAsset(assertMgr, "fonts/PingFang.ttc");
//        setTypeface(font);
    }
}
