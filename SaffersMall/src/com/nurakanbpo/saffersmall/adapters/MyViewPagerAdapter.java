package com.nurakanbpo.saffersmall.adapters;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter {

	ArrayList<View> views;
	List<String> viewTitle;

	public MyViewPagerAdapter(ArrayList<View> views, List<String> viewTitle) {
		this.views = views;
		this.viewTitle = viewTitle;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {

		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return this.viewTitle.get(position);
	}
}