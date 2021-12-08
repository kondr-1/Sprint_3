package api.sender;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MethodService {

    public static Response postRequest(String url, Object body) {
        Response response = (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(url)
                .then()
                .extract();
        return response;
    }

    public static Response deleteRequests(String url, Object body, String idCourier) {
        Response response = (Response) given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .delete(String.format(url, idCourier))
                .then()
                .extract();
        return response;
    }

    public static Response getRequests(String url) {
        Response response = (Response) given()
                .and()
                .when()
                .get(url)
                .then()
                .extract();
        return response;
    }
}