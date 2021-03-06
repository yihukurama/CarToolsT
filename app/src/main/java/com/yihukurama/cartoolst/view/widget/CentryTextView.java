package com.yihukurama.cartoolst.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yihukurama.cartoolst.CartoolApp;

/**
 * Created by dengshuai on 16/4/2.
 */
public class CentryTextView extends TextView {
    public CentryTextView(Context context) {
        super(context);
        initFont();
    }

    public CentryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public CentryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();

    }


    private void initFont(){
        setTypeface(CartoolApp.getInstace().getTypefaceC());
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
