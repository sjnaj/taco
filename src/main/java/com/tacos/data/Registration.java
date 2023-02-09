package com.tacos.data;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class Registration {
  @NotBlank(message = "Is required")
  private String username;

 @Pattern(regexp = "^\\d{1,6}$",message="Is requred to be composed of one to six digits")
  private String password;

  private String passwordConf;

  @NotBlank(message = "Is required")
  private String fullname;

  @NotBlank(message = "Is required")
  private String street;

  @NotBlank(message = "Is required")
  private String city;

  @NotBlank(message = "Is required")
  private String state;

  @NotBlank(message = "Is required")
  private String zip;

  @Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}",message = "Is required a correct number")

  private String phoneNumber;

  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(
      username,
      passwordEncoder.encode(password),
      fullname,
      street,
      city,
      state,
      zip,
      phoneNumber
    );
  }
}
