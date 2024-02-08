package com.example.algomasterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecViewAdapter_RevFeed extends RecyclerView.Adapter<RecViewAdapter_RevFeed.MyViewHolder> {

    List<RevisionItem> revisionItems;
    Context context;

    public RecViewAdapter_RevFeed(List<RevisionItem> revItemList, Context context) {
        this.revisionItems = revItemList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title;
        TextView tv_Main;
        ImageView iv_Image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title = itemView.findViewById(R.id.textView_RevFeedImage_Title);
            tv_Main = itemView.findViewById(R.id.textView_RevFeedImage_Main);
            iv_Image = itemView.findViewById(R.id.RevFeed_Image);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.revision_feed_textimage, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_Title.setText(revisionItems.get(position).getTitle());
        holder.tv_Main.setText(revisionItems.get(position).getMain());

        if (revisionItems.get(position).getHasImage()){
            String mDrawableName = revisionItems.get(position).getImageID();
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            holder.iv_Image.setImageResource(resID);
        }

    }

    @Override
    public int getItemCount() {
        return revisionItems.size();
    }
}
