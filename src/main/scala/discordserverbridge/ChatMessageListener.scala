package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

object ChatMessageListener:
  val logger: Logger = Logger(getClass.getName)

  def handle(event: MessageCreateEvent): org.reactivestreams.Publisher[Object] =
    logger.info(event.getMessage.toString)
    Mono.empty