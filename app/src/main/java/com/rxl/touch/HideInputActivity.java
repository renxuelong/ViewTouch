package com.rxl.touch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 测试EditText，当Activity中存在EditText时，此控件会获取焦点，除非在该控件的父View中设置获取焦点，拦截该控件对焦点的获取
 * 或者设置其他控件获取焦点
 */
public class HideInputActivity extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_input);
        button1 = (Button) findViewById(R.id.button1);
        final boolean is = button1.requestFocus();
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HideInputActivity.this, "Click" + is, Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HideInputActivity.this, HideInputMethodActivity.class));
            }
        });
    }

    // 第一种方法，在Activity点击事件分发逻辑中处理
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 根据Android事件分发的规则，Activity的 dispatchTouchEvent() 中处理事件分发，如果在此方法中添加
        // 隐藏输入键盘的逻辑，则可以完成功能，不过当点击的为EditText时会出现键盘先隐藏再弹出的情况，不太符合需求
        if (ev.getAction() == MotionEvent.ACTION_DOWN && getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.dispatchTouchEvent(ev);
    }

    // 第二种方法，在Activity点击事件处理逻辑中处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据Android事件分发的规则，Activity的 onTouchEvent 为最末端的事件处理方法，如果在此方法中添加
        // 隐藏输入键盘的逻辑，可以实现点击空白处实现隐藏，EditText点击也不会出现先消失在弹出的情况，
        // 不过点击其他按钮或空间时此方法会失效，因为很多控件的 onTouchEvent 方法的返回值为true，
        // 即当前控件已经完全处理了该方法，Activity的onTouchEvent方法将不会得到执行
        if (event.getAction() == MotionEvent.ACTION_DOWN && getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.onTouchEvent(event);
    }
}
