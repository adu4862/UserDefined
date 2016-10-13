package com.itheima.quickindexbardemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.quickindexbardemo.Utils.PinyinUtil;
import com.itheima.quickindexbardemo.adpter.PersonsAdapter;
import com.itheima.quickindexbardemo.domnain.Haohan;
import com.itheima.quickindexbardemo.ui.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private android.widget.ListView lv;
    private android.widget.TextView tvindex;
    private android.widget.RelativeLayout activitymain;
    private List<Haohan> haohans;
    private com.itheima.quickindexbardemo.ui.QuickIndexBar bar;
    private TextView tittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.tittle = (TextView) findViewById(R.id.tittle);
        this.bar = (QuickIndexBar) findViewById(R.id.bar);
        this.activitymain = (RelativeLayout) findViewById(R.id.activity_main);
        this.tvindex = (TextView) findViewById(R.id.tv_index);
        this.lv = (ListView) findViewById(R.id.lv);

        initHaohans();

        lv.setAdapter(new PersonsAdapter(this, haohans));

        bar.setmOnLetterUpdateListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void updateLetter(String letter) {
                // Utils.showToast(MainActivity.this,letter);
                showTextView(letter);
                for (int i = 0; i < haohans.size(); i++) {
                    String c = String.valueOf(haohans.get(i).getPinyin().charAt(0));
                    if (TextUtils.equals(letter, c)) {
                        lv.setSelection(i);
                        break;
                    }
                }


            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            // 当滚动的状态发生变化的时候调用的方法， 静止-->滚动 滚动-->静止
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            // 一旦滚动就执行的方法
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem != 0) {


                    Haohan lastHaohan = haohans.get(firstVisibleItem - 1);
                    Haohan currentHaohan = haohans.get(firstVisibleItem);

                    String pinyin1 = lastHaohan.getPinyin();
                    String pinyin2 = currentHaohan.getPinyin();

                    if (!TextUtils.equals(pinyin1, pinyin2)) {
                        tittle.setText(pinyin2.charAt(0) + "");
                    }
                } else {
                    Haohan currentHaohan = haohans.get(firstVisibleItem);
                    String pinyin2 = currentHaohan.getPinyin();
                    tittle.setText(pinyin2.charAt(0) + "");
                }

            }
        });
    }

    private Handler mHandler = new Handler();

    private void showTextView(String letter) {
        tvindex.setVisibility(View.VISIBLE);
        tvindex.setText(letter);

        //移除所有的延迟操作和消息
        mHandler.removeCallbacksAndMessages(tvindex);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvindex.setVisibility(View.GONE);
            }
        }, 2000);

    }

    private void initHaohans() {
        haohans = new ArrayList<>();

        for (String name :
                Cheeses.NAMES) {
            String pinyin = PinyinUtil.getPinyin(name);
            haohans.add(new Haohan(name));
        }

        //排序
        Collections.sort(haohans);
    }
}
