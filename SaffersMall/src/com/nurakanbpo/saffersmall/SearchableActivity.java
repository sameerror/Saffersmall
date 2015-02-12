package com.nurakanbpo.saffersmall;

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

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.nurakanbpo.saffersmall.adapters.SearchListAdapter;
import com.nurakanbpo.saffersmall.adapters.SimpleListAdapter;

public class SearchableActivity extends ActionBarActivity {
	EditText searchEditText;
	Spinner categorySpinner;
	ImageButton searchButton;
	ListView resultListView;
	private AsyncHttpClient client;
	List<HashMap<String, String>> parsedAdsData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		searchButton = (ImageButton) findViewById(R.id.searchImageButton);
		categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		resultListView = (ListView) findViewById(R.id.searchResultListView);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", MySSLSocketFactory
				.getFixedSocketFactory(), 443));
		client = new AsyncHttpClient(schemeRegistry);
		client.setMaxRetriesAndTimeout(3, 300000);

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			// use the query to search your data somehow
			searchEditText.setText(query);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("query", query);
			RequestParams params = new RequestParams(map);
			client.post(this, MainActivity.SEARCH + MainActivity.API, params,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							try {
								JSONArray resultArray = response
										.getJSONArray("result");
								parsedAdsData = parseAdsData(resultArray);
								SearchListAdapter adapter = new SearchListAdapter(
										SearchableActivity.this, parsedAdsData);
								resultListView.setAdapter(adapter);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							// super.onSuccess(statusCode, headers, response);
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) {
							Log.e("failure", throwable.getMessage() + " "
									+ errorResponse.toString());
							try {
								JSONArray resultArray = errorResponse
										.getJSONArray("result");
								parsedAdsData = parseAdsData(resultArray);
								SearchListAdapter adapter = new SearchListAdapter(
										SearchableActivity.this, parsedAdsData);
								resultListView.setAdapter(adapter);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							// super.onFailure(statusCode, headers, throwable,
							// errorResponse);
						}
					});
		}
	}

	private List<HashMap<String, String>> parseAdsData(JSONArray jsonArray)
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

}
