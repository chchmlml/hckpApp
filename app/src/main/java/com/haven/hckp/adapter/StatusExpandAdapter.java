package com.haven.hckp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.haven.hckp.R;
import com.haven.hckp.bean.OneStatusEntity;
import com.haven.hckp.bean.TwoStatusEntity;

import java.util.List;

public class StatusExpandAdapter extends BaseExpandableListAdapter {
	//private static final String TAG = "StatusExpandAdapter";
	private LayoutInflater inflater = null;
	private List<OneStatusEntity> oneList;
	private Context context;
	
	
	public StatusExpandAdapter(Context context, List<OneStatusEntity> oneList) {
		this.oneList = oneList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return oneList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(oneList.get(groupPosition).getTwoList() == null){
			return 0;
		}else{
			return oneList.get(groupPosition).getTwoList().size();
		}
	}

	@Override
	public OneStatusEntity getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return oneList.get(groupPosition);
	}

	@Override
	public TwoStatusEntity getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return oneList.get(groupPosition).getTwoList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		GroupViewHolder holder = new GroupViewHolder();
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.one_status_item, null);
		}
		holder.groupName = (TextView) convertView.findViewById(R.id.one_status_name);
		holder.group_tiao = (TextView) convertView.findViewById(R.id.group_tiao);
		
		holder.groupName.setText(oneList.get(groupPosition).getStatusName());
		if(oneList.get(groupPosition).getTwoList().get(0).isIsfinished()){
			holder.groupName.setTextColor(context.getResources().getColor(R.color.black));
			holder.group_tiao.setBackgroundColor(context.getResources().getColor(R.color.orange));
		}else{
			holder.group_tiao.setBackgroundColor(context.getResources().getColor(R.color.graywhite));
		}
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		ChildViewHolder viewHolder = null;
		TwoStatusEntity entity = getChild(groupPosition, childPosition);
		if (convertView != null) {
			viewHolder = (ChildViewHolder) convertView.getTag();
		} else {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.two_status_item, null);
			viewHolder.childName = (TextView) convertView.findViewById(R.id.two_status_name);
			viewHolder.twoStatusTime = (TextView) convertView.findViewById(R.id.two_complete_time);
			viewHolder.tiao = (TextView) convertView.findViewById(R.id.tiao);
			
			
			
			
		}
		viewHolder.childName.setText(entity.getStatusName());
		viewHolder.twoStatusTime.setText(entity.getCompleteTime());
		
		if(entity.isIsfinished()){
			viewHolder.childName.setTextColor(context.getResources().getColor(R.color.black));
			viewHolder.tiao.setBackgroundColor(context.getResources().getColor(R.color.orange));
		}else{
			viewHolder.tiao.setBackgroundColor(context.getResources().getColor(R.color.graywhite));
		}
		
		convertView.setTag(viewHolder);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class GroupViewHolder {
		TextView groupName;
		public TextView group_tiao;
	}
	
	private class ChildViewHolder {
		public TextView childName;
		public TextView twoStatusTime;
		public TextView tiao;
	}

}
