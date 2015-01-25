package models

import com.rethinkscala.Document
import com.rethinkscala.ast.DB
import com.rethinkscala.net.Version3
import play.api.Play
import play.api.Play.current

import com.rethinkscala.Implicits.Async._

case class User(name: String, password: String) extends Document

object User {
  val host = Play.configuration.getString("rethinkdb.host").get
  val port = Play.configuration.getInt("rethinkdb.port").get
  val dbname = Play.configuration.getString("rethinkdb.dbname").get
  lazy val version = new Version3(host,port)
  lazy implicit val connection = Async(version)
  lazy val db = DB(dbname)
  val table = db.table[User]("users")

  def insert(newUser: User) = table.insert(newUser).run

  def getSamples() = table.sample(10).as[Seq[User]]
}