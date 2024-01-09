package discordserverbridge

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger

object Main:
  val logger: Logger = Logger(getClass.getName)

  @main def run(): Unit =
    logger.info("I was compiled by Scala 3. :)")

    val config = ConfigFactory.load("bot.conf")
    val bottoken = config.getString("discord.token")

    val bot = new Bot(bottoken)
    bot.start()
