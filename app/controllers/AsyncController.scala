package controllers

import play.api.libs.json.Json
import play.api.mvc._
import models.User

import scala.concurrent.ExecutionContext.Implicits.global


object AsyncController extends Controller {

  implicit val userWrite = Json.writes[User]

  def index = Action.async {
    val newUser = User("john","password")
    
    User.createTable

    User.insert(newUser).flatMap( insertResult =>
      User.getSamples().map( users =>
        Ok (Json.toJson(users)))
    )
  }
}
