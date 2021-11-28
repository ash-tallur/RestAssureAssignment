import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import reposeModel.ReceivedUserResponse;
import requestModel.CreateUsersWithArrayRequestModel;
import requestModel.SendUserDetails;

import static io.restassured.RestAssured.given;


public class AppTest 
{
    //Test for post request
    @Test
    public void postUserDetails(){

        SendUserDetails sendData;
        sendData = new SendUserDetails(3,"joe","Joey","Tribbiani","joey.tribbabi@abc.com","12345","6578493986",1);

        ReceivedUserResponse postResponse;
        postResponse = given()
                        .contentType(ContentType.JSON)
                        .body(sendData).log().all()
                        .when()
                        .post("https://petstore.swagger.io/v2/user")
                        .as(ReceivedUserResponse.class);

        postResponse.printResponse();
        Assert.assertEquals(200,postResponse.getCode());
    }

    @Test
    public void sendMultipleUsers() {
        SendUserDetails sendData1 = new SendUserDetails(5, "joe", "Joey", "Tribbiani", "joey.tribbabi@abc.com", "12345", "6578493986", 1);
        SendUserDetails sendData2 = new SendUserDetails(6, "phoebo", "Phoebe", "Buffay", "phoebe.buffay@abc.com", "12345", "6578457886", 1);

        CreateUsersWithArrayRequestModel createUsersWithArrayRequestModel = new CreateUsersWithArrayRequestModel();
        createUsersWithArrayRequestModel.addaUserDetail(sendData1);
        createUsersWithArrayRequestModel.addaUserDetail(sendData2);

        ReceivedUserResponse postResponse =
                given().contentType(ContentType.JSON).
                        body(createUsersWithArrayRequestModel).log().all().
                        when().post("https://petstore.swagger.io/v2/user/createWithArray").
                        as(ReceivedUserResponse.class);

//        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
//        gson.toJson(createUsersWithArrayRequestModel);

        postResponse.printResponse();
        Assert.assertEquals(200, postResponse.getCode());
        Assert.assertEquals("unknown",postResponse.getType());
        //Assert.assertEquals("ok",postResponse.getMessage());
    }
}
