package com.app;
import android.app.*;
import android.os.*;
import android.media.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.io.*;
import android.content.*;

public class PlayMujikActivity extends Activity
{
	String path,name;
	MediaPlayer mp;
	ImageView img;
	TextView txt,stime,etime;
	int index;
	SeekBar sb;
	Handler mHandler;
	int mCurrentPosition;
	TextView tAlbum,tAlbumArtist,tArtist;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_mujik);
		
		img = findViewById(R.id.img_play_pause);
		txt = findViewById(R.id.txt_song);
		sb = findViewById(R.id.seekBar);
		stime = findViewById(R.id.txt_start_time);
		etime = findViewById(R.id.txt_end_time);
		tAlbum = findViewById(R.id.txt_album);
		tAlbumArtist = findViewById(R.id.txt_album_artist);
		tArtist = findViewById(R.id.txt_artist);
		index = getIntent().getIntExtra("index", -1);
		name = MainActivity.list.get(index).getName();
		PbarFragment fr=new PbarFragment();
		Bundle bb=new Bundle();
		bb.putInt("index", index);
		fr.setArguments(bb);
		getFragmentManager().beginTransaction().replace(R.id.container, fr).commit();
		mp = new MediaPlayer();  
        try
		{  
            mp.setDataSource(MainActivity.list.get(index).getPath());
            mp.prepare();  
            mp.start();
			tAlbum.append(MainActivity.list.get(index).getAlbum());
			//tAlbumArtist.append(MainActivity.list.get(index).getAlbumArtist());
			tArtist.append(MainActivity.list.get(index).getArtist());

			sb.setMax(mp.getDuration() / 1000);
			sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar)
					{

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar)
					{

					}

					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
					{                
						if (mp != null && fromUser)
						{
							mp.seekTo(progress * 1000);
							mCurrentPosition = mp.getCurrentPosition() / 1000;
						}
					}
				});
			long min1=(mp.getDuration() / 1000) / 60;
			long sec1=(mp.getDuration() / 1000) % 60;
			
			etime.setText(convertDate(min1) + ":" + convertDate(sec1));

			Thread t=new Thread(new Runnable(){

					@Override
					public void run()
					{
						mCurrentPosition = mp.getCurrentPosition() / 1000;
						try
						{
							int x=0;
							while (x < mp.getDuration())
							{
								if (mp != null)
								{

									PlayMujikActivity.this.runOnUiThread(new Runnable(){

											@Override
											public void run()
											{

												mCurrentPosition = mp.getCurrentPosition();
												long min=(mCurrentPosition / 1000) / 60;
												long sec=(mCurrentPosition / 1000) % 60;
												sb.setProgress(mp.getCurrentPosition() / 1000);	
												stime.setText(convertDate(min) + ":" + convertDate(sec));
											}


										});

								}
								Thread.sleep(1000);


							}
						}
						catch (InterruptedException e)
						{

						}
					}


				});
			t.start();
		}
		catch (IOException e)
		{

		}
	}
	public void playpause(View v)
	{
		if (mp.isPlaying())
		{
			mp.pause();
			img.setImageResource(R.drawable.ic_play);
		}
		else
		{
			mp.start();
			img.setImageResource(R.drawable.ic_pause);
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mp.pause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mp.start();
	}
	
	public void prev(View v)
	{
		Intent ii=new Intent(this, PlayMujikActivity.class);
		int i=index;
		i--;
		if (i >= 0){
			ii.putExtra("index", i);
			startActivity(ii);
			finish();
			}
		else
			Toast.makeText(this, "reached at starting", Toast.LENGTH_LONG).show();
			
	}
	public void next(View v)
	{
		Intent ii=new Intent(this, PlayMujikActivity.class);
		int i=index;
		i++;
		if (i < MainActivity.list.size()){
			ii.putExtra("index", i);
			startActivity(ii);
			finish();
		}
		else
			Toast.makeText(this, "reached at end", Toast.LENGTH_LONG).show();
		
	}
	public String convertDate(long input) {
		if (input >= 10) {
			return String.valueOf(input);
		} else {
			return "0" + String.valueOf(input);
		}
	}
}
