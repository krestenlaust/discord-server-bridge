package discordserverbridge

import com.typesafe.config.Config
import discord4j.core.DiscordClientBuilder
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent

import scala.collection.JavaConverters.*

class Bot(config: Config):
  private val token = config.getString("discord.token")

  def start() =
    val client = DiscordClientBuilder.create(token)
      .build()
      .login()
      .block()

    new GlobalCommandRegistrar(
      client.getRestClient,
      config.getString("commands.root"),
      config.getStringList("commands.metadata-filenames").asScala.toList
    )
      .registerCommands()

    client.on(classOf[ChatInputInteractionEvent], SlashCommandListener.handle)
      .or(client.on(classOf[MessageCreateEvent], ChatMessageListener.handle))
      .`then`(client.onDisconnect())
      .block()
