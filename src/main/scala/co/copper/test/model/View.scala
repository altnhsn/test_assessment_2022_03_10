package co.copper.test.model

case class UserView(firstName: String, lastName: String, email: String)

object UserView {
  def apply(user: User): UserView = UserView(user.name.first, user.name.last, user.email)
}

case class ErrorView(errorCode: String, errorMessage: String)
