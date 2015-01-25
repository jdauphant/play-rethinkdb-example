package controllers

import com.rethinkscala.ast.DB
import com.rethinkscala.japi.Connection
import com.rethinkscala.net.Version3
import controllers.AsyncController._
import models.User
import play.api.Play
import play.api.Play.current
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import com.rethinkscala.Implicits.Blocking._

object BlockingController extends Controller {
  val host = Play.configuration.getString("rethinkdb.host").get
  val port = Play.configuration.getInt("rethinkdb.port").get
  val dbname = Play.configuration.getString("rethinkdb.dbname").get

  implicit val userWrite = Json.writes[User]
  def index = Action {
    lazy val version = new Version3(host,port)
    lazy implicit val connection = Blocking(version)
    implicit val blockingConnection = Blocking
    lazy val db = DB(dbname)

    val table = db.table[User]("users")
    table.create.run

    val newUser = User("john","password")
    table.insert(newUser).run match {
      case Left(e) =>
        InternalServerError("Error "+e.toString)
      case Right(_) =>
        table.sample(10).as[Seq[User]] match {
          case Left(e) =>
            InternalServerError("Error "+e.toString)
          case Right(users) =>
            Ok (Json.toJson(users))
        }
    }
  }
}
