package commands

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discordserverbridge.commands.SlashCommand
import reactor.core.publisher.Mono

class UnlinkCommand extends SlashCommand:
  override def name: String = "unlink"

  override def handle(event: ChatInputInteractionEvent): Mono[Void] =
    event.reply().withEphemeral(true).withContent("Unlinked")