import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.example.OrderList;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class TestOrderList {

    private final OrderClient orderClient = new OrderClient();
    private OrderList orderList = new OrderList();

    @Test
    @DisplayName("Проверка что список заказов не пустой")
    public void returnOrderList() {
        ValidatableResponse response = orderClient.getOrderList(orderList);
        ArrayList<Order> list = response.extract().path("orders");

        Assert.assertTrue(list.size() > 0);
    }
}
