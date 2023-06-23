package com.app;

import android.app.*;
import android.os.*;
import androidx.recyclerview.widget.*;
import java.util.*;
import android.provider.*;
import android.database.*;
import android.widget.*;

public class MainActivity extends Activity 
{
	RecyclerView rec;
	LinearLayoutManager manager;
	MujikAdapter adap;
	public static ArrayList<Mujik> list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		rec = findViewById(R.id.recycler_view);
		manager = new LinearLayoutManager(this);
		list = new ArrayList<>();
		init();
		adap = new MujikAdapter(list, this);
		rec.setAdapter(adap);
		rec.setLayoutManager(manager);

    }
	public void init()
	{
		String[] proj = { MediaStore.Audio.Media._ID,
		MediaStore.Audio.Media.DISPLAY_NAME,
		MediaStore.Audio.Media.DATA,
		MediaStore.Audio.Media.ALBUM,
		MediaStore.Audio.Media.ALBUM_ARTIST,
		MediaStore.Audio.Media.ARTIST
		};
		Cursor audioCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
		if (audioCursor != null)
		{
            if (audioCursor.moveToFirst())
			{
                do{
					int audioIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
					Mujik m=new Mujik();
					m.setName(audioCursor.getString(audioIndex));
					m.setPath(audioCursor.getString(2));
					m.setAlbum(audioCursor.getString(3));
					m.setAlbumArtist(audioCursor.getString(4));
					m.setArtist(audioCursor.getString(5));
					list.add(m);
                }while(audioCursor.moveToNext());
            }
        }
        audioCursor.close();
		Toast.makeText(this,""+list.size(),Toast.LENGTH_LONG).show();
	}
}
