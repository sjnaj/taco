package com.tacos.controllers;

import com.tacos.config.OrderProps;
import com.tacos.data.Order;
import com.tacos.data.User;
import com.tacos.repository.jpa.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")//Spring MVC将存放在model中对应的数据暂存到session
public class OrderController {

  private OrderProps orderProps ;
  private OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository ,OrderProps orderProps) {
    this.orderRepository = orderRepository;
    this.orderProps=orderProps;
  }

  @GetMapping("/current")
  public String orderForm(Model model) {
    model.addAttribute("order", new Order());
    return "OrderForm";
  }

  @PostMapping
  public String procesOrder(
    @Validated @ModelAttribute("order") Order order,
    Errors errors,
    SessionStatus sessionStatus,
    @AuthenticationPrincipal User user
  ) {
    if (errors.hasErrors()) {
      return "OrderForm";
    }
    order.setUser(user);
    log.info("Order submitted: " + order);
    orderRepository.save(order);
    sessionStatus.setComplete(); //clear order in session
    return "redirect:/";
  }

  @GetMapping
  public String orderForUser(@AuthenticationPrincipal User user, Model model) {
    Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
    model.addAttribute(
      "orders",
      orderRepository.findByUserOrderByPlaceAtDesc(user, pageable)
    );
    return "orderList";
  }

}
