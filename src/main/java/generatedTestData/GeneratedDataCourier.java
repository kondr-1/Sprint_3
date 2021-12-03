package generatedTestData;

import api.requests.CreateCourier;
import api.requests.DeleteCourier;
import api.requests.LoginCourier;
import api.responses.CreateCourierOk;
import api.responses.LoginCourierOk;
import api.sender.MethodService;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import static constant.Urls.*;
import static org.junit.Assert.assertEquals;

public class GeneratedDataCourier {

    public CreateCourier TestCourierService() {

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        CreateCourier createCourier = CreateCourier.builder()
                .firstName(courierFirstName)
                .login(courierLogin)
                .password(courierPassword)
                .build();

        Response createNewCourier = MethodService.postRequest(ADD_COURIER, createCourier);
        assertEquals(201, createNewCourier.statusCode());
        Assert.assertTrue(createNewCourier.as(CreateCourierOk.class).getOk());
        return createCourier;
    }

    public String authorizationCourier(String courierPassword, String courierLogin) {
        LoginCourier loginCourier = LoginCourier.builder()
                .password(courierPassword)
                .login(courierLogin)
                .build();

        Response getIdCourier = MethodService.postRequest(LOGIN_COURIER, loginCourier);
        return getIdCourier.as(LoginCourierOk.class).getId().toString();
    }

    public void deleteCourier(String courierId) {
        DeleteCourier deleteCourierRequests = DeleteCourier.builder()
                .id(courierId)
                .build();

        assertEquals(200, MethodService.deleteRequests(DELETE_COURIER, deleteCourierRequests, courierId).statusCode());
    }
}