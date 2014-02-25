package com.example.fragmentexercise;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.models.Madame;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListFragment extends Fragment implements
		DetailsFragment.DetailsConfig {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.list_layout, container, false);
		ListView list = (ListView) view.findViewById(R.id.listNews);

		String feed = "http://www.bonjourmadame.fr/rss";
		List<Madame> madames = Madame.readMamades(feed);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		for (Madame madame : madames) {
			HashMap<String, String> map = new HashMap<String, String>();
			;
			map.put("image", madame.getImage());
			map.put("link", madame.getImage());// madame.getLink());
			map.put("title", madame.getTitle());
			listItem.add(map);
		}

		SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItem,
				R.layout.madame_item,
				new String[] { "image", "link", "title" }, new int[] {
						R.id.image, R.id.link, R.id.title });

		mSchedule.setViewBinder(new SimpleAdapter.ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view.getId() == R.id.image) {
				}
				return false;
			}
		});
		list.setAdapter(mSchedule);
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// retrieve the selected item
				TextView tv = (TextView) arg1.findViewById(R.id.link);
				
				String text = tv.getText().toString();
				DetailsFragment detail;
				detail = (DetailsFragment) getFragmentManager()
						.findFragmentByTag("details");
				if (detail == null) {
					detail = new DetailsFragment();
				}

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					getFragmentManager().beginTransaction()
							.replace(R.id.portraitLayout, detail, "details")
							.commit();
				}

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

					getFragmentManager().executePendingTransactions();
					detail.updateText(text);
				} else {
					setDetail(text);
				}

			}
		});
		return view;
	}

	@Override
	public void setDetail(String text) {
		getFragmentManager().executePendingTransactions();
		DetailsFragment details;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			details = (DetailsFragment) getFragmentManager().findFragmentByTag(
					"details");
		} else {
			details = (DetailsFragment) getFragmentManager().findFragmentByTag(
					"details_land");
		}
		
		if (details != null)
			details.updateText(text);
		else
			Log.e("ERROR", "Details fragment not found");
	}
}
