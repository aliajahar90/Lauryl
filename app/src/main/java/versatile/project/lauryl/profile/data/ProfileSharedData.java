package versatile.project.lauryl.profile.data;

import java.io.Serializable;

public class ProfileSharedData implements Serializable {
    public GetProfileResponse.ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(GetProfileResponse.ProfileData profileData) {
        this.profileData = profileData;
    }

    private GetProfileResponse.ProfileData profileData;

}
