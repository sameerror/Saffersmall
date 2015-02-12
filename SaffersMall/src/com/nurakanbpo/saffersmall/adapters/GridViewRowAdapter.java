package com.nurakanbpo.saffersmall.adapters;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurakanbpo.saffersmall.R;

public class GridViewRowAdapter extends ArrayAdapter<HashMap<String, String>> {

	private Context context;
	private List<HashMap<String, String>> dataList;

	public GridViewRowAdapter(Context context,
			List<HashMap<String, String>> dataList) {
		super(context, R.layout.grid_view_row_layout, R.id.itemNameTextView,
				dataList);
		this.context = context;
		this.dataList = dataList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.grid_view_row_layout, null,
					false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.itemImage);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.itemNameTextView);
			viewHolder.user = (TextView) convertView
					.findViewById(R.id.itemUserTextView);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.itemDateTextView);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();
		
		return convertView;
	}

	private class ViewHolder {
		ImageView imageView;
		TextView name;
		TextView user;
		TextView date;
	}
}
