package com.yihukurama.cartoolst.view.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yihukurama.cartoolst.CartoolApp;

/**
 * Created by dengshuai on 16/4/2.
 */
public class BebasNeueFontTextView extends TextView {
    public BebasNeueFontTextView(Context context) {
        super(context);
        initFont();
    }

    public BebasNeueFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public BebasNeueFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();

    }


    private void initFont(){
        setTypeface(CartoolApp.getInstace().getTypefaceB());

    }
}
