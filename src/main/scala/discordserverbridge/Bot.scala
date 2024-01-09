package discordserverbridge

import discord4j.core.DiscordClientBuilder
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent

class Bot(val token: String, command_metadata_path: List[String]):
  def start() =
    val client = DiscordClientBuilder.create(token)
      .build()
      .login()
      .block()

    new GlobalCommandRegistrar(client.getRestClient).registerCommands(command_metadata_path)

    client.on(classOf[ChatInputInteractionEvent], SlashCommandListener.handle)
      .or(client.on(classOf[MessageCreateEvent], ChatMessageListener.handle))
      .`then`(client.onDisconnect())
      .block()
