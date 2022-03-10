package co.copper.test.routes

import akka.http.scaladsl.server.Route
import co.copper.test.services.{TestJavaService, TestService}
import com.sbuslab.http.RestRoutes
import com.sbuslab.sbus.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.compat.java8.FutureConverters.CompletionStageOps

@Component
@Autowired
class TestRoutes(
  deribitService: TestService,
  deribitJavaService: TestJavaService,
) extends RestRoutes {

  def anonymousRoutes(implicit context: Context): Route =
    pathEnd {
      post {
        complete {
          deribitService.returnOk
        }
      } ~
      get {
        complete {
          deribitJavaService.getOk.toScala
        }
      }
    }

}
