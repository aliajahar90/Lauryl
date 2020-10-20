package versatile.project.lauryl.profile.viewmodel;

import androidx.lifecycle.LiveData;

import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.profile.ProfileRepository;
import versatile.project.lauryl.profile.data.GetProfileResponse;

public class ProfileViewModel extends BaseViewModel {
   private ProfileRepository profileRepository;
    public ProfileViewModel() {
        this.profileRepository=ProfileRepository.getInstance();
    }

    public void getProfile(String accessToken){
        profileRepository.getProfileInformation(accessToken);
    }

    public LiveData<GetProfileResponse> profileFetchSuccessHandler(){
        return profileRepository.getGetProfileResponseLiveData();
    }
    public LiveData<Throwable> profileFetchErrorHandler(){
        return profileRepository.getThrowableMutableLiveData();
    }
}
