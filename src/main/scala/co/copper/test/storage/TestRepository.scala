package co.copper.test.storage

import co.copper.test.datamodel.Test
import com.fasterxml.jackson.databind.ObjectMapper
import com.sbuslab.utils.db.JacksonBeanRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.{MapSqlParameterSource, NamedParameterJdbcTemplate}
import org.springframework.stereotype.Repository
import java.util

@Repository
@Autowired
class TestRepository(jdbcTemplate: NamedParameterJdbcTemplate, mapper: ObjectMapper) {

  private val rowMapperTest = new JacksonBeanRowMapper(classOf[Test], mapper)

  def getById(id: Long): util.List[Test] =
    jdbcTemplate.query(
      """
      SELECT * FROM test
      WHERE id = :id
    """, new MapSqlParameterSource("id", id), rowMapperTest)
}
