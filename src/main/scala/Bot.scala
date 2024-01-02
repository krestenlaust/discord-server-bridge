package discordserverbridge

import discord4j.core.DiscordClientBuilder
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent

class Bot(val token: String):
  def start() =
    val client = DiscordClientBuilder.create(token)
      .build()
      .login()
      .block()

    val commands = List("link.json", "ping.json")
    new GlobalCommandRegistrar(client.getRestClient).registerCommands(commands)

    client.on(classOf[ChatInputInteractionEvent], SlashCommandListener.handle)
      .or(client.on(classOf[MessageCreateEvent], ChatMessageListener.handle))
      .`then`(client.onDisconnect())
      .block()
