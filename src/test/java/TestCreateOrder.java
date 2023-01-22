import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestCreateOrder {

    private final Order order;
    private final int expectedStatusCode;
    private final OrderClient orderClient = new OrderClient();

    public TestCreateOrder(Order order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters()
    public static Object[][] getColor() {
        return new Object[][] {
                { Order.create(new String[]{"BLACK", "GREY"}), 201 },
                { Order.create(new String[]{"BLACK"}), 201},
                { Order.create(new String[]{"GREY"}), 201},
                { Order.create(new String[]{}), 201},
                { Order.create(null), 201}
        };
    }

    @Test
    @DisplayName("проверка, что можно выбрать черный, серый, черный+серый или ни один из цветов")
    public void createOrder () {
        ValidatableResponse response = orderClient.createOrder(order);

        int statusCode = response.extract().statusCode();
        int track = response.extract().path("track");
        orderClient.cancelOrder(track);

        Assert.assertTrue(track > 0);
        Assert.assertEquals(expectedStatusCode, statusCode);
    }
}
