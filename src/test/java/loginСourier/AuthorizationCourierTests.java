package loginСourier;

import api.requests.CreateCourier;
import api.requests.LoginCourier;
import api.responses.LoginCourierOk;
import api.sender.MethodService;
import generatedTestData.GeneratedDataCourier;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static constant.Urls.LOGIN_COURIER;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationCourierTests {

    @Test
    @Description("Create, authorization and delete courier")
    public void authorizationCourier() {
        CreateCourier createCourier = new GeneratedDataCourier().TestCourierService();

        LoginCourier loginCourier = LoginCourier.builder()
                .password(createCourier.getPassword())
                .login(createCourier.getLogin())
                .build();

        Response authorizationNewCourier = MethodService.postRequest(LOGIN_COURIER, loginCourier);
        assertEquals(200, authorizationNewCourier.statusCode());
        assertNotNull(authorizationNewCourier.as(LoginCourierOk.class).getId());

        new GeneratedDataCourier().deleteCourier(authorizationNewCourier.as(LoginCourierOk.class).getId().toString());
    }
}