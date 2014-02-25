package com.example.fragmentexercise;

import com.example.camera.SurfaceCamera;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		/*
		 * if (getResources().getConfiguration().orientation ==
		 * Configuration.ORIENTATION_PORTRAIT) {
		 * 
		 * ListFragment listFrag; listFrag = (ListFragment)
		 * getFragmentManager().findFragmentByTag( "init"); if (listFrag ==
		 * null) { listFrag = new ListFragment(); }
		 * getFragmentManager().beginTransaction() .replace(R.id.portraitLayout,
		 * listFrag, "init").commit();
		 * 
		 * }
		 */
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ListFragment listFrag;
			listFrag = (ListFragment) getFragmentManager().findFragmentByTag(
					"init");
			if (listFrag == null) {
				listFrag = new ListFragment();
			}
			getFragmentManager().beginTransaction()
					.replace(R.id.portraitLayout, listFrag, "init").commit();
		} else {

			ListFragment listFrag = (ListFragment) getFragmentManager()
					.findFragmentByTag("list");
			if (listFrag == null) {
				listFrag = new ListFragment();
			}

			DetailsFragment detail = (DetailsFragment) getFragmentManager()
					.findFragmentByTag("details_land");
			if (detail == null) {
				detail = new DetailsFragment();
			}

			getFragmentManager().beginTransaction()
					.replace(R.id.landLayoutlist, listFrag, "list").commit();
			getFragmentManager().beginTransaction()
					.replace(R.id.landLayoutdetail, detail, "details_land")
					.commit();
			getFragmentManager().executePendingTransactions();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_snap:
			openCamera();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void openCamera() {
		Intent intent = new Intent(this, SurfaceCamera.class);
		startActivity(intent);
	}

}
