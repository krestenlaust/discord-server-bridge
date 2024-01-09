package discordserverbridge

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger
import scala.collection.JavaConverters.*

object Main:
  val logger: Logger = Logger(getClass.getName)

  @main def run(): Unit =
    logger.info("I was compiled by Scala 3. :)")

    val config = ConfigFactory.load("bot.conf")

    val bot = new Bot(config)
    bot.start()
