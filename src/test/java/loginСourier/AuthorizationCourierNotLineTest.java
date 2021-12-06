package loginСourier;

import api.requests.CreateCourier;
import api.requests.LoginCourier;
import api.responses.CreateCourierOk;
import api.sender.MethodService;
import generatedTestData.GeneratedDataCourier;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.annotations.AfterClass;

import static constant.ResponseMessage.NOT_AUTHORIZATION;
import static constant.ResponseMessage.NOT_AUTHORIZATION_LINE;
import static constant.Urls.LOGIN_COURIER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationCourierNotLineTest {

    @AfterClass
    public void deleteCreatesCourierFromTest() {
        new GeneratedDataCourier().deleteCourier(new GeneratedDataCourier().authorizationCourier(cratePasswordCourier, crateLoginCourier));
    }

    private static String courierLogin = RandomStringUtils.randomAlphabetic(10);
    private static String courierPassword = RandomStringUtils.randomAlphabetic(10);

    static CreateCourier loginCourier = new GeneratedDataCourier().TestCourierService();
    static String crateLoginCourier = loginCourier.getLogin();
    static String cratePasswordCourier = loginCourier.getPassword();

    public static Object[] getViewEData() {
        return new Object[][]{
                {"", courierLogin},
                {courierPassword, ""},
                {"", ""},
        };
    }

    @ParameterizedTest
    @Description("Parameterized test mandatory line and mismatch login password")
    @MethodSource("getViewEData")
    public void createNewCourierVariousParameters(String courierPassword, String courierLogin) {
        LoginCourier loginCourierBead = LoginCourier.builder()
                .password(courierPassword)
                .login(courierLogin)
                .build();

        Response authorizationCourier = MethodService.postRequest(LOGIN_COURIER, loginCourierBead);
        assertEquals(400, authorizationCourier.statusCode());
        assertEquals(NOT_AUTHORIZATION, authorizationCourier.as(CreateCourierOk.class).getMessage());
    }

    public static Object[] getViewNotCreateCourier() {
        return new Object[][]{
                {courierPassword, courierLogin},
                {courierPassword, crateLoginCourier},
                {cratePasswordCourier, courierLogin},
        };
    }

    @ParameterizedTest
    @Description("Parameterized test mandatory line and mismatch login password")
    @MethodSource("getViewNotCreateCourier")
    public void createNewCourierVariousCreateLogPass(String courierPassword, String courierLogin) {
        LoginCourier loginCourierBead = LoginCourier.builder()
                .password(courierPassword)
                .login(courierLogin)
                .build();

        Response authorizationCourier = MethodService.postRequest(LOGIN_COURIER, loginCourierBead);
        assertEquals(404, authorizationCourier.statusCode());
        assertEquals(NOT_AUTHORIZATION_LINE, authorizationCourier.as(CreateCourierOk.class).getMessage());
    }

    @Test
    @Description("Create, authorization courier not line password")
    public void authorizationCourierNotPassword() {
        CreateCourier createCourier = new GeneratedDataCourier().TestCourierService();

        LoginCourier loginCourier = LoginCourier.builder()
                .login(createCourier.getLogin())
                .build();

        Response authorizationNewCourier = MethodService.postRequest(LOGIN_COURIER, loginCourier);
        assertEquals(400, authorizationNewCourier.statusCode());
        assertEquals(NOT_AUTHORIZATION, authorizationNewCourier.as(CreateCourierOk.class).getMessage());
    }

    @Test
    @Description("Create, authorization courier not line login")
    public void authorizationCourierNotLogin() {
        CreateCourier createCourier = new GeneratedDataCourier().TestCourierService();

        LoginCourier loginCourier = LoginCourier.builder()
                .password(createCourier.getPassword())
                .build();

        Response authorizationNewCourier = MethodService.postRequest(LOGIN_COURIER, loginCourier);
        assertEquals(400, authorizationNewCourier.statusCode());
        assertEquals(NOT_AUTHORIZATION, authorizationNewCourier.as(CreateCourierOk.class).getMessage());
    }
}