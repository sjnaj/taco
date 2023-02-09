package com.tacos.repository.jpa;

import com.tacos.data.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository
  extends CrudRepository<Ingredient, String> {}
