package com.example.volleyimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private Context context;
    private ArrayList<Item> itemArrayList;
    private static OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position, byte[] img);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public MyAdapter(Context context, ArrayList<Item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_raw, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Item item = itemArrayList.get(position);

        String uri = item.getImageUrl();
        String title = item.getTitle();
        String like = item.getLike();

        try {
            Picasso.get().load(uri).fit().centerInside().into(holder.img);
        }
        catch (Exception e) {
            holder.img.setImageResource(R.mipmap.wait);
        }

        holder.titleTv.setText(title);
        holder.likeTv.setText(like);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


    static class MyHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView titleTv, likeTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.iV);
            titleTv = itemView.findViewById(R.id.titleTv);
            likeTv = itemView.findViewById(R.id.likeTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Drawable drawable = img.getDrawable();
                            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                            ByteArrayOutputStream bOus = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bOus);
                            byte[] img = bOus.toByteArray();
                            mItemClickListener.onItemClick(position, img);
                        }
                    }
                }
            });

        }
    }
}
