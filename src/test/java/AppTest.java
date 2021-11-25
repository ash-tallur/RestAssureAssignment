import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;


public class AppTest 
{
    //Test for post request
    @Test
    public void sendUserDetails(){

        SendUserDetails sendData = new SendUserDetails();
        sendData.setId(3);
        sendData.setUsername("joe");
        sendData.setFirstName("Joey");
        sendData.setLastName("Tribbiani");
        sendData.setEmail("joey.tribbani@abc.com");
        sendData.setPassword("12345");
        sendData.setPhone("6578493986");
        sendData.setUserStatus(1);

        //created a java object and sending it to the post request and checking the status code of 200
        ReceivedUserResponse postResponse =
                given().contentType(ContentType.JSON).
                        body(sendData).log().body().
                        when().post("https://petstore.swagger.io/v2/user").
                        as(ReceivedUserResponse.class);

        postResponse.printResponse();
    }

    @Test
    public void sendMultipleUsers(){

        //sending two user details
        SendUserDetails sendData1 = new SendUserDetails();
        sendData1.setId(3);
        sendData1.setUsername("joe");
        sendData1.setFirstName("Joey");
        sendData1.setLastName("Tribbiani");
        sendData1.setEmail("joey.tribbani@abc.com");
        sendData1.setPassword("12345");
        sendData1.setPhone("6578493986");
        sendData1.setUserStatus(1);

        SendUserDetails sendData2 = new SendUserDetails();
        sendData2.setId(4);
        sendData2.setUsername("phoebo");
        sendData2.setFirstName("Phoebe");
        sendData2.setLastName("Buffay");
        sendData2.setEmail("phoebe.buffay@abc.com");
        sendData2.setPassword("12345");
        sendData2.setPhone("6578457886");
        sendData2.setUserStatus(1);

        //adding both the user details to an array
        List<SendUserDetails> sendJsonArray = new ArrayList<>();

        sendJsonArray.add(sendData1);
        sendJsonArray.add(sendData2);

        ReceivedUserResponse postResponse =
                given().contentType(ContentType.JSON).
                        body(sendJsonArray).log().body().
                        when().post("https://petstore.swagger.io/v2/user/createWithArray").
                        as(ReceivedUserResponse.class);

        //printing the response
        postResponse.printResponse();
        //Verifyign the response code
        Assert.assertEquals(200,postResponse.getCode());

    }






}
