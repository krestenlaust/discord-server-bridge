package discordserverbridge

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import commands.{SlashCommand,LinkCommand};
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import scala.collection.JavaConverters._

object SlashCommandListener:
  val command_list: List[SlashCommand] = List(new LinkCommand())

  def handle(event: ChatInputInteractionEvent) =
    Flux.fromIterable(command_list.asJava)
        .filter(cmd => cmd.name == event.getCommandName())
        .next()
        .flatMap(cmd => cmd.handle(event))    
