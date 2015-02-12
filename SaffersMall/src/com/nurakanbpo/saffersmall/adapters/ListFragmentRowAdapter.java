package com.nurakanbpo.saffersmall.adapters;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nurakanbpo.saffersmall.R;

public class ListFragmentRowAdapter extends
		ArrayAdapter<HashMap<String, String>> {
	Context context;
	List<HashMap<String, String>> objects;

	public ListFragmentRowAdapter(Context context,
			List<HashMap<String, String>> objects) {
		super(context, R.layout.card_view_layout, R.id.itemTextView, objects);
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
}
