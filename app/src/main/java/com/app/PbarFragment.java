package com.app;
import android.app.*;
import android.view.*;
import android.os.*;
import android.widget.*;

public class PbarFragment extends Fragment 
{
	TextView txt;
	int index;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		index=getArguments().getInt("index");
		View v=inflater.inflate(R.layout.fragment_pbar, container, false);
		txt = v.findViewById(R.id.txt_song);
		txt.setText(MainActivity.list.get(index).getName());
		txt.setSelected(true);
		return v;
	}


}
