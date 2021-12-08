package orders;

import api.responses.Orders;
import api.responses.OrdersCouriersOk;
import api.sender.MethodService;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static constant.Urls.GET_ORDERS;
import static org.junit.jupiter.api.Assertions.*;

public class GetOrdersTest {

    @Test
    @Description("Test get list orders")
    public void getAllOrdersCourier() {
        Response getAllOrderCouriersResponse = MethodService.getRequests(GET_ORDERS);
        assertEquals(200, getAllOrderCouriersResponse.statusCode());
        List<Orders> listOrders = getAllOrderCouriersResponse.as(OrdersCouriersOk.class).getOrders();
        Integer ordersId = getAllOrderCouriersResponse.as(OrdersCouriersOk.class).getOrders().get(0).getId();
        assertFalse(listOrders.isEmpty());
        assertNotNull(ordersId);
    }
}