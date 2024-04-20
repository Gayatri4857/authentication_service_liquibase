package org.dnyanyog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AddPattern {

  @NotNull(message = "Password is mandatory")
  @NotBlank(message = "Password should not be blank")
  @Pattern(
      regexp = "(?=.*[a-z])(?=.*[A-Z]).{8}",
      message =
          "Password should be at least 8 characters, with 1 lowercase, 1 uppercase, and 1 special character")
  private String password;

  @NotNull(message = "Email id is mendatory")
  @NotBlank(message = "Email id should not be blank")
  @Pattern(regexp = "[a-z]{3,50}(@)[a-z]{3,50}(.)[a-z]{2,3}", message = "Invalid email format")
  private String email;

  @NotNull(message = "CardNumber is manadatory")
  @NotBlank(message = "CardNumber Should Not blank")
  @Pattern(regexp = "[0-9]{10,19}", message = "Card number must be 10 To 19 digits")
  private String cardNumber;

  @NotNull(message = "Phone Number is manadatory")
  @NotBlank(message = "Phone Number Should Not blank")
  @Pattern(regexp = "[0-9]{10}", message = "Invalid phone Number")
  private String phoneNumber;

  @NotNull(message = "Phone Number is manadatory")
  @NotBlank(message = "Phone Number Should Not blank")
  @Pattern(regexp = "[a-zA-Z0-9]*", message = "Alphanumeric characters only")
  private String alphanumericString;

  @NotNull(message = "Card Number is manadatory")
  @NotBlank(message = "Card Number Should Not blank")
  @Pattern(regexp = "[0-9]{16}", message = "Invalid credit card number")
  private String creditCardNumber;

  @NotNull(message = "Username is manadatory")
  @NotBlank(message = "Username Should Not blank")
  @Pattern(regexp = "[a-zA-Z0-9]{5,20}", message = "Invalid username format")
  private String username;

  @NotNull(message = "Color Code is manadatory")
  @NotBlank(message = "Color Code Should Not blank")
  @Pattern(regexp = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})", message = "Invalid color code")
  private String hexColorCode;

  @Pattern(regexp = "[1-9]d+", message = "Positive integer only")
  private String positiveInteger;
}
