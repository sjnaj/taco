package com.tacos.repository.jpa;

import com.tacos.data.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByUsername(String username);//auto generated
}
