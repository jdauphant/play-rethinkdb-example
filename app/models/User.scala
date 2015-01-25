package models

import com.rethinkscala.Document

case class User(name: String, password: String) extends Document
