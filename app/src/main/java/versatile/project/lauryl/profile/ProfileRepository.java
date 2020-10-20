package versatile.project.lauryl.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.profile.data.GetProfileResponse;

public class ProfileRepository extends LaurylRepository {
    private static ProfileRepository single_instance = null;
    private ProfileRepository() {
    }

    // static method to create instance of Singleton class
    public static ProfileRepository getInstance() {
        if (single_instance == null)
            single_instance = new ProfileRepository();

        return single_instance;
    }
    private MutableLiveData<GetProfileResponse> getProfileResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();

    public void getProfileInformation(String accessToken) {
        getApiServices().getMyProfile(accessToken).enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    GetProfileResponse getProfileResponse=response.body();
                    getProfileResponseLiveData.postValue(getProfileResponse);
                }else {
                    getProfileResponseLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
               throwableMutableLiveData.postValue(t);
            }
        });
    }

    public MutableLiveData<GetProfileResponse> getGetProfileResponseLiveData() {
        return getProfileResponseLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
