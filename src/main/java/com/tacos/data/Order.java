package com.tacos.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
// import javax.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
@Entity
@Table(name = "Taco_Order") //order is reserve word in sql,shouldn't use as table name
public class Order {
  @NotBlank(message = "Name is required")
  @Column(name = "deliverName")
  private String name;

  @NotBlank(message = "Street is required")
  @Column(name = "deliverStreet")
  private String street;

  @NotBlank(message = "City is required")
  @Column(name = "deliverCity")
  private String city;

  @Size(
    max = 2,
    min = 1,
    message = "State is required and The maximum length is 2"
  )
  @Column(name = "deliverState")
  private String state;

  @NotBlank(message = "Zip code is required")
  @Column(name = "deliverZip")
  private String zip;

  @CreditCardNumber(message = "Not a valid credit card number")
  private String ccNumber;

  @Pattern( 
    regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
    message = "Must be formatted MM/YY"
  )
  private String ccExpiration;

  @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
  private String ccCVV;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date placeAt;

  @ManyToMany(targetEntity = Taco.class)
  @JoinTable(
    name = "taco_order_tacos",
    joinColumns = {
      @JoinColumn(name = "tacoOrder", referencedColumnName = "id"),
    },
    inverseJoinColumns = {
      @JoinColumn(name = "taco", referencedColumnName = "id"),
    }
  )
  private List<Taco> tacos = new ArrayList<>();

  @ManyToOne
  private User user;

  public void addDesign(Taco design) {
    tacos.add(design);
  }

  @PrePersist
  void placeAt() {
    placeAt = new Date();
  }
}
