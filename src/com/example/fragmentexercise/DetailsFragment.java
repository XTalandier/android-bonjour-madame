package com.example.fragmentexercise;

import java.net.URL;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class DetailsFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.detail_layout, container, false);
		
		Button btnClose = (Button) view.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeMe();
			}
		});
				
		return view;
	}
	
	public void closeMe(){
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	
	public interface DetailsConfig{
		public void setDetail(String text);
	}
	
	public void updateText(String text)
	{
		ImageView mme = (ImageView) getView().findViewById(R.id.imgMadame);

		try {
		    URL thumb_u = new URL(text);
		    Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
		    mme.setImageDrawable(thumb_d);
		}
		catch (Exception e) {
			Log.e("OUTPUT", "+++> " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
