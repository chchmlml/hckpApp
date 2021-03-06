package com.instway.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.instway.app.R;
import com.instway.app.bean.Dispath;
import com.instway.app.common.StringUtils;
import com.instway.app.common.UIHelper;

import java.util.List;

public class DispathViewAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<Dispath> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	static class ListItemView { // 自定义控件集合
		public TextView tpTcPhone;
		public TextView tpTcName;
        public TextView tpDiEnddate;
        public ImageView status;
	}

	/**
	 * 实例化Adapter
	 *
	 * @param context
	 * @param data
	 * @param resource
	 */
	public DispathViewAdapter(Context context, List<Dispath> data, int resource) {
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
            listItemView.tpTcPhone = (TextView) convertView.findViewById(R.id.tp_tc_phone);
            listItemView.tpTcName = (TextView) convertView.findViewById(R.id.tp_tc_name);
            listItemView.status = (ImageView) convertView.findViewById(R.id.status_icon);
            listItemView.tpDiEnddate = (TextView) convertView.findViewById(R.id.tp_o_reachdate);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		Dispath news = listItems.get(position);
        listItemView.tpTcPhone.setText(news.getTp_tc_phone());
        listItemView.tpTcPhone.setTag(news);
        listItemView.tpTcName.setText(news.getTp_tc_name());
        listItemView.tpDiEnddate.setText(news.getTp_di_startdate());
		int status = StringUtils.toInt(news.getTp_di_status());
		listItemView.status.setImageDrawable(UIHelper.getIconByStatus(context,status));

		return convertView;
	}
}
