package discordserverbridge.commands

import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discordserverbridge.LinkedMessageChannelManager
import reactor.core.publisher.Mono

class LinkCommand extends SlashCommand:
  override def name: String = "link"

  override def handle(event: ChatInputInteractionEvent): Mono[Void] =
    var destinationChannelSnowflake: Option[Snowflake] = None

    try {
      destinationChannelSnowflake = Option(Snowflake.of(event.getOption("destination").get().getValue.get().asString()))
    }catch
      case _ => return event.reply().withEphemeral(true).withContent("Invalid destination channel :(")

    destinationChannelSnowflake match
      case Some(snowflake: Snowflake) =>
        event.getClient.getGuilds
          .flatMap(guild => guild.getChannelById(snowflake).cast(classOf[MessageChannel])
          .flatMap(destinationChannel => {
            event.getInteraction.getChannel.flatMap(originChannel => {
              // Example: Reply with a message in the destination channel
              //destinationChannel.createMessage("Hello, hello there")
              LinkedMessageChannelManager.linkChannel(originChannel, destinationChannel)
              event.reply().withEphemeral(true).withContent("Successfully linked channels")
            })
          })
        ).`then`(Mono.empty)
      case None =>
        event.reply().withEphemeral(true).withContent("Invalid destination channel")