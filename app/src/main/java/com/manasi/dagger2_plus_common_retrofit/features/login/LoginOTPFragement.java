package com.manasi.dagger2_plus_common_retrofit.features.login;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.manasi.dagger2_plus_common_retrofit.BuildConfig;
import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.custom.RippleDrawableHelper;
import com.manasi.dagger2_plus_common_retrofit.features.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginOTPFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginOTPFragement extends BaseFragment implements SmsListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tv_resend_otp)
    TextView tvResendOtp;
    Unbinder unbinder;
    @BindView(R.id.et_otp)
    TextInputEditText etOtp;

    @Inject
    LoginPresenter<LoginContract.View> loginPresenter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String mobileNumber;

    private OnFragmentInteractionListener mListener;

    public LoginOTPFragement() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginMbNumFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginOTPFragement newInstance(String param1, String param2) {
        LoginOTPFragement fragment = new LoginOTPFragement();
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
            mobileNumber = getArguments().getString("mobile_number");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_otp, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivityComponent().inject(this);

        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
            etOtp.setText("0000");
        }


        tvResendOtp.setBackground(RippleDrawableHelper.createRippleDrawable(tvResendOtp, getResources().getColor(R.color.grey_ripple)));



        SmsReceiver.bindListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String type) {
        if (mListener != null) {
            mListener.onFragmentInteraction(type, etOtp.getText().toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnLogin, R.id.tv_resend_otp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (loginPresenter.isOTPValid(etOtp.getText().toString())) {
                   loginPresenter.callInitialiseAPI();
                    // onButtonPressed("otp");
                }
                break;
            case R.id.tv_resend_otp:
                loginPresenter.resentOTP_API(mobileNumber);

                //Toast.makeText(getActivity(), "Resend OTP", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void messageReceived(String messageText) {
        if(etOtp!=null) {
            etOtp.setText(messageText);
        }
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
        void onFragmentInteraction(String type, String value);
    }
}
