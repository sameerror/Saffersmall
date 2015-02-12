package com.nurakanbpo.saffersmall;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.nurakanbpo.saffersmall.adapters.SimpleListAdapter;
import com.nurakanbpo.saffersmall.adapters.SmartFragmentStatePagerAdapter;
import com.nurakanbpo.saffersmall.helper.CategoryFragment;
import com.nurakanbpo.saffersmall.helper.SlidingTabLayout;

public class FragmentHome extends Fragment {
	View view;
	private AsyncHttpClient client;
	private List<List<HashMap<String, String>>> totalList = new ArrayList<List<HashMap<String, String>>>();
	private int numItems;
	private List<String> title = new ArrayList<String>();
	// test

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, container, false);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", MySSLSocketFactory
				.getFixedSocketFactory(), 443));
		client = new AsyncHttpClient(schemeRegistry);
		client.setMaxRetriesAndTimeout(3, 300000);

		MainActivity.msgTextView.setText("Connecting...");
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		final ViewPager viewPager = (ViewPager) view
				.findViewById(R.id.tabsViewPager);
		final SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view
				.findViewById(R.id.slidingTabs);
		final HListView listView = (HListView) view
				.findViewById(R.id.hListView1);

		if (savedInstanceState != null) {

		} else {
			client.get(getActivity(), MainActivity.HOME_API + MainActivity.API,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							try {
								// for category ads
								JSONArray categoryArray = response
										.getJSONArray("cats");
								// get number of cats
								numItems = categoryArray.length();
								// get items in each cat
								for (int i = 0; i < numItems; i++) {
									JSONObject jsonObject = categoryArray
											.getJSONObject(i);
									title.add(jsonObject
											.getString("category_name"));
									JSONArray adsArray = jsonObject
											.getJSONArray("ads");
									List<HashMap<String, String>> tempMapList = parseAndSetAdsData(adsArray);
									totalList.add(i, tempMapList);
								}

								MyPagerAdapter myViewPagerAdapter = new MyPagerAdapter(
										getFragmentManager(), numItems,
										FragmentHome.this.getActivity(),
										totalList, title);
								viewPager.setAdapter(myViewPagerAdapter);
								slidingTabLayout.setViewPager(viewPager);

								// for sfi ads
								JSONArray sfiArray = response
										.getJSONArray("sfi");
								List<HashMap<String, String>> sfiMapList = parseAndSetAdsData(sfiArray);
								SimpleListAdapter listAdapter = new SimpleListAdapter(
										getActivity(), sfiMapList);
								listView.setAdapter(listAdapter);
								MainActivity.msgTextView
										.setVisibility(View.GONE);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							Log.i("connection failure", throwable.getMessage());
							MainActivity.msgTextView.setText(throwable
									.getMessage());
						}
					});
		}

		super.onActivityCreated(savedInstanceState);
	}

	private List<HashMap<String, String>> parseAndSetAdsData(JSONArray jsonArray)
			throws JSONException {
		List<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();

		for (int j = 0; j < jsonArray.length(); j++) {
			JSONObject adsJsonObj = jsonArray.getJSONObject(j);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("sno", adsJsonObj.getString("sno"));
			hashMap.put("bid", adsJsonObj.getString("bid"));
			hashMap.put("name", adsJsonObj.getString("name"));
			hashMap.put("cat_id", adsJsonObj.getString("cat_id"));
			hashMap.put("location", adsJsonObj.getString("location"));
			hashMap.put("city", adsJsonObj.getString("city"));
			hashMap.put("country", adsJsonObj.getString("country"));
			hashMap.put("price", adsJsonObj.getString("price"));
			hashMap.put("description", adsJsonObj.getString("description"));
			hashMap.put("package_id", adsJsonObj.getString("package_id"));
			hashMap.put("images", adsJsonObj.getString("images"));
			hashMap.put("video", adsJsonObj.getString("video"));
			hashMap.put("status", adsJsonObj.getString("status"));
			// hashMap.put("is_link_ad", adsJsonObj.getString("is_link_ad"));
			hashMap.put("expire_date", adsJsonObj.getString("expire_date"));
			hashMap.put("date_added", adsJsonObj.getString("date_added"));
			// hashMap.put("bump", adsJsonObj.getString("bump"));
			// hashMap.put("topad", adsJsonObj.getString("topad"));
			// hashMap.put("highlight", adsJsonObj.getString("highlight"));
			// hashMap.put("urgent", adsJsonObj.getString("urgent"));
			// hashMap.put("home", adsJsonObj.getString("home"));
			// hashMap.put("extra_expired",
			// adsJsonObj.getString("extra_expired"));
			// hashMap.put("expired", adsJsonObj.getString("expired"));
			// hashMap.put("is_sfi", adsJsonObj.getString("is_sfi"));
			if (adsJsonObj.has("user"))
				hashMap.put("user", adsJsonObj.getString("user"));
			// hashMap.put("profile", adsJsonObj.getString("profile"));
			// hashMap.put("phone", adsJsonObj.getString("phone"));
			// hashMap.put("username", adsJsonObj.getString("username"));
			maps.add(j, hashMap);
		}

		return maps;
	}

	// Extend from SmartFragmentStatePagerAdapter now instead for more dynamic
	// ViewPager items
	public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
		private int NUM_ITEMS;
		private Context context;

		private List<List<HashMap<String, String>>> dataList;
		private List<String> TITLE;

		public MyPagerAdapter(FragmentManager fragmentManager, int numItems,
				Context context, List<List<HashMap<String, String>>> dataList,
				List<String> title) {
			super(fragmentManager);
			NUM_ITEMS = numItems;
			this.context = context;
			this.dataList = dataList;
			this.TITLE = title;
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			// return GenericListFragment.newInstance(dataList.get(position),
			// context);
			return CategoryFragment
					.newInstance(dataList.get(position), context);
		}

		// Returns the page title for the top indicator
		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE.get(position);
		}

	}
}
