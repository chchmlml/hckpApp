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
        public TextView carNo;
        public TextView carName;
        public TextView carWeight;
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
            listItemView.carName = (TextView) convertView.findViewById(R.id.car_name);
            listItemView.carNo = (TextView) convertView.findViewById(R.id.car_no);
            listItemView.carWeight = (TextView) convertView.findViewById(R.id.car_weight);

            // 设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        // 设置文字和图片
        Car news = listItems.get(position);

        listItemView.carNo.setText(news.getTp_car_no());
        listItemView.carNo.setTag(news);
        listItemView.carName.setText(news.getTp_car_name());
        listItemView.carWeight.setText(news.getTp_car_weight()+"t");

        return convertView;
    }
}
