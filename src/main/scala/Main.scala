import com.typesafe.scalalogging.Logger
//import com.novamaday.d4j.gradle.simplebot.listeners.SlashCommandListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;

val logger = Logger("Discord-Server-Bridge")

@main def hello: Unit =
  logger.debug(msg)

def msg = "I was compiled by Scala 3. :)"
