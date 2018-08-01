package com.example.thiga.afrofitness_android.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.api.ApiService;
import com.example.thiga.afrofitness_android.api.ApiUrl;
import com.example.thiga.afrofitness_android.helper.SessionAdapter;
import com.example.thiga.afrofitness_android.helper.SharedPrefManager;
import com.example.thiga.afrofitness_android.models.Session;
import com.example.thiga.afrofitness_android.models.Sessions;
import com.example.thiga.afrofitness_android.models.User;
import com.example.thiga.afrofitness_android.models.WorkoutSessionResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SessionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SessionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionsFragment extends Fragment {
    private RecyclerView recyclerViewSessions;
    private RecyclerView.Adapter adapter;
    private Button buttonAddSession;
    private Context mContext;
    private User user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SessionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SessionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionsFragment newInstance(String param1, String param2) {
        SessionsFragment fragment = new SessionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sessions, container, false);

        buttonAddSession = view.findViewById(R.id.button_add_workout);
        buttonAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext = getContext();
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptsView = layoutInflater.inflate(R.layout.dialog_add_session, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setView(promptsView);

                final EditText editTextExerciseType = promptsView.findViewById(R.id.add_exercise_type);
                final EditText editTextDate = promptsView.findViewById(R.id.add_date);
                final EditText editTextLocation = promptsView.findViewById(R.id.add_location);
                final EditText editTextReps = promptsView.findViewById(R.id.add_reps);
                final EditText editTextSets = promptsView.findViewById(R.id.add_sets);

                alertDialogBuilder
                        .setPositiveButton(getString(R.string.add_workout), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String exerciseType = editTextExerciseType.getText().toString().trim();
                                String date = editTextDate.getText().toString().trim();
                                String location = editTextLocation.getText().toString().trim();
                                String reps = editTextReps.getText().toString().trim();
                                String sets = editTextSets.getText().toString().trim();
                                int userId = SharedPrefManager.getInstance(getActivity()).getUser().getId();
                                addSession(exerciseType,date,location,reps,sets,userId);
                            }
                        })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.workout_sessions);
        recyclerViewSessions = (RecyclerView) view.findViewById(R.id.recycler_view_sessions);
        recyclerViewSessions.setHasFixedSize(true);
        recyclerViewSessions.setLayoutManager(new LinearLayoutManager(getActivity()));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);


        Call<Sessions> call = service.getSessions(SharedPrefManager.getInstance(getActivity()).getUser().getId());

        call.enqueue(new Callback<Sessions>() {
            @Override
            public void onResponse(Call<Sessions> call, Response<Sessions> response) {
                adapter = new SessionAdapter(response.body().getSessions(), getActivity());
                recyclerViewSessions.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Sessions> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addSession(String exercise,String date,String location,String reps,String sets,int user_id){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(getString(R.string.adding_workout_session));
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);


        Call<WorkoutSessionResult> call = service.addSession(
                exercise,
                date,
                location,
                reps,
                sets,
                user_id
        );

        call.enqueue(new Callback<WorkoutSessionResult>() {
            @Override
            public void onResponse(Call<WorkoutSessionResult> call, Response<WorkoutSessionResult> response) {
                progressDialog.dismiss();
                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<WorkoutSessionResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
