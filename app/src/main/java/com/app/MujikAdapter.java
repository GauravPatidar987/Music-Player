package com.app;
import androidx.recyclerview.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.provider.*;

public class MujikAdapter extends RecyclerView.Adapter<MujikAdapter.Mvholder>
{
	ArrayList<Mujik> list;
	Context context;

	public MujikAdapter(ArrayList<Mujik> list, Context context)
	{
		this.list = list;
		this.context = context;
	}

	@Override
	public MujikAdapter.Mvholder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View v= LayoutInflater.from(p1.getContext()).inflate(R.layout.mujik_item, null);
		return new Mvholder(v);
	}

	@Override
	public void onBindViewHolder(MujikAdapter.Mvholder p1, int p2)
	{
		//Bitmap image = coverpicture(list.get(p2).getPath());
		//ThumbnailUtils.createAudioThumbnail(list.get(p2).getPath(), MediaStore.Images.Thumbnails.MICRO_KIND);
		p1.txt.setText(list.get(p2).getName());
		//p1.img.setImageBitmap(image);
	}

	@Override
	public int getItemCount()
	{

		return list.size();
	}
	public  Bitmap coverpicture(String path)
	{

      return  ThumbnailUtils.createAudioThumbnail(path,MediaStore.Images.Thumbnails.MICRO_KIND);

	} 
	class Mvholder extends RecyclerView.ViewHolder
	{
		ImageView img;
		TextView txt;
		public Mvholder(View v)
		{
			super(v);
			img = v.findViewById(R.id.img_mujik);
			txt = v.findViewById(R.id.text_mujik);
			itemView.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						Intent i=new Intent(context,PlayMujikActivity.class);
						
						String path=list.get(getAdapterPosition()).getPath();
						i.putExtra("path",path);
						i.putExtra("index", getAdapterPosition());
						context.startActivity(i);
					}
					
				
			});
		}
	}
}
