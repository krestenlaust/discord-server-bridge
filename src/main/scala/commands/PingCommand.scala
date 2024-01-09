package discordserverbridge.commands

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

class PingCommand extends SlashCommand:
  override def name: String = "ping"

  override def handle(event: ChatInputInteractionEvent): Mono[Void] =
    event.reply()
      .withEphemeral(true)
      .withContent("Pong!")
