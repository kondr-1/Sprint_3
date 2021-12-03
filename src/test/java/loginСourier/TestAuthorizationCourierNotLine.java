package loginСourier;

import api.requests.CreateCourier;
import api.requests.LoginCourier;
import api.responses.CreateCourierOk;
import api.sender.MethodService;
import generatedTestData.GeneratedDataCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constant.ResponseMessage.NOT_AUTHORIZATION;
import static constant.ResponseMessage.NOT_AUTHORIZATION_LINE;
import static constant.Urls.LOGIN_COURIER;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestAuthorizationCourierNotLine {

    @AfterClass
    public static void deleteCreatesCourierFromTest() {
        new GeneratedDataCourier().deleteCourier(new GeneratedDataCourier().authorizationCourier(cratePasswordCourier, crateLoginCourier));
    }

    private static String courierLogin = RandomStringUtils.randomAlphabetic(10);
    private static String courierPassword = RandomStringUtils.randomAlphabetic(10);

    public TestAuthorizationCourierNotLine(String courierLogin, String courierPassword) {
        TestAuthorizationCourierNotLine.courierLogin = courierLogin;
        TestAuthorizationCourierNotLine.courierPassword = courierPassword;
    }

    static CreateCourier loginCourier = new GeneratedDataCourier().TestCourierService();
    static String crateLoginCourier = loginCourier.getLogin();
    static String cratePasswordCourier = loginCourier.getPassword();

    @Parameterized.Parameters
    public static Object[] getViewException() {
        return new Object[][]{
                {"", courierLogin},
                {courierPassword, ""},
                {courierPassword, courierLogin},
                {"", ""},
                {courierPassword, crateLoginCourier},
                {cratePasswordCourier, courierLogin},
        };
    }

    @Test
    @Description("Parameterized test mandatory line and mismatch login password")
    public void createNewCourierVariousParameters() {
        LoginCourier loginCourierBead = LoginCourier.builder()
                .password(courierPassword)
                .login(courierLogin)
                .build();

        Response authorizationCourier = MethodService.postRequest(LOGIN_COURIER, loginCourierBead);
        switch (authorizationCourier.statusCode()) {
            case 400:
                assertEquals(NOT_AUTHORIZATION, authorizationCourier.as(CreateCourierOk.class).getMessage());
                break;
            case 404:
                assertEquals(NOT_AUTHORIZATION_LINE, authorizationCourier.as(CreateCourierOk.class).getMessage());
                break;
        }
    }
}
