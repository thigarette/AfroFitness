package com.example.thiga.afrofitness_android.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.api.ApiService;
import com.example.thiga.afrofitness_android.api.ApiUrl;
import com.example.thiga.afrofitness_android.helper.SharedPrefManager;
import com.example.thiga.afrofitness_android.models.User;
import com.example.thiga.afrofitness_android.ui.UpdateProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private TextView textViewFirstName,textViewLastName,textViewEmail,textViewPrefLocation,textViewAge,
            textViewGender,textViewWeight,textViewTargetWeight;
    private Button buttonEditProfile;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        buttonEditProfile = v.findViewById(R.id.button_edit_profile);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.profile);

        textViewFirstName = view.findViewById(R.id.text_view_first_name);
        textViewLastName = view.findViewById(R.id.text_view_last_name);
        textViewEmail = view.findViewById(R.id.text_view_email);
        textViewPrefLocation = view.findViewById(R.id.text_view_pref_location);
        textViewAge = view.findViewById(R.id.text_view_age);
        textViewGender = view.findViewById(R.id.text_view_gender);
        textViewWeight = view.findViewById(R.id.text_view_weight);
        textViewTargetWeight = view.findViewById(R.id.text_view_target_weight);

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        textViewFirstName.setText(String.format("%1$s : %2$s",getResources().getString(R.string.first_name),user.getFirstName()));
        textViewLastName.setText(String.format("%1$s : %2$s",getResources().getString(R.string.last_name),user.getLastName()));
        textViewEmail.setText(String.format("%1$s : %2$s",getResources().getString(R.string.email),user.getEmail()));
        textViewPrefLocation.setText(String.format("%1$s : %2$s",getResources().getString(R.string.preferred_workout_location),user.getPreferredWorkout()));
        textViewAge.setText(String.format("%1$s : %2$s",getResources().getString(R.string.age),String.valueOf(user.getAge())));
        textViewGender.setText(String.format("%1$s : %2$s",getResources().getString(R.string.gender),user.getGender()));
        textViewWeight.setText(String.format("%1$s : %2$s",getResources().getString(R.string.weight_kg),String.valueOf(user.getWeight())));
        textViewTargetWeight.setText(String.format("%1$s : %2$s",getResources().getString(R.string.target_weight_kg),String.valueOf(user.getTargetWeight())));
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
