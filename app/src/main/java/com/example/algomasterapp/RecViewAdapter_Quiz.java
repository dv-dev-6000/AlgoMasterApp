package com.example.algomasterapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecViewAdapter_Quiz extends RecyclerView.Adapter<RecViewAdapter_Quiz.MyViewHolder> {

    private RadioGroupClickListener radioGroupClickListener;
    List<QuizItem> questions;
    Context context;

    public interface RadioGroupClickListener {
        void onRadioGroupClick(int position, int checkedRadioButtonId);
    }

    public void setRadioGroupClickListener(RadioGroupClickListener listener) {
        this.radioGroupClickListener = listener;
    }

    public RecViewAdapter_Quiz(List<QuizItem> questionList, Context context) {
        this.questions = questionList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Title;
        ImageView iv_Image;
        TextView tv_Question;
        TextView tv_op1;
        TextView tv_op2;
        TextView tv_op3;
        TextView tv_op4;
        RadioGroup rad_Options;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_Title = itemView.findViewById(R.id.textView_QuizQuestionTitle);
            iv_Image = itemView.findViewById(R.id.imageView_QuizQuestion);
            tv_Question = itemView.findViewById(R.id.textView_QuizQuestionMain);
            tv_op1 = itemView.findViewById(R.id.textViewA);
            tv_op2 = itemView.findViewById(R.id.textViewB);
            tv_op3 = itemView.findViewById(R.id.textViewC);
            tv_op4 = itemView.findViewById(R.id.textViewD);
            rad_Options = itemView.findViewById(R.id.radioGroup_Quiz);
        }
    }

    @NonNull
    @Override
    public RecViewAdapter_Quiz.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_feed_widget, parent, false);
        RecViewAdapter_Quiz.MyViewHolder holder = new RecViewAdapter_Quiz.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewAdapter_Quiz.MyViewHolder holder, int position) {


        holder.tv_Title.setText(questions.get(position).getTitle());
        holder.tv_Question.setText(questions.get(position).getQuestion());
        holder.tv_op1.setText(questions.get(position).getOptions()[0]);
        holder.tv_op2.setText(questions.get(position).getOptions()[1]);
        holder.tv_op3.setText(questions.get(position).getOptions()[2]);
        holder.tv_op4.setText(questions.get(position).getOptions()[3]);
        if (!questions.get(position).getImageFile().equals("null")){
            String mDrawableName = questions.get(position).getImageFile();
            int resID = context.getResources().getIdentifier(mDrawableName, "drawable", context.getPackageName());
            holder.iv_Image.setImageResource(resID);
        }

        holder.rad_Options.setOnCheckedChangeListener((group, checkedId) -> {
            if (radioGroupClickListener != null){
                radioGroupClickListener.onRadioGroupClick(position, checkedId);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
