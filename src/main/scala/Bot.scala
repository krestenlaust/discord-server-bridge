package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import commands.SlashCommand

class Bot(val token: String):
  def start() =
    val client = DiscordClientBuilder.create(token)
      .build()
      .login()
      .block()

    val commands = List("link.json")
    new GlobalCommandRegistrar(client.getRestClient()).registerCommands(commands)
    
    client.on(classOf[ChatInputInteractionEvent], SlashCommandListener.handle)