package com.tacos.service;

import com.tacos.data.User;
import com.tacos.repository.jpa.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
  private UserRepository userRepo;

  public UserRepositoryUserDetailsService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    log.debug(username);
    return Optional
      .ofNullable(user)
      .orElseThrow(
        () -> new UsernameNotFoundException("User '" + username + "' not found")
      );
  }
}
