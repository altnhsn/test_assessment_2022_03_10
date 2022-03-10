package co.copper.test.model

case class Name(first: String, last: String)

case class Login(password: String)

case class User(name: Name, login: Login, email: String)

case class Users(results: List[User])