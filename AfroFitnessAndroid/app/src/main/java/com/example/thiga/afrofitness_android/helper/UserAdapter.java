package com.example.thiga.afrofitness_android.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> user;
    private Context mContext;

    public UserAdapter(List<User> user, Context mContext) {
        this.user = user;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_profile, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User users = user.get(position);
        holder.textViewFirstName.setText(users.getFirstName());
        holder.textViewLastName.setText(users.getLastName());
        holder.textViewEmail.setText(users.getEmail());
        holder.textViewPrefLocation.setText(users.getPreferredWorkout());
        holder.textViewAge.setText(String.valueOf(users.getAge()));
        holder.textViewGender.setText(users.getGender());
        holder.textViewWeight.setText(String.valueOf(users.getWeight()));
        holder.textViewTargetWeight.setText(String.valueOf(users.getTargetWeight()));
    }

    @Override
    public int getItemCount() {
        return user.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewFirstName;
        public TextView textViewLastName;
        public TextView textViewEmail;
        public TextView textViewPrefLocation;
        public TextView textViewAge;
        public TextView textViewGender;
        public TextView textViewWeight;
        public TextView textViewTargetWeight;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewFirstName = itemView.findViewById(R.id.text_view_first_name);
            textViewLastName = itemView.findViewById(R.id.text_view_last_name);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            textViewPrefLocation = itemView.findViewById(R.id.text_view_pref_location);
            textViewAge = itemView.findViewById(R.id.text_view_age);
            textViewGender = itemView.findViewById(R.id.text_view_gender);
            textViewWeight = itemView.findViewById(R.id.text_view_weight);
            textViewTargetWeight = itemView.findViewById(R.id.text_view_target_weight);
        }
    }
}
