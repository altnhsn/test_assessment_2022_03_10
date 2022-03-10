package co.copper.test.storage

import co.copper.test.model._
import co.copper.test.storage.UserRepository.UserRowMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.sbuslab.utils.db.JacksonBeanRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.{MapSqlParameterSource, NamedParameterJdbcTemplate}
import org.springframework.stereotype.Repository

import java.sql.ResultSet
import java.util.UUID
import scala.collection.JavaConverters._

@Repository
@Autowired
class UserRepository(jdbcTemplate: NamedParameterJdbcTemplate) {

  private val rowMapper = new UserRowMapper

  private val insertUsersQuery: String =
    """
      |INSERT INTO random_user (id, first, last, email, password)
      |VALUES (:id, :first, :last, :email, :password);
      |""".stripMargin

  def retrieveUsers(): List[User] = {
    jdbcTemplate.query("""SELECT * FROM public.random_user""", rowMapper).asScala.toList
  }

  def saveUsers(users: Users): Map[User, Boolean] =
    users.results.map(user => user -> saveUser(user)).toMap

  def saveUser(user: User): Boolean = {
    jdbcTemplate.update(insertUsersQuery,
      new MapSqlParameterSource().addValues(Map(
        "id" -> UUID.randomUUID().toString,
        "first" -> user.name.first,
        "last" -> user.name.last,
        "email" -> user.email,
        "password" -> user.login.password).asJava)) == 1
  }

}

object UserRepository {
  class UserRowMapper extends RowMapper[User] {
    override def mapRow(rs: ResultSet, rowNum: Int): User = {
      User(name = Name(first = rs.getString("first"), last = rs.getString("last")),
        login = Login(rs.getString("password")),
        email = rs.getString("email")
      )
    }
  }
}
