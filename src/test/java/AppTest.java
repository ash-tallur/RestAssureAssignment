import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import org.junit.Test;
import reposeModel.ReceivedUserResponse;
import requestModel.CreateUsersWithArrayRequestModel;
import requestModel.SendUserDetails;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.hasKey;


public class AppTest extends BaseTest {
    //Test for post request
    @Test
    public void postUserDetails(){

        SendUserDetails sendData;
        sendData = new SendUserDetails(3,"joe","Joey","Tribbiani","joey.tribbabi@abc.com","12345","6578493986",1);

        ReceivedUserResponse postResponse;
        postResponse = given()
                        .spec(userSpecification)
                        .body(sendData).log().all()
                        .when()
                        .post()
                        .as(ReceivedUserResponse.class);

        postResponse.printResponse();
        Assert.assertEquals(200,postResponse.getCode());
    }

    //Testing the get request based on particular username
    @Test
    public void getUserDetails(){

        Response userResponse =
        given().spec(userSpecification).
                pathParam("username","joe").log().all().
                when().get("{username}").then().assertThat().
                statusCode(200).and().extract().response();

        System.out.println("Response received "+userResponse.asPrettyString());
    }

    //Testing the post request by sending multiple user details
    @Test
    public void sendMultipleUsers() {
        SendUserDetails sendData1 = new SendUserDetails(5, "joe", "Joey", "Tribbiani", "joey.tribbabi@abc.com", "12345", "6578493986", 1);
        SendUserDetails sendData2 = new SendUserDetails(6, "phoebo", "Phoebe", "Buffay", "phoebe.buffay@abc.com", "12345", "6578457886", 1);

        CreateUsersWithArrayRequestModel createUsersWithArrayRequestModel = new CreateUsersWithArrayRequestModel();
        createUsersWithArrayRequestModel.addaUserDetail(sendData1);
        createUsersWithArrayRequestModel.addaUserDetail(sendData2);

        ReceivedUserResponse postResponse =
                given().spec(userSpecification).
                        body(createUsersWithArrayRequestModel).log().all().
                        when().post("/createWithArray").
                        as(ReceivedUserResponse.class);

        postResponse.printResponse();
        Assert.assertEquals(200, postResponse.getCode());
        Assert.assertEquals("unknown",postResponse.getType());
        Assert.assertEquals("ok",postResponse.getMessage());
    }

    //Updating the user detail by sending the username as param
    @Test
    public void updateUserDetail(){
        SendUserDetails senddata1 = new SendUserDetails(5, "joe", "Joey", "Tribbiani", "joey.tribbabi@abc.com", "1245", "6279493986", 2);

        ReceivedUserResponse putResponse;
        putResponse = given().spec(userSpecification).pathParam("username","joe").
                      body(senddata1).log().all().
                      when().
                      put("{username}").
                      as(ReceivedUserResponse.class);

        putResponse.printResponse();
        Assert.assertEquals(200,putResponse.getCode());
        Assert.assertEquals("unknown",putResponse.getType());
        Assert.assertEquals("2",putResponse.getMessage());

    }

    //Deleting a particular user by sending user as param
    @Test
    public void deleteUser(){

        ReceivedUserResponse deleteResponse;
        deleteResponse = given().spec(userSpecification).pathParam("username","joe").
                         log().all().
                         when().
                         delete("{username}").
                         as(ReceivedUserResponse.class);

        deleteResponse.printResponse();
        Assert.assertEquals(200,deleteResponse.getCode());
        Assert.assertEquals("unknown",deleteResponse.getType());
        Assert.assertEquals("joe",deleteResponse.getMessage());
    }

    //Validating if the header's content type is Json
    @Test
    public void getHeaders(){

        Response userResponse;
        userResponse = given().spec(userSpecification).
                pathParam("username","joe").log().all().
                when().get("{username}");

        String contentType = userResponse.header("Content-Type");
        Assert.assertEquals(contentType,"application/json");
    }

    //Test to check if response body contains a specific string
    @Test
    public void validateResponseBodyJson(){
        Response response;
        response = given().spec(userSpecification).
                pathParam("username","joe").log().all().
                when().get("{username}").then().assertThat().
                statusCode(200).and().extract().response();

        ResponseBody body = response.getBody();

        String bodyAsStringValue = body.asString();

        // Validate if Response Body Contains a specific String
        Assert.assertTrue(bodyAsStringValue.contains("firstName"));

        // Get JSON Representation from Response Body
        JsonPath jsonPathEvaluator = response.jsonPath();

        String userFirstName = jsonPathEvaluator.get("firstName");

        Assert.assertTrue(userFirstName.equalsIgnoreCase("Joey"));
    }

    //Checking if the field like code,type or message exists in the response body
    @Test
    public void validateResponseField(){

        given().spec(userSpecification).
                pathParam("username","joe").log().all().
                when().get("{username}").then().
                body("[1]",hasKey("id"));
    }

    //Structure validation -> validating the get() json response with schema
    @Test
    public void responseSchemaValidation(){
        File schema = new File(System.getProperty("user.dir")+"/schema1.json");

        given().spec(userSpecification).
                pathParam("username","joe").log().all().
                when().get("{username}").then().
                body(matchesJsonSchema(schema));
    }

    //Measuring the response time
    @Test
    public void checkingResponseTime(){
        long timeInSeconds = given().spec(userSpecification).
                pathParam("username","joe").log().all().
                when().get("{username}")
                .timeIn(SECONDS);
        System.out.println(timeInSeconds);

        Assert.assertEquals(2,timeInSeconds);
    }



}
