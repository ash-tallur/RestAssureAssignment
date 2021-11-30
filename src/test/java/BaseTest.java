import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class BaseTest {
    public static String baseURl = "https://petstore.swagger.io/v2";
    public static String urlCategory = "/user";

    static RequestSpecification userSpecification;

    @BeforeClass
    public static void whenSetup(){
        RequestSpecBuilder createUserSpecification = new RequestSpecBuilder();

        createUserSpecification.setBaseUri(baseURl);
        createUserSpecification.setBasePath(urlCategory);
        createUserSpecification.setContentType(ContentType.JSON);

        userSpecification = createUserSpecification.build();
    }
}
