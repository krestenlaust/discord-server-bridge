package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discordserverbridge.commands.SlashCommand
import reactor.core.publisher.{Flux, Mono}

import scala.collection.JavaConverters.*

class SlashCommandListener(commands: List[SlashCommand]):
  val logger: Logger = Logger(getClass.getName)

  def handle(event: ChatInputInteractionEvent): org.reactivestreams.Publisher[Object] =
    Flux.fromIterable(commands.asJava)
      .filter(cmd => cmd.name == event.getCommandName())
      .next()
      .flatMap(cmd => cmd.handle(event))
