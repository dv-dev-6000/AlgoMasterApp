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

public class RecViewAdapter_Achievements extends RecyclerView.Adapter<RecViewAdapter_Achievements.MyViewHolder> {
    List<Achievement> AchievementItems;
    Context context;

    public RecViewAdapter_Achievements(List<Achievement> AchievementList, Context context) {
        this.AchievementItems = AchievementList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title;
        TextView tv_Desc;
        //ImageView iv_Image;
        ImageView iv_Check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title = itemView.findViewById(R.id.textView_Achievement_Title);
            tv_Desc = itemView.findViewById(R.id.textView_Achievment_Main);
            //iv_Image = itemView.findViewById(R.id.imageView_Achievement_Image);
            iv_Check = itemView.findViewById(R.id.imageView_Achievement_check);
        }
    }

    @NonNull
    @Override
    public RecViewAdapter_Achievements.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_feed_widget, parent, false);
        RecViewAdapter_Achievements.MyViewHolder holder = new RecViewAdapter_Achievements.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewAdapter_Achievements.MyViewHolder holder, int position) {

        holder.tv_Title.setText(AchievementItems.get(position).getTitle());
        holder.tv_Desc.setText(AchievementItems.get(position).getDesc());

        if (AchievementItems.get(position).getActive()){
            //activate checkmark
            holder.iv_Check.setVisibility(View.VISIBLE);
            //set image
            //String mDrawableName = AchievementItems.get(position).getImageID();
            //int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            //holder.iv_Image.setImageResource(resID);
        }
        else
        {
            //activate checkmark
            holder.iv_Check.setVisibility(View.INVISIBLE);
            //set image
            //String mDrawableName = "test";
            //int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            //holder.iv_Image.setImageResource(resID);
        }

    }

    @Override
    public int getItemCount() {
        return AchievementItems.size();
    }

}
