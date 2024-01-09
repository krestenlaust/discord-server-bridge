package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.`object`.entity.{Message, User}
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

import scala.jdk.OptionConverters.*

object ChatMessageListener:
  val logger: Logger = Logger(getClass.getName)

  def handle(event: MessageCreateEvent): Mono[Void] =
    logger.info(event.getMessage.toString)

    event.getMessage.getAuthor.asScala.flatMap { author =>
      if author.isBot then
        return Mono.empty
      else
        None
      }

    event.getMessage.getChannel.flatMap { (originChannel: MessageChannel) =>
      LinkedMessageChannelManager.linkedChannelByOrigin(originChannel) match
        case Some(destChannel) =>
          destChannel.createMessage(s"${event.getMessage.getContent}")
        case None =>
          Mono.empty[Message]
    }.`then`(Mono.empty)