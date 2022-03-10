package co.copper.test.services

import akka.actor.ActorSystem
import com.sbuslab.utils.Logging
import org.asynchttpclient.AsyncHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.compat.java8.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}

@Service
@Autowired
class TestService(httpClient: AsyncHttpClient)(implicit ec: ExecutionContext, system: ActorSystem) extends Logging {

  def returnOk: Future[String] = {
    httpClient.prepareGet("https://postman-echo.com/get").execute().toCompletableFuture.toScala map { resp ⇒
      resp.getResponseBody
    }
  }
}
