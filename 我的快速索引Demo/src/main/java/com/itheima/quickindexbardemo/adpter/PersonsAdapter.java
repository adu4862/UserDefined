package com.itheima.quickindexbardemo.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itheima.quickindexbardemo.R;
import com.itheima.quickindexbardemo.Utils.PinyinUtil;
import com.itheima.quickindexbardemo.domnain.Haohan;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class PersonsAdapter extends BaseAdapter {
    private Context context;
    private List<Haohan> haohans;

    public PersonsAdapter(Context context, List<Haohan> haohans) {
        this.context = context;
        this.haohans = haohans;
    }

    @Override
    public int getCount() {
        return haohans.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list, null);
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String indexStr = null;
        String currentStr = getPinyin(position);
        if (position == 0) {
            indexStr = currentStr;

        } else {
            String lastStr = getPinyin(position - 1);
            if (!TextUtils.equals(lastStr, currentStr)) {
                indexStr = currentStr;
            }
        }


        viewHolder.tv1.setVisibility(indexStr == null ? View.GONE : View.VISIBLE);


        viewHolder.tv1.setText(getPinyin(position));
        viewHolder.tv2.setText(haohans.get(position).getName());
        return convertView;
    }

    @NonNull
    private String getPinyin(int position) {
        return PinyinUtil.getPinyin(haohans.get(position).getName()).substring(0, 1);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView tv1;
        TextView tv2;
    }


}
