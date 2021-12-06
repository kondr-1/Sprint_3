package orders;

import api.requests.Order;
import api.responses.CreateOrderOk;
import api.sender.MethodService;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constant.Urls.CREATE_ORDER;

public class CreateOrderTest {
    Random random = new Random();
    int numberMetroStation = random.nextInt(237);
    int daysRentTime = random.nextInt(31);

    Faker faker = new Faker(new Locale("ru-RU"));
    String address = faker.address().streetAddress();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String phone = faker.numerify("+79#########");
    String comment = faker.commerce().productName() + faker.company().name();

    LocalDate date = LocalDate.now();
    String deliveryDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


    @ParameterizedTest
    @Description("Parameterized test mandatory colors")
    @CsvSource(
            value = {
                    "BLACK,",
                    "GREY,",
                    "GREY, BLACK",
                    ","
            }
    )
    void requestsSomeColorKickScooter(String color, String color2) {
        List<String> colors = Stream.of(color, color2).filter(Objects::nonNull).collect(Collectors.toList());
        Order order = Order.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .metroStation(numberMetroStation)
                .phone(phone)
                .rentTime(daysRentTime)
                .deliveryDate(deliveryDate)
                .comment(comment)
                .color(colors)
                .build();
        Response createNewOrderBasic = MethodService.postRequest(CREATE_ORDER, order);
        Assertions.assertEquals(201, createNewOrderBasic.statusCode());
        Assertions.assertNotNull(createNewOrderBasic.as(CreateOrderOk.class).getTrack());
    }
}