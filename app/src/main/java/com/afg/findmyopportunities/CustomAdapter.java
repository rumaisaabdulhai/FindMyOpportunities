package com.afg.findmyopportunities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<Opportunity> opportunityArrayList;
    Context context;

    public CustomAdapter(ArrayList<Opportunity> opportunityArrayList, Context context) {
        this.opportunityArrayList = opportunityArrayList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView img_thumb;
        TextView tv_country;
        TextView tv_headlines;
        ImageView img_more;

        MyViewHolder(View itemview)
        {
            super(itemview);

            this.img_thumb=(ImageView)itemview.findViewById(R.id.img_thumb);
            this.tv_country=(TextView) itemview.findViewById(R.id.tv_country);
            this.tv_headlines=(TextView) itemview.findViewById(R.id.tv_headlines);
            this.img_more=(ImageView)itemview.findViewById(R.id.img_more);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.opportunity, parent, false);
        MyViewHolder myViewHolder= new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ImageView img_thumb = holder.img_thumb;
        TextView tv_country = holder.tv_country;
        TextView tv_headlines = holder.tv_headlines;
        ImageView getImg_thumb = holder.img_more;

        tv_country.setText(opportunityArrayList.get(position).country+"");
        tv_headlines.setText(opportunityArrayList.get(position).headlines+"");

        //======== set image source from url =======
        try {
            URL image_url = new URL(opportunityArrayList.get(position).thumb+"");
            Log.d("image url", image_url+"");
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(image_url).apply(options).into(img_thumb);
        }
        catch(Exception e)
        {
            Log.d("error", e+"");
        }
    }

    @Override
    public int getItemCount() {
        return opportunityArrayList.size();
    }
}
