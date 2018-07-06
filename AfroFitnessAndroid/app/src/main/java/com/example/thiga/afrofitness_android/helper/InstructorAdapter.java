package com.example.thiga.afrofitness_android.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.models.Instructor;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder>{
    private List<Instructor> instructors;
    private Context mContext;

    public InstructorAdapter(List<Instructor> instructors, Context mContext) {
        this.instructors = instructors;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_instructors, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InstructorAdapter.ViewHolder holder, int position) {
        Instructor instructor = instructors.get(position);
        holder.textViewName.setText("Name: "+instructor.getName());
        holder.textViewGender.setText("Gender: "+instructor.getGender());
        holder.textViewPhoneNumber.setText("Phone Number: "+instructor.getPhoneNumber());
        holder.textViewEmail.setText("Email: "+instructor.getEmail());
    }


    @Override
    public int getItemCount() {
        return instructors.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewGender;
        public TextView textViewPhoneNumber;
        public TextView textViewEmail;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.text_view_name);
            textViewGender = (TextView) itemView.findViewById(R.id.text_view_gender);
            textViewPhoneNumber = (TextView) itemView.findViewById(R.id.text_view_phone_number);
            textViewEmail = (TextView) itemView.findViewById(R.id.text_view_email);
        }
    }

}
