package discordserverbridge.commands

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

trait SlashCommand:
    def name: String
    def handle(event: ChatInputInteractionEvent): Mono[Void]
