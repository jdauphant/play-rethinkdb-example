package controllers

import com.rethinkscala.ast.DB
import com.rethinkscala.japi.Connection
import com.rethinkscala.net.{AsyncConnection, Version3}
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.Play.current
import models.User

import scala.concurrent.ExecutionContext.Implicits.global

import com.rethinkscala.Implicits.Async._

object AsyncController extends Controller {
  val host = Play.configuration.getString("rethinkdb.host").get
  val port = Play.configuration.getInt("rethinkdb.port").get
  val dbname = Play.configuration.getString("rethinkdb.dbname").get

  implicit val userWrite = Json.writes[User]

  def index = Action.async {
    lazy val version = new Version3(host,port)
    lazy implicit val connection = Async(version)
    lazy val db = DB(dbname)

    val table = db.table[User]("users")

    table.create.run

    val newUser = User("john","password")

    table.insert(newUser).run.flatMap( insertResult =>
      table.sample(10).as[Seq[User]].map( users =>
        Ok (Json.toJson(users)))
    )
  }

}