package discordserverbridge

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discordserverbridge.commands.{LinkCommand, SlashCommand}
import reactor.core.publisher.{Flux, Mono}

import scala.collection.JavaConverters.*

object SlashCommandListener:
  val command_list: List[SlashCommand] = List(new LinkCommand())

  def handle(event: ChatInputInteractionEvent) =
    Flux.fromIterable(command_list.asJava)
      .filter(cmd => cmd.name == event.getCommandName())
      .next()
      .flatMap(cmd => cmd.handle(event))    
