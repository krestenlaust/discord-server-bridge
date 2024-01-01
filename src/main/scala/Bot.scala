package discordserverbridge

import discord4j.core.DiscordClientBuilder
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent

class Bot(val token: String):
  def start() =
    val client = DiscordClientBuilder.create(token)
      .build()
      .login()
      .block()

    val commands = List("link.json", "ping.json")
    new GlobalCommandRegistrar(client.getRestClient()).registerCommands(commands)

    client.on(classOf[ChatInputInteractionEvent], SlashCommandListener.handle)
      .`then`(client.onDisconnect())
      .block()
