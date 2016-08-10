package com.rxl.touch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnTouchListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HideInputActivity.class));
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 在Activity的TounchEvent时，getX/Y 和getRawX/Y 相同，结果都为相对窗口左上角坐标
            Log.i("MainActivity", "TouchEvent-->  getX " + event.getX() + "  getY " + event.getY() + "  getRawX " + event.getRawX() + "  getRawY " + event.getRawY());
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 为View控件设置了onTouchListener getX/Y 得到的是距离父控件对应边缘的距
        // getX() = getLeft() + getTranslationX()
        if (v != null) {
            Log.i("MainActivity", v.getX() + "--->" + v.getY());
            Log.i("MainActivity", v.getLeft() + "--->" + v.getTop());
        }

        // View的onTounchEvent事件中Event的getX/Y 和 getRawX/Y 的结果是不一样的，X/Y 是点击位置相对当前控件左上角的坐标，RawX/Y是相对窗口左上角坐标
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i("MainActivity", "TouchListener-->  getX " + event.getX() + "  getY " + event.getY() + "  getRawX " + event.getRawX() + "  getRawY " + event.getRawY());
        }
        return false;
    }
}
