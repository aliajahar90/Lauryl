package versatile.project.lauryl.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import versatile.project.lauryl.R;
import versatile.project.lauryl.application.MyApplication;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.databinding.FragmentProfileBinding;
import versatile.project.lauryl.dialogs.ErrorBottomSheetDialog;
import versatile.project.lauryl.payment.PaymentErrorFragment;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.profile.data.GetProfileResponse;
import versatile.project.lauryl.profile.data.ProfileSharedData;
import versatile.project.lauryl.profile.viewmodel.ProfileViewModel;
import versatile.project.lauryl.utils.AllConstants;

public class ProfileFragment extends BaseBinding<ProfileViewModel, FragmentProfileBinding> {

    public static final String TAG =ProfileFragment.class.getName() ;
    private FragmentProfileBinding profileBinding;
    private ProfileViewModel profileViewModel;
    private ProfileSharedData profileSharedData;
    private boolean isErrorDialgActive =false;
    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        try {
            profileFragment.profileSharedData = new Gson().fromJson((String) profileFragment.getArguments().get(AllConstants.Profile.PROFILE_DATA), ProfileSharedData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        return profileFragment;
    }
    @Override
    protected void initializeViewModel() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return profileBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoader();
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();
        if (myApplication != null) {
            profileViewModel.getProfile(myApplication.getAccessToken());
        }
        setupGetProfileObserver();
    }

    private void setupGetProfileObserver() {
        profileViewModel.profileFetchSuccessHandler().observe(this, getProfileResponse -> {
            hideLoading();
            if (getProfileResponse != null) {
                profileUiController(getProfileResponse.getProfileData());
            }

        });
        profileViewModel.profileFetchErrorHandler().observe(this, throwable -> {
            hideLoading();

            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("logoId",-1);
                jsonObject.put("tile","Error fetching profile");
                jsonObject.put("msg","We are unable to fetch your profile at the moment. Please try again after sometime");
                jsonObject.put("cancelTxt","Cancel");
                jsonObject.put("proceedTxt","");

                ErrorBottomSheetDialog errorBottomSheetDialog=ErrorBottomSheetDialog.newInstance(new Gson().toJson(jsonObject),new ErrorBottomSheetDialog.DialogClickListener() {
                    @Override
                    public void dialogProceed() {
                        isErrorDialgActive=false;
                    }

                    @Override
                    public void dialogCancelled() {
                        isErrorDialgActive=false;
                    }
                });
                if(!isErrorDialgActive) {
                    isErrorDialgActive=true;
                    errorBottomSheetDialog.show(getFragmentManager(), ErrorBottomSheetDialog.TAG);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    private void profileUiController(GetProfileResponse.ProfileData profileData) {
        profileBinding.txtName.setText(profileData.getFirstName() + " "+ profileData.getLastName());
        profileBinding.txtNumber.setText(profileData.getPhoneNumber());
    }

    private void showLoader() {

    }

    private void hideLoading() {

    }
}
