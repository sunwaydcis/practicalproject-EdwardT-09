package addressapp.util

import scalikejdbc.*
import addressapp.model.Person

trait Database:
  val derbyDriverClassname = 
    "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB; create=true;"
  
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL,"me", "mine")
  given AutoSession = AutoSession
  
object Database extends Database:
  def setupDB() =
    if (!hasDBInitialize) then
      Person.initializeTable()

  
  def hasDBInitialize:Boolean = 
    DB getTable "Person" match {
      case Some(x) => true
      case None => false
    }
      
      