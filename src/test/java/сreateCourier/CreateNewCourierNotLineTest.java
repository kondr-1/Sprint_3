package сreateCourier;

import api.requests.CreateCourier;
import api.responses.CreateCourierOk;
import api.sender.MethodService;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constant.ResponseMessage.LOGIN_NOT_FIELD;
import static constant.Urls.ADD_COURIER;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateNewCourierNotLineTest {

    private static String courierLogin = RandomStringUtils.randomAlphabetic(10);
    private static String courierPassword = RandomStringUtils.randomAlphabetic(10);
    private static String courierFirstName = RandomStringUtils.randomAlphabetic(10);

    public CreateNewCourierNotLineTest(String courierLogin, String courierPassword, String courierFirstName) {
        this.courierLogin = courierLogin;
        this.courierPassword = courierPassword;
        this.courierFirstName = courierFirstName;
    }

    @Parameterized.Parameters
    public static Object[] getViewException() {
        return new Object[][]{
                {courierLogin, "", courierFirstName},
                {"", courierPassword, courierFirstName},
                {courierLogin, courierPassword, ""},
                {"", "", ""},
        };
    }

    @Test
    @Description("Parameterized test mandatory line create courier")
    public void createNewCourierVariousParameters() {
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