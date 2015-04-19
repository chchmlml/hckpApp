package com.haven.hckp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.haven.hckp.R;
import com.haven.hckp.common.StringUtils;
import java.util.List;
import java.util.Map;

public class DispathDetailAdapter extends BaseAdapter {
    private Context context;// 运行上下文
    private List<Map<String, Object>> listItems;// 数据集合
    private LayoutInflater listContainer;// 视图容器
    private int itemViewResource;// 自定义项视图源

    static class ListItemView { // 自定义控件集合
        public TextView sn;
        public TextView reachdate;
        public TextView status;
        public TextView type;
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
            listItemView.sn = (TextView) convertView.findViewById(R.id.tp_tt_sn);
            listItemView.reachdate = (TextView) convertView.findViewById(R.id.tp_o_reachdate);
            listItemView.status = (TextView) convertView.findViewById(R.id.tp_o_status);
            listItemView.type = (TextView) convertView.findViewById(R.id.tp_o_type);

            // 设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        // 设置文字和图片
        Map<String, Object> news = listItems.get(position);

        listItemView.sn.setText(StringUtils.toString(news.get("tp_tt_sn")));
        listItemView.sn.setTag(news);
        listItemView.reachdate.setText(StringUtils.toString(news.get("tp_o_reachdate")));
        String starus = "";
        switch (StringUtils.toInt(news.get("tp_o_status")))
        {
            case 1:
                starus = "未提交";
                break;
            case 2:
                starus = "未接受";
                break;
            case 3:
                starus = "已接受";
                break;
            case 4:
                starus = "已发货";
                break;
            case 5:
                starus = "运输中";
                break;
            case 6:
                starus = "已签收";
                break;
            case 7:
                starus = "中断";
                break;
        }
        listItemView.status.setText(starus);
        String type = "";
        switch (StringUtils.toInt(news.get("tp_o_type")))
        {
            case 1:
                type = "正常";
                break;
            case 2:
                type = "转交";
                break;
        }
        listItemView.type.setText(type);
        return convertView;
    }
}
