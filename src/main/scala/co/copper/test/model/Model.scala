package co.copper.test.model

case class Name(first: String, last: String)

case class Login(password: String)

case class User(name: Name, login: Login, email: String)

case class Users(results: List[User])

case class UserView(firstName: String, lastName: String, email: String)

object UserView {
  def apply(user: User): UserView = UserView(user.name.first, user.name.last, user.email)
}

case class ErrorView(errorCode : String, errorMessage : String)