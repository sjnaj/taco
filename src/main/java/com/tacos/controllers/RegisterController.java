package com.tacos.controllers;

import com.tacos.data.Registration;
import com.tacos.repository.jpa.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public RegisterController(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  @Qualifier("passwordValidator")
  private Validator validator;

  @InitBinder//注册自定义验证器
  private void initBinder(WebDataBinder binder) {
    // binder.setValidator(validator);
    binder.addValidators(validator);
  }

  @GetMapping
  public String registerForm(Model model) {
    model.addAttribute("registration", new Registration());
    return "registerForm";
  }

  @PostMapping
  public String processRegister(
    @Validated @ModelAttribute("registration") Registration form,
    BindingResult result
  ) {
    if(result.hasErrors()){
        return "registerForm";
    }
    userRepository.save(form.toUser(passwordEncoder));
    return "redirect:/login";
  }
}
