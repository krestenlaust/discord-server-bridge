package discordserverbridge

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger

object Main:
  val logger = Logger("Discord-Server-Bridge")

  @main def run(): Unit =
    logger.debug("I was compiled by Scala 3. :)")

    val config = ConfigFactory.load("bot.conf")
    val bottoken = config.getString("discord.token")

    val bot = new Bot(bottoken)
    bot.start()
