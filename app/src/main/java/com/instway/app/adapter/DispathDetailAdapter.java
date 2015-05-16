package com.instway.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.instway.app.R;
import com.instway.app.common.StringUtils;
import java.util.List;
import java.util.Map;

public class DispathDetailAdapter extends BaseAdapter {
    private Context context;// 运行上下文
    private List<Map<String, Object>> listItems;// 数据集合
    private LayoutInflater listContainer;// 视图容器
    private int itemViewResource;// 自定义项视图源

    static class ListItemView { // 自定义控件集合
        public TextView reachdate;
        public ImageView status;
        public TextView startcity;
        public TextView endcity;
    }

    /**
     * 实例化Adapter
     *
     * @param context
     * @param data
     * @param resource
     */
    public DispathDetailAdapter(Context context, List<Map<String, Object>> data, int resource) {
        this.context = context;
        this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
        this.itemViewResource = resource;
        this.listItems = data;
    }

    public int getCount() {
        return listItems.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    /**
     * ListView Item设置
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 自定义视图
        ListItemView listItemView = null;
        if (convertView == null) {
            // 获取list_item布局文件的视图
            convertView = listContainer.inflate(this.itemViewResource, null);
            listItemView = new ListItemView();
            // 获取控件对象
            listItemView.reachdate = (TextView) convertView.findViewById(R.id.reachdate);
            listItemView.status = (ImageView) convertView.findViewById(R.id.status_icon);
            listItemView.startcity = (TextView) convertView.findViewById(R.id.start_city);
            listItemView.endcity = (TextView) convertView.findViewById(R.id.end_city);

            // 设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        // 设置文字和图片
        Map<String, Object> news = listItems.get(position);

        listItemView.reachdate.setText(StringUtils.toString(news.get("tp_o_reachdate")));
        listItemView.reachdate.setTag(news);
        //listItemView.status.setText(StringUtils.toString(news.get("tp_o_status")));

        listItemView.startcity.setText(StringUtils.toString(news.get("tp_o_start_city")));
        listItemView.endcity.setText(StringUtils.toString(news.get("tp_o_end_city")));

        int status = StringUtils.toInt(StringUtils.toString(news.get("tp_o_status")));
        switch (status)
        {
            case 1:
            case 2:
                listItemView.status.setImageDrawable(context.getResources().getDrawable(R.drawable.trans_status_3));
                break;
            case 3:
            case 4:
                listItemView.status.setImageDrawable(context.getResources().getDrawable(R.drawable.trans_status_0));
                break;
            case 5:
                listItemView.status.setImageDrawable(context.getResources().getDrawable(R.drawable.trans_status_1));
                break;
            case 6:
            case 7:
                listItemView.status.setImageDrawable(context.getResources().getDrawable(R.drawable.trans_status_2));
                break;
        }
        return convertView;
    }
}
