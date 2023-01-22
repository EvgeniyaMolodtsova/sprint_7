import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCourierCreate {

    private Courier courier;
    private Courier courierWithoutLogin;
    private Courier courierWithoutPassword;
    private CourierClient courierClient;
    private int id;
    CourierGenerator courierGenerator = new CourierGenerator();

    @Before
    public void setUp() {
        courier = courierGenerator.getDefault();
        courierWithoutLogin = courierGenerator.getDefaultWithoutLogin();
        courierWithoutPassword = courierGenerator.getDefaultWithoutPassword();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("тест на создание курьера с валидными параметрами")
    public void courierCanBeCreated() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        if (loginResponse.extract().statusCode() == 200) {
            id = loginResponse.extract().path("id");
        }

        int statusCode = response.extract().statusCode();

        Assert.assertEquals(201, statusCode); //так же проверяется, что при успехе возвращается верный код
    }

    @Test
    @DisplayName("нельзя создать двух одинаковых курьеров")
    public void equalCourierNotBeCreated() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse responseEqual = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        if (loginResponse.extract().statusCode() == 200) {
            id = loginResponse.extract().path("id");
        }

        int statusCode = responseEqual.extract().statusCode();

        Assert.assertEquals(409, statusCode);
    }

    @Test
    @DisplayName("создание курьера без логина")
    public void courierNotBeCreatedWithoutLogin() {
        ValidatableResponse response = courierClient.create(courierWithoutLogin);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierWithoutLogin));

        if (loginResponse.extract().statusCode() == 200) {
            id = loginResponse.extract().path("id");
        }

        int statusCode = response.extract().statusCode();

        Assert.assertEquals(400, statusCode);
    }

    @Test
    @DisplayName("создание курьера без пароля")
    public void courierNotBeCreatedWithoutPassword() {
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierWithoutPassword));

        if (loginResponse.extract().statusCode() == 200) {
            id = loginResponse.extract().path("id");
        }

        int statusCode = response.extract().statusCode();

        Assert.assertEquals(400, statusCode);
    }

    @Test
    @DisplayName("тест проверяет, что успешный запрос возвращает ok:true")
    public void successfulRequestReturnsOkTrue() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        if (loginResponse.extract().statusCode() == 200) {
            id = loginResponse.extract().path("id");
        }

        boolean statusRequest = response.extract().path("ok");

        Assert.assertTrue(statusRequest);
    }

    @Test
    @DisplayName("нельзя создать курьера с уже существующим логином")
    public void canNotCreateCourierWithEqualLogin() {
        Courier courier1 = new Courier("SwG2Gp", "1234", "king");
        Courier courier2 = new Courier("SwG2Gp", "98776", "prince");

        ValidatableResponse firstCourier = courierClient.create(courier1);
        ValidatableResponse secondCourier = courierClient.create(courier2);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier1));

        if (loginResponse.extract().statusCode() == 200) {
            id = loginResponse.extract().path("id");
        }

        int statusCode = secondCourier.extract().statusCode();

        Assert.assertEquals(409, statusCode);
    }
}
