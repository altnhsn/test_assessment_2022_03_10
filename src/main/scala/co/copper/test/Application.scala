package co.copper.test

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import co.copper.test.routes.{TestRoutes, UsersRoutes}
import com.sbuslab.http.RestService
import com.sbuslab.utils.Logging
import com.typesafe.config.Config
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt


object Application extends App with Logging {
  val ctx = new AnnotationConfigApplicationContext(classOf[ApplicationConfiguration])
  implicit val system = ctx.getBean(classOf[ActorSystem])
  implicit val materializer = ctx.getBean(classOf[ActorMaterializer])
  implicit val ec = ctx.getBean(classOf[ExecutionContext])
  implicit val config = ctx.getBean(classOf[Config])

  val restService = new RestService(config.getConfig("copper.test.public-api")) {
    start {
      metrics("all") {
        toStrictEntity(5.seconds) {
          sbusContext { implicit context ⇒
            pathPrefix("test")(ctx.getBean(classOf[TestRoutes]).anonymousRoutes)
            pathPrefix("users")(ctx.getBean(classOf[UsersRoutes]).anonymousRoutes)
          }
        }
      }
    }
  }

  def blockMe: Unit = {
    val keepAlive = true
    while (keepAlive) Thread.sleep(1000)
  }

  blockMe
}
