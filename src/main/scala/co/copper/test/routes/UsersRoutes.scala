package co.copper.test.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import co.copper.test.model.{ErrorView, UserView}
import co.copper.test.services.UserService
import co.copper.test.util.JsonUtil.toJsonString
import com.sbuslab.http.RestRoutes
import com.sbuslab.sbus.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Autowired
class UsersRoutes(userService: UserService) extends RestRoutes {

  private implicit val ec = scala.concurrent.ExecutionContext.global

  def anonymousRoutes(implicit context: Context): Route =
    pathEnd {
      post {
        complete {
          userService.storeUsers.map(_ match {
            case true => HttpResponse(status = StatusCodes.Created)
            case false => HttpResponse(status = StatusCodes.InternalServerError, entity = toJsonString(ErrorView("INTERNAL", "storing failed for some records")))
          }).recover { case ex => HttpResponse(status = StatusCodes.InternalServerError, entity = toJsonString(ErrorView("UNKNOWN", ex.getMessage))) }
        }
      } ~
        get {
          complete {
            userService.retrieveUsers
              .map(_.map(UserView(_)))
              .map(toJsonString)
              .map(body => HttpResponse(status = StatusCodes.OK, entity = body))
              .recover { case ex => HttpResponse(status = StatusCodes.InternalServerError, entity = toJsonString(ErrorView("UNKNOWN", ex.getMessage))) }
          }
        }
    }

}
