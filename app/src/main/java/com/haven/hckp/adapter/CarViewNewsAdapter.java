package com.haven.hckp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haven.hckp.R;
import com.haven.hckp.bean.Car;
import com.haven.hckp.bean.News;

import java.util.List;

public class CarViewNewsAdapter extends BaseAdapter {
    private Context context;// 运行上下文
    private List<Car> listItems;// 数据集合
    private LayoutInflater listContainer;// 视图容器
    private int itemViewResource;// 自定义项视图源

    static class ListItemView { // 自定义控件集合
        public TextView orderTitle;
        public TextView orderTitle2;
        public TextView orderType;
        public TextView orderDesc;
        public TextView orderStarttime;
        public TextView orderEndtime;
        public TextView orderLength;
    }

    /**
     * 实例化Adapter
     *
     * @param context
     * @param data
     * @param resource
     */
    public CarViewNewsAdapter(Context context, List<Car> data, int resource) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // Log.d("method", "getView");

        // 自定义视图
        ListItemView listItemView = null;

        if (convertView == null) {
            // 获取list_item布局文件的视图
            convertView = listContainer.inflate(this.itemViewResource, null);

            listItemView = new ListItemView();
            // 获取控件对象
            listItemView.orderTitle = (TextView) convertView.findViewById(R.id.order_title);
            listItemView.orderTitle2 = (TextView) convertView.findViewById(R.id.order_title2);
            listItemView.orderType = (TextView) convertView.findViewById(R.id.order_type);
            listItemView.orderDesc = (TextView) convertView.findViewById(R.id.order_desc);
            listItemView.orderStarttime = (TextView) convertView.findViewById(R.id.order_starttime);
            listItemView.orderEndtime = (TextView) convertView.findViewById(R.id.order_endtime);
            listItemView.orderLength = (TextView) convertView.findViewById(R.id.order_length);

            // 设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        // 设置文字和图片
        Car news = listItems.get(position);

        listItemView.orderTitle.setText(news.getTp_d_id());
        listItemView.orderTitle.setTag(news);
        listItemView.orderTitle2.setText(news.getTp_tc_name());

        return convertView;
    }
}
