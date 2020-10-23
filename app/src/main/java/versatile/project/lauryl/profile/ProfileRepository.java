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


}
