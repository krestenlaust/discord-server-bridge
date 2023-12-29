package discordserverbridge.commands

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

class LinkCommand extends SlashCommand:
  override def name: String = "link"

  override def handle(event: ChatInputInteractionEvent): Mono[Void] =
    // Reply to the slash command, with the name the user supplied
    event.reply()
      .withEphemeral(true)
      .withContent("Hello, hello there")
