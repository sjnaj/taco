package com.tacos.controllers;

import com.tacos.data.Ingredient;
import com.tacos.data.Ingredient.Type;
import com.tacos.data.Order;
import com.tacos.data.Taco;
import com.tacos.repository.jpa.IngredientRepository;
import com.tacos.repository.jpa.TacoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
  private final IngredientRepository ingredientRepository;
  private final TacoRepository designRepo;

  // @Autowired  Unnecessary, @Controller is enough
  public DesignTacoController(
    IngredientRepository ingredientRepository,
    TacoRepository designRepo
  ) {
    this.ingredientRepository = ingredientRepository;
    this.designRepo = designRepo;
  }

  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  public Model getDesignForm(Model model) {
    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepository.findAll().forEach(i -> ingredients.add(i));
    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(
        type.toString().toLowerCase(),
        ingredients
          .stream()
          .filter(x -> x.getType().equals(type))
          . collect(Collectors.toList())
      );
    }
    return model;
  }

  @GetMapping
  public String showDesignForm(Model model) {
    getDesignForm(model).addAttribute("design", new Taco());
    return "design";
  }

  @PostMapping
  public String processDesign(
    @Validated @ModelAttribute("design") Taco design,
    Errors errors,
    Model model,
    @ModelAttribute Order order
  ) {
    int i=0;
    if (errors.hasErrors()) {
      getDesignForm(model);
      return "design";
    }

    design.setIngredients(
      design
        .getIngredient_ids()
        .stream()
        .map(id -> ingredientRepository.findById(id).get())
        .collect(Collectors.toList())
    );
    log.info("Processing design: " + design);
    Taco saved = designRepo.save(design);
    order.addDesign(saved);
    return "redirect:/orders/current";
  }
}
