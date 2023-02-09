package com.tacos.repository.jdbc;
import com.tacos.data.Ingredient;
public interface IngredientRepository {
    Iterable<Ingredient>findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
