package com.rxl.touch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private EditText edit;
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
                Toast.makeText(MainActivity.this, "button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i("MainActivity", "TouchEvent-->" + event.getX() + "  event  " + event.getRawY() + "  event  " + event.getRawX() + "  event  " + event.getRawY());
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 为View控件设置了onTouchListener getX/Y 得到的是距离父控件的距离
//        getX() = getLeft() + getTranslationX()
        if (v != null) {
            Log.i("MainActivity", v.getX() + "--->" + v.getY());
            Log.i("MainActivity", v.getLeft() + "--->" + v.getTop());
        }
        // View的onTounchEvent事件中Event的getX/Y 和 getRawX/Y 的结果是不一样的，X/Y 是点击位置相对控件左上角的坐标，RawX/Y是相对窗口左上角坐标
        // 在Activity的TounchEvent时，getX/Y 和getRawX/Y 相同，结果都为相对窗口左上角坐标
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i("MainActivity", "TouchListener-->" + event.getX() + "  event  " + event.getY() + "  event  " + event.getRawX() + "  event  " + event.getRawY());
        }
        return false;
    }
}
