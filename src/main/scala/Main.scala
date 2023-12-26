import com.typesafe.scalalogging
import com.typesafe.scalalogging.Logger

val logger = Logger("Discord-Server-Bridge")

@main def hello: Unit =
  logger.debug("Hello world")
  println("Hello world!")
  println(msg)

def msg = "I was compiled by Scala 3. :)"
