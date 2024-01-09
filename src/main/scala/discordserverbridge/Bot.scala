package discordserverbridge

import com.typesafe.config.Config
import discord4j.core.DiscordClientBuilder
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import discordserverbridge.commands.{LinkCommand, PingCommand}

import scala.collection.JavaConverters.*

class Bot(config: Config):
  private val token = config.getString("discord.token")
  private val commands = List(new LinkCommand(), new PingCommand())

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

    val commandListener = new SlashCommandListener(commands)

    client.on(classOf[ChatInputInteractionEvent], commandListener.handle)
      .or(client.on(classOf[MessageCreateEvent], ChatMessageListener.handle))
      .`then`(client.onDisconnect())
      .block()
