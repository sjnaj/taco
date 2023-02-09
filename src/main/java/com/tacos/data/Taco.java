package com.tacos.data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Taco {
  @NotNull
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private String name;

  @NotEmpty(message = "You must choose at least 1 ingredient")
  @Transient
  private List<String> ingredient_ids;

  @ManyToMany(targetEntity = Ingredient.class)// 定义与ingredient实体集的单向多对多联系，自定义联系集的名称和属性名
  @JoinTable(
    name = "taco_ingredients",
    joinColumns = { @JoinColumn(name = "taco", referencedColumnName = "id") },
    inverseJoinColumns = {
      @JoinColumn(name = "ingredient", referencedColumnName = "id"),
    }
  )
  private List<Ingredient> ingredients=new ArrayList<>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) //self-adaption strategy
  private Long id;

  private Date createAt;

  @PrePersist
  void createAt() {
    createAt = new Date();
  }
}
