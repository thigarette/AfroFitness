package com.example.thiga.afrofitness_android.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.models.Session;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private List<Session> sessions;
    private Context mContext;

    public SessionAdapter(List<Session> sessions, Context mContext) {
        this.sessions = sessions;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_sessions, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SessionAdapter.ViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.textViewExercise.setText("Exercise: "+session.getExerciseType());
        holder.textViewDate.setText("Date: "+session.getDate());
        holder.textViewLocation.setText("Location: "+session.getLocationName());
        holder.textViewReps.setText(String.valueOf(session.getNumberOfReps())+" reps");
        holder.textViewSets.setText(String.valueOf(session.getNumberOfSets())+" sets");
    }


    @Override
    public int getItemCount() {
        return sessions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewExercise;
        public TextView textViewDate;
        public TextView textViewLocation;
        public TextView textViewReps;
        public TextView textViewSets;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewExercise = (TextView) itemView.findViewById(R.id.text_view_exercise_type);
            textViewDate = (TextView) itemView.findViewById(R.id.text_view_date);
            textViewLocation = (TextView) itemView.findViewById(R.id.text_view_location_name);
            textViewReps = (TextView) itemView.findViewById(R.id.text_view_no_of_reps);
            textViewSets = (TextView) itemView.findViewById(R.id.text_view_no_of_sets);
        }
    }
}
