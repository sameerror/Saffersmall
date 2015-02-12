package com.nurakanbpo.saffersmall.adapters;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nurakanbpo.saffersmall.MainActivity;
import com.nurakanbpo.saffersmall.R;

public class LeftDrawerListAdapter extends ArrayAdapter<String> {
	Context context;
	List<String> objects;
	TextView tv;

	public LeftDrawerListAdapter(Context context,
			List<String> objects) {

		super(context, R.layout.left_drawer_list_view_row_layout, R.id.leftDrawerListViewRowLayout,
				objects);
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null){
			convertView = inflater.inflate(R.layout.left_drawer_list_view_row_layout, parent, false);
		}
		tv = (TextView) convertView.findViewById(R.id.leftDrawerListViewRowLayout);
		tv.setText(objects.get(position));
				
		if(position == 5 || position == 8){
			convertView.setBackgroundColor(Color.parseColor("#39a85f"));
			convertView.setEnabled(false);
		}
		return convertView;
	}

}
