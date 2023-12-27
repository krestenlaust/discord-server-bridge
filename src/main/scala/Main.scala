package discordserverbridge

import com.typesafe.scalalogging.Logger

object Main:
  val logger = Logger("Discord-Server-Bridge")

  @main def run(): Unit =
    logger.debug("I was compiled by Scala 3. :)")
    val bot = new Bot("MTE4OTI2OTMzNDI5MjQ0NzMwMg.GTYaUC.2a49kh11cqggpxakqV_OksIsB9WQR78d5rKFek")
