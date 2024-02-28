package com.example.algomasterapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecViewAdapter_Lessons extends RecyclerView.Adapter<RecViewAdapter_Lessons.MyViewHolder> {

    List<LessonItem> lessons;
    Context context;

    public RecViewAdapter_Lessons(List<LessonItem> lessons, Context context) {
        this.lessons = lessons;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title;
        TextView tv_Desc;
        ImageView iv_Check;
        ConstraintLayout parentLayout;
        TextView tv_Perfect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title = itemView.findViewById(R.id.textView_lessonheading);
            tv_Desc = itemView.findViewById(R.id.textView_lessonDesc);
            iv_Check = itemView.findViewById(R.id.imageView_Lesson_Check);
            parentLayout = itemView.findViewById(R.id.ConstraintLayout_Lesson);
            tv_Perfect = itemView.findViewById(R.id.textView_perfect);
        }
    }

    @NonNull
    @Override
    public RecViewAdapter_Lessons.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_feed_widget, parent, false);
        RecViewAdapter_Lessons.MyViewHolder holder = new RecViewAdapter_Lessons.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewAdapter_Lessons.MyViewHolder holder, int position) {

        int id = lessons.get(position).getId();
        holder.tv_Title.setText(lessons.get(position).getTitle());
        holder.tv_Desc.setText(lessons.get(position).getDescription());
        if (lessons.get(position).getIsComplete()){
            holder.iv_Check.setVisibility(View.VISIBLE);
        }
        else{
            holder.iv_Check.setVisibility(View.INVISIBLE);
        }
        if (lessons.get(position).isPerfect()){
            holder.tv_Perfect.setVisibility(View.VISIBLE);
        }
        else{
            holder.tv_Perfect.setVisibility(View.INVISIBLE);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Content.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }
}
