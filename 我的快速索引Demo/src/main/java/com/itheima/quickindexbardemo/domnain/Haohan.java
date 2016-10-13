package com.itheima.quickindexbardemo.domnain;

import com.itheima.quickindexbardemo.Utils.PinyinUtil;

/**
 * Created by Administrator on 2016/10/12 0012.
 */

public class Haohan implements Comparable<Haohan>{
    private String name;
    private String pinyin;

    public Haohan(String name) {
        this.name = name;
        this.pinyin = PinyinUtil.getPinyin(name);
    }

    public Haohan() {
    }



    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    @Override
    public int compareTo(Haohan o) {

        return this.pinyin.compareTo(o.pinyin);
    }
}
