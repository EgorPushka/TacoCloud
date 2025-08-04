package sia.tacocloud.data.model;


import com.datastax.oss.driver.api.core.uuid.Uuids;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("orders")
public class TacoOrder implements Serializable {

  private static final long serialVersionId = 1L;

  @PrimaryKey
  private UUID id = Uuids.timeBased();
  private Date placedAt = new Date();

  @NotBlank(message = "Delivery Name required")
  private String deliveryName;

  @NotBlank(message = "Street is required")
  private String deliveryStreet;

  @NotBlank(message = "City is required")
  private String deliveryCity;

  @NotBlank(message = "State id required")
  private String deliveryState;

  @NotBlank(message = "Zip Code is required")
  private String deliveryZip;

  //    @CreditCardNumber(message = "Invalid Card Number")
  @NotBlank(message = "Number is required")
  private String ccNumber;

  @NotNull(message = "Expiration date is required")
  @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Expiration date must be in MM/YY format")
  private String ccExpiration;

  @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
  private String ccCVV;

  @Column("tacos")
  private List<TacoUDT> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {

  }
}
