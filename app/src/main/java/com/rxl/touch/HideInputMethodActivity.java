package com.rxl.touch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HideInputMethodActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_input_method);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HideInputMethodActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 为了彻底解决点击除EditText时键盘的隐藏功能，需要在Activity的事件分发逻辑中，添加点击位置的判断，
        // 如果当前焦点是EditText，点击位置不是EditText则隐藏
        if (ev.getAction() == MotionEvent.ACTION_DOWN && getCurrentFocus() != null) {
            if (getCurrentFocus() instanceof EditText) {
                // 判断是否需要隐藏
                if (shouleHideInputMethod(ev, getCurrentFocus()) && getCurrentFocus().getWindowToken() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean shouleHideInputMethod(MotionEvent event, View v) {
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0];
        int top = l[1];
        int right = left + v.getWidth();
        int bottom = top + v.getHeight();

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        if (x > left && x < right && y > top && y < bottom) {
            return false;
        }
        return true;
    }

}
