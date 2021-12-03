package сreateCourier;

import api.requests.CreateCourier;
import api.requests.DeleteCourier;
import api.requests.LoginCourier;
import api.responses.CreateCourierOk;
import api.responses.LoginCourierOk;
import api.sender.MethodService;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static constant.ResponseMessage.LOGIN_SOME_COURIER;
import static constant.Urls.*;
import static org.junit.Assert.assertEquals;

public class AddNewCourierTest {
    Faker faker = new Faker(new Locale("en-US"));
    String courierLogin = faker.name().lastName();
    String courierPassword = faker.name().lastName() + faker.number().randomNumber();
    String courierFirstName = faker.name().firstName();

    CreateCourier createCourier = CreateCourier.builder()
            .firstName(courierFirstName)
            .login(courierLogin)
            .password(courierPassword)
            .build();

    LoginCourier loginCourier = LoginCourier.builder()
            .password(courierPassword)
            .login(courierLogin)
            .build();


    @Test
    @Description("Create new courier test")
    public void newCourier() {
        Response createNewCourier = MethodService.postRequest(ADD_COURIER, createCourier);
        assertEquals(201, createNewCourier.statusCode());
        Assert.assertTrue(createNewCourier.as(CreateCourierOk.class).getOk());

        Response getIdCourier = MethodService.postRequest(LOGIN_COURIER, loginCourier);
        String courierId = getIdCourier.as(LoginCourierOk.class).getId().toString();

        DeleteCourier deleteCourier = DeleteCourier.builder()
                .id(courierId)
                .build();

        MethodService.deleteRequests(DELETE_COURIER, deleteCourier, courierId);
    }

    @Test
    @Description("Create two еру same courier")
    public void createTwoSameCourier() {
        Response createOneCourier = MethodService.postRequest(ADD_COURIER, createCourier);
        Assert.assertTrue(createOneCourier.as(CreateCourierOk.class).getOk());

        Response createTwoCourier = MethodService.postRequest(ADD_COURIER, createCourier);
        assertEquals(LOGIN_SOME_COURIER, createTwoCourier.as(CreateCourierOk.class).getMessage());

        Response getIdCourier = MethodService.postRequest(LOGIN_COURIER, loginCourier);
        String courierId = getIdCourier.as(LoginCourierOk.class).getId().toString();

        DeleteCourier deleteCourier = DeleteCourier.builder()
                .id(courierId)
                .build();

        MethodService.deleteRequests(DELETE_COURIER, deleteCourier, courierId);
    }
}