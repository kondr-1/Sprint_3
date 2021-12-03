package orders;

import api.responses.Orders;
import api.responses.OrdersCouriersOk;
import api.sender.MethodService;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static constant.Urls.GET_ORDERS;
import static org.junit.Assert.assertEquals;

public class GetOrdersTest {

    @Test
    @Description("Test get list orders")
    public void getAllOrdersCourier() {
        Response getAllOrderCouriersResponse = MethodService.getRequests(GET_ORDERS);
        assertEquals(200, getAllOrderCouriersResponse.statusCode());
        List<Orders> listOrders = getAllOrderCouriersResponse.as(OrdersCouriersOk.class).getOrders();
        Assert.assertFalse(listOrders.isEmpty());
    }
}