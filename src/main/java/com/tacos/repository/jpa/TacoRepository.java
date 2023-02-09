package com.tacos.repository.jpa;

import com.tacos.data.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {}
