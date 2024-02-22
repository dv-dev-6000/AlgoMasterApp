package com.example.algomasterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecViewAdapter_Quiz extends RecyclerView.Adapter<RecViewAdapter_Quiz.MyViewHolder> {

    List<QuizItem> questions;
    Context context;

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
        Button bt_submit;

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
            bt_submit = itemView.findViewById(R.id.button_SubmitQuiz);
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

        float id = questions.get(position).getId();

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

        holder.bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "ButtonPress", Toast.LENGTH_SHORT);
                toast.show();

                questions.remove(holder.getAdapterPosition());

                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), questions.size()-holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
