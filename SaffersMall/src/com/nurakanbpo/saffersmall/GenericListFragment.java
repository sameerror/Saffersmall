package com.nurakanbpo.saffersmall;

import java.util.HashMap;
import java.util.List;

import com.nurakanbpo.saffersmall.adapters.ListFragmentRowAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

public class GenericListFragment extends ListFragment {
	List<HashMap<String, String>> dataList;

	public GenericListFragment(List<HashMap<String, String>> dataList) {
		this.dataList = dataList;

	}

	public static GenericListFragment newInstance(
			List<HashMap<String, String>> dataList, Context context) {
		GenericListFragment genericListFragment = new GenericListFragment(
				dataList);
		return genericListFragment;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListFragmentRowAdapter listFragmentRowAdapter = new ListFragmentRowAdapter(
				getActivity(), dataList);
		setListAdapter(listFragmentRowAdapter);
	}
}
