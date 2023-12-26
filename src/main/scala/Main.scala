import com.typesafe.scalalogging.Logger
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import commands.SlashCommand;

val logger = Logger("Discord-Server-Bridge")

@main def hello: Unit =
  logger.debug(msg)

  val client = DiscordClientBuilder.create("BOT-TOKEN")
    .build()
    .login()
    .block()
  
  val commands = List("link.json")
  new GlobalCommandRegistrar(client.getRestClient()).registerCommands(commands)
  
  client.on(classOf[ChatInputInteractionEvent], SlashCommandListener.handle)

def msg = "I was compiled by Scala 3. :)"
