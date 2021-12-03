package api.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("metroStation")
    private Integer metroStation;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("rentTime")
    private Integer rentTime;
    @JsonProperty("deliveryDate")
    private String deliveryDate;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("color")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> color;
}