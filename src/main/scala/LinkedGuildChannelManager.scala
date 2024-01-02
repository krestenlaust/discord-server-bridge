package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.`object`.entity.channel
import discord4j.core.`object`.entity.channel.GuildChannel

import scala.collection.mutable

object LinkedGuildChannelManager:
  val logger: Logger = Logger(getClass.getName)
  private val forwardLinkMap: mutable.HashMap[GuildChannel, GuildChannel] = new mutable.HashMap()
  private val backwardLinkMap: mutable.HashMap[GuildChannel, GuildChannel] = new mutable.HashMap()

  def linkChannel(origin: GuildChannel, dest: GuildChannel): Unit =
    logger.info(s"Linked from channel '${origin.getName}' to channel '${origin.getName}'")
    forwardLinkMap.addOne((origin, dest))
    backwardLinkMap.addOne((dest, origin))

  def unlinkChannel(origin: GuildChannel, dest: GuildChannel): Unit =
    logger.info(s"Unlinked from channel '${origin.getName}' to channel '${origin.getName}'")
    forwardLinkMap.remove(origin)
    backwardLinkMap.remove(dest)

  def linkedChannelByOrigin(origin: GuildChannel): Option[GuildChannel] =
    forwardLinkMap.get(origin)

  def linkedChannelByDestination(dest: GuildChannel): Option[GuildChannel] =
    backwardLinkMap.get(dest)