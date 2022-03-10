package co.copper.test.services

import akka.actor.ActorSystem
import co.copper.test.ApplicationConfiguration.UserServiceConf
import co.copper.test.model._
import co.copper.test.storage.UserRepository
import co.copper.test.util.JsonUtil
import com.sbuslab.utils.Logging
import org.asynchttpclient.AsyncHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.compat.java8.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}

trait UserService {
  def storeUsers: Future[Boolean]

  def retrieveUsers: Future[List[User]]
}

@Service
@Autowired
class DefaultUserService(httpClient: AsyncHttpClient, repository: UserRepository, conf: UserServiceConf)
                        (implicit ec: ExecutionContext, system: ActorSystem) extends UserService with Logging {

  def storeUsers: Future[Boolean] = {

    lazy val usersF = httpClient.prepareGet(conf.fullUrl)
      .execute().toCompletableFuture.toScala
      .map(resp ⇒ resp.getResponseBody)
      .map(JsonUtil.as[Users])

    for {
      users <- usersF
      persistedUsers <- Future(repository.saveUsers(users))
      verified = persistedUsers.count(_._2 == true) == persistedUsers.size
    } yield verified

  }

  def retrieveUsers: Future[List[User]] = Future(repository.retrieveUsers())

}
