package com.tacos.repository.jdbc;

import com.tacos.data.Ingredient;
import com.tacos.data.Taco;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTacoRepository implements TacoRepository {
  private JdbcTemplate jdbc;

  /**
   * @param jdbc
   */
  public JdbcTacoRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Taco save(Taco taco) {
    long tacoId = saveTacoInfo(taco);
    taco.setId(tacoId);
    for (Ingredient ingredient : taco.getIngredients()) {
      saveIngredientToTaco(ingredient, tacoId);
    }
    return taco;
  }

  private long saveTacoInfo(Taco taco) {
    taco.setCreateAt(new Date());
    PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
      "insert into Taco (name,createAt) values(?,?)",
      Types.VARCHAR,
      Types.TIMESTAMP
    );
    pscf.setReturnGeneratedKeys(true);
    PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
      Arrays.asList(taco.getName(), new Timestamp(taco.getCreateAt().getTime()))
    );

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(psc, keyHolder);
    return keyHolder.getKey().longValue();
  }

  private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
    jdbc.update(
      "insert into Taco_Ingredients (taco,ingredient) " + "values(?,?)",
      tacoId,
      ingredient.getId()
    );
  }
}
