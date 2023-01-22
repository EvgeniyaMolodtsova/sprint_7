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

public class TestLoginCourier {

    private Courier courier;
    private CourierClient courierClient = new CourierClient();

    @Before
    public void createCourier() {
        courier = new Courier("ZJTi1M", "1234", "testTest");
        courierClient.create(courier);
    }

    @After
    public void deleteCourier() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        if (loginResponse.extract().statusCode() == 200) {
            int id = loginResponse.extract().path("id");
            courierClient.delete(id);
        }
    }

    @Test
    @DisplayName("курьер может авторизоваться в системе")
    public void courierCanLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int statusCode = loginResponse.extract().statusCode();

        Assert.assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("нельзя зайти без логина")
    public void canNotLoginWithoutLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.create("", "1234"));

        int statusCode = loginResponse.extract().statusCode();

        Assert.assertEquals(400, statusCode);
    }

    @Test
    @DisplayName("нельзя зайти без пароля")
    public void canNotLoginWithoutPassword() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.create("ZJTi1M", ""));

        int statusCode = loginResponse.extract().statusCode();

        Assert.assertEquals(400, statusCode);
    }

    @Test
    @DisplayName("нельзя зайти с неправильным паролем")
    public void canNotLoginWithIncorrectPassword() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.create("ZJTi1M", "7865"));

        int statusCode = loginResponse.extract().statusCode();

        Assert.assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("нельзя зайти с неправильным логином")
    public void canNotLoginWithIncorrectLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.create("ZJTi1F", "1234"));

        int statusCode = loginResponse.extract().statusCode();

        Assert.assertEquals(404, statusCode);
    }

    @Test
    @DisplayName("успешный запрос возвращает id")
    public void courierCanLoginReturnId() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int id = loginResponse.extract().path("id");

        Assert.assertTrue(id > 0);
    }
}
