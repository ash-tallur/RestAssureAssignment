package requestModel;

import java.util.ArrayList;
import java.util.List;

public class CreateUsersWithArrayRequestModel {
    private List<SendUserDetails> userDetails = new ArrayList<>();

    public void addaUserDetail(SendUserDetails aUserDetail) {
        this.getUserDetails().add(aUserDetail);
    }

    public List<SendUserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<SendUserDetails> userDetails) {
        this.userDetails = userDetails;
    }
}

