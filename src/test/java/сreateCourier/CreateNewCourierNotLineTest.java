package сreateCourier;

import api.requests.CreateCourier;
import api.responses.CreateCourierOk;
import api.sender.MethodService;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static constant.ResponseMessage.LOGIN_NOT_FIELD;
import static constant.Urls.ADD_COURIER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewCourierNotLineTest {

    private static String courierLogin = RandomStringUtils.randomAlphabetic(10);
    private static String courierPassword = RandomStringUtils.randomAlphabetic(10);
    private static String courierFirstName = RandomStringUtils.randomAlphabetic(10);


    public static Object[] getViewException() {
        return new Object[][]{
                {courierLogin, "", courierFirstName},
                {"", courierPassword, courierFirstName},
                {courierLogin, courierPassword, ""},
                {"", "", ""},
        };
    }

    @ParameterizedTest
    @MethodSource("getViewException")
    @Description("Parameterized test mandatory line create courier")
    public void createNewCourierVariousParameters(String courierFirstName, String courierLogin, String courierPassword) {
        CreateCourier createCourier = CreateCourier.builder()
                .firstName(courierFirstName)
                .login(courierLogin)
                .password(courierPassword)
                .build();

        Response createNewCourier = MethodService.postRequest(ADD_COURIER, createCourier);
        assertEquals(400, createNewCourier.statusCode());
        assertEquals(LOGIN_NOT_FIELD, createNewCourier.as(CreateCourierOk.class).getMessage());
    }
}