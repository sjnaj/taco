package com.tacos.repository.jdbc;

import com.tacos.data.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}
