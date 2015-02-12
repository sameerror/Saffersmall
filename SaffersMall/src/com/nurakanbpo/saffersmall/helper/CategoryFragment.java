package com.nurakanbpo.saffersmall.helper;

import java.util.HashMap;
import java.util.List;

import com.nurakanbpo.saffersmall.R;
import com.nurakanbpo.saffersmall.adapters.GridViewRowAdapter;
import com.nurakanbpo.saffersmall.adapters.SimpleListAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class CategoryFragment extends Fragment {

	private List<HashMap<String, String>> dataList;
	private View view;

	public CategoryFragment(List<HashMap<String, String>> dataList) {
		this.dataList = dataList;

	}

	public static CategoryFragment newInstance(
			List<HashMap<String, String>> dataList, Context context) {
		CategoryFragment categoryFragment = new CategoryFragment(dataList);
		return categoryFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.cat_items_grid_layout, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GridView gridView = (GridView) view.findViewById(R.id.gridview);
//		GridViewRowAdapter adapter = new GridViewRowAdapter(getActivity(),
//				dataList);
		SimpleListAdapter adapter = new SimpleListAdapter(getActivity(), dataList);
		gridView.setAdapter(adapter);
	}

}
