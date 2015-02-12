package com.nurakanbpo.saffersmall.adapters;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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

public class SearchListAdapter extends ArrayAdapter<HashMap<String, String>> {
	Context context;
	List<HashMap<String, String>> objects;

	public SearchListAdapter(Context context,
			List<HashMap<String, String>> objects) {

		super(context, R.layout.search_result_row_layout,
				R.id.itemNameTextView, objects);
		this.context = context;
		this.objects = objects;
	}

	private class ViewHolder {
		ProgressBar progressBar;
		ImageView imageView;
		TextView name;
		TextView location;
		TextView views;
		TextView description;
		TextView price;
		TextView date;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.search_result_row_layout,
					parent, false);
			viewHolder.progressBar = (ProgressBar) convertView.findViewById(
					R.id.itemImageView).findViewById(R.id.progressBar);
			viewHolder.imageView = (ImageView) convertView.findViewById(
					R.id.itemImageView).findViewById(R.id.simpleImageView);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.itemNameTextView);
			viewHolder.views = (TextView) convertView
					.findViewById(R.id.itemViewsTextView);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.itemDateTextView);
			viewHolder.location = (TextView) convertView
					.findViewById(R.id.itemLocTextView);
			viewHolder.price = (TextView) convertView
					.findViewById(R.id.itemPriceTextView);
			viewHolder.description = (TextView) convertView
					.findViewById(R.id.itemDescTextView);

			convertView.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) convertView.getTag();

		final ProgressBar pb = viewHolder.progressBar;
		final ImageView iv = viewHolder.imageView;

		String images = objects.get(position).get("images");
		String[] image = images.split(",");

		ImageLoader.getInstance().displayImage(
				MainActivity.IMAGE_URL + image[0], viewHolder.imageView,
				MainActivity.options, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						pb.setVisibility(View.INVISIBLE);
						iv.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}
				});

		viewHolder.name.setText(objects.get(position).get("name"));
		viewHolder.description.setText(android.text.Html.fromHtml(objects.get(
				position).get("description")));
		viewHolder.views.setText(objects.get(position).get("views"));
		viewHolder.location.setText(objects.get(position).get("location"));
		viewHolder.price.setText(objects.get(position).get("price"));
		viewHolder.date.setText(objects.get(position).get("date_added"));

		return convertView;
	}

	@Override
	public int getPosition(HashMap<String, String> item) {
		return super.getPosition(item);
	}
}
