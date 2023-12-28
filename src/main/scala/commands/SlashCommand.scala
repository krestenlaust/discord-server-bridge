package discordserverbridge.commands

import reactor.core.publisher.Mono
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent

trait SlashCommand:
    def name: String
    def handle(event: ChatInputInteractionEvent): Mono[Void]
