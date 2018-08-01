package com.example.thiga.afrofitness_android.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.api.ApiService;
import com.example.thiga.afrofitness_android.api.ApiUrl;
import com.example.thiga.afrofitness_android.helper.SharedPrefManager;
import com.example.thiga.afrofitness_android.models.Result;
import com.example.thiga.afrofitness_android.models.User;
import com.example.thiga.afrofitness_android.ui.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProfileFragment extends Fragment {
    private Button buttonUpdate;
    private EditText editTextFirstName,editTextLastName,editTextEmail,editTextPrefLocation,editTextAge,
            editTextGender,editTextWeight,editTextTargetWeight;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProfileFragment newInstance(String param1, String param2) {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
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
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.edit_profile);

        editTextFirstName = view.findViewById(R.id.update_first_name);
        editTextLastName = view.findViewById(R.id.update_last_name);
        editTextEmail = view.findViewById(R.id.update_email);
        editTextPrefLocation = view.findViewById(R.id.update_pref_location);
        editTextAge = view.findViewById(R.id.update_age);
        editTextGender = view.findViewById(R.id.update_gender);
        editTextWeight = view.findViewById(R.id.update_weight);
        editTextTargetWeight = view.findViewById(R.id.update_target_weight);

        buttonUpdate = view.findViewById(R.id.button_update_profile);
        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        editTextFirstName.setText(user.getFirstName());
        editTextLastName.setText(user.getLastName());
        editTextEmail.setText(user.getEmail());
        editTextPrefLocation.setText(user.getPreferredWorkout());
        editTextAge.setText(String.valueOf(user.getAge()));
        editTextGender.setText(user.getGender());
        editTextWeight.setText(String.valueOf(user.getWeight()));
        editTextTargetWeight.setText(String.valueOf(user.getTargetWeight()));

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==buttonUpdate){
                    updateUser();
                }
            }
        });
    }

    private void updateUser(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(String.format("%s...",getString(R.string.updating)));
        progressDialog.show();

        //final RadioButton radioSex = (RadioButton) getActivity().findViewById(radioGender.getCheckedRadioButtonId());

        String fname = editTextFirstName.getText().toString().trim();
        String lname = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String preflocation = editTextPrefLocation.getText().toString().trim();
        int age = Integer.parseInt(editTextAge.getText().toString().trim());
        String gender = editTextGender.getText().toString();
        int weight = Integer.parseInt(editTextWeight.getText().toString().trim());
        int targetweight = Integer.parseInt(editTextTargetWeight.getText().toString().trim());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        User user = new User(SharedPrefManager.getInstance(getActivity()).getUser().getId(), fname,lname, email,preflocation,age,gender,weight,targetweight);

        Call<Result> call = service.updateUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPreferredWorkout(),
                user.getAge(),
                user.getGender(),
                user.getWeight(),
                user.getTargetWeight()

        );

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    SharedPrefManager.getInstance(getActivity()).login(response.body().getUser());
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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
