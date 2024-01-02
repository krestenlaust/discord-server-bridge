package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discordserverbridge.commands.{LinkCommand, PingCommand, SlashCommand}
import reactor.core.publisher.{Flux, Mono}

import scala.collection.JavaConverters.*

object SlashCommandListener:
  val logger: Logger = Logger(getClass.getName)

  private val command_list: List[SlashCommand] = List(new LinkCommand(), new PingCommand())

  def handle(event: ChatInputInteractionEvent): org.reactivestreams.Publisher[Object] =
    Flux.fromIterable(command_list.asJava)
      .filter(cmd => cmd.name == event.getCommandName())
      .next()
      .flatMap(cmd => cmd.handle(event))
