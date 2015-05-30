package com.instway.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.instway.app.R;
import com.instway.app.bean.News;
import com.instway.app.common.StringUtils;

import java.util.List;

public class ListViewNewsAdapter extends BaseAdapter {
    private Context context;// 运行上下文
    private List<News> listItems;// 数据集合
    private LayoutInflater listContainer;// 视图容器
    private int itemViewResource;// 自定义项视图源

    static class ListItemView { // 自定义控件集合
        public TextView endtime;
        public TextView startPlace;
        public TextView endPlace;
        public TextView desc;
        public ImageView priceBtn;
        public ImageView statusIcon;
    }

    /**
     * 实例化Adapter
     *
     * @param context
     * @param data
     * @param resource
     */
    public ListViewNewsAdapter(Context context, List<News> data, int resource) {
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
            listItemView.endtime = (TextView) convertView.findViewById(R.id.end_time);
            listItemView.startPlace = (TextView) convertView.findViewById(R.id.start_place);
            listItemView.endPlace = (TextView) convertView.findViewById(R.id.end_place);
            listItemView.desc = (TextView) convertView.findViewById(R.id.desc);
            listItemView.priceBtn = (ImageView) convertView.findViewById(R.id.send_price_btn);
            listItemView.statusIcon = (ImageView) convertView.findViewById(R.id.status_icon);

            // 设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        // 设置文字和图片
        News news = listItems.get(position);
        listItemView.endtime.setText(news.getTp_diy_enddate());
        listItemView.endtime.setTag(news);
        listItemView.startPlace.setText(news.getTp_diy_start_city());
        listItemView.endPlace.setText(news.getTp_diy_end_city());
        listItemView.desc.setText(news.getTp_diy_desc());
        if("2".equals(news.getTp_diy_category())){
            listItemView.statusIcon.setVisibility(View.VISIBLE);
        }else{
            listItemView.statusIcon.setVisibility(View.GONE);
        }
        if(StringUtils.isEmpty(news.getTp_diyp_price())){
            listItemView.priceBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_unbaojia));
        }else{
            listItemView.priceBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_baojia));
        }

        return convertView;
    }
}
