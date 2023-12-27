package discordserverbridge.commands

import scala.collection.JavaConverters._
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.`object`.command.ApplicationCommandInteractionOption;
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;

class LinkCommand extends SlashCommand {
  override def getName(): String = "link"
  
  override def handle(event: ChatInputInteractionEvent): Mono[Void] =
    // Reply to the slash command, with the name the user supplied
    event.reply()
      .withEphemeral(true)
      .withContent("Hello, hello there")
}
