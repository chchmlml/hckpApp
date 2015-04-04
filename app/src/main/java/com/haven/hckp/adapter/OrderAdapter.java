package com.haven.hckp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haven.hckp.AppException;
import com.haven.hckp.R;
import com.haven.hckp.api.ApiClient;
import com.haven.hckp.bean.Order;

import java.util.List;

/**
 * 新闻资讯Adapter类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class OrderAdapter extends BaseAdapter {
	private Context 					context;//运行上下文
	private List<Order> 					listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源
	static class ListItemView{				//自定义控件集合
	        public TextView orderTitle;
		    public TextView orderAttr1;
	        public TextView orderAttr2;
		    public TextView orderRank;
	        public TextView orderScore;
		    public TextView orderStart;
	        public TextView orderEnd;
		    public TextView orderLength;
        public ImageView orderThumb;
	 }

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public OrderAdapter(Context context, List<Order> data, int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
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
		//Log.d("method", "getView");
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.orderTitle = (TextView)convertView.findViewById(R.id.order_title);
			listItemView.orderRank = (TextView)convertView.findViewById(R.id.order_rank);
            listItemView.orderScore= (TextView)convertView.findViewById(R.id.order_score);
            listItemView.orderAttr1= (TextView)convertView.findViewById(R.id.order_attr_1);
            listItemView.orderAttr2= (TextView)convertView.findViewById(R.id.order_attr_2);
            listItemView.orderStart= (TextView)convertView.findViewById(R.id.order_start);
            listItemView.orderEnd= (TextView)convertView.findViewById(R.id.order_end);
            listItemView.orderThumb= (ImageView)convertView.findViewById(R.id.order_thumd);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Order news = listItems.get(position);
		
		listItemView.orderTitle.setText(news.getTitle());
		listItemView.orderRank.setTag(news);//设置隐藏参数(实体类)
		listItemView.orderScore.setText(news.getAuthor());
        listItemView.orderAttr1.setText(news.getTitle());
        listItemView.orderAttr2.setText(news.getTitle());
        listItemView.orderStart.setText(news.getTitle());
        listItemView.orderEnd.setText(news.getCommentCount()+"");
        Bitmap bitmap = null;
        try {
            bitmap = ApiClient.getNetBitmap("http://blog.3gstdy.com/wp-content/themes/twentyten/images/headers/path.jpg");
        } catch (AppException e) {
            e.printStackTrace();
        }
		
		return convertView;
	}
}