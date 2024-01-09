package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.core.`object`.entity.channel
import discord4j.core.`object`.entity.channel.MessageChannel

import scala.collection.mutable

object LinkedMessageChannelManager:
  val logger: Logger = Logger(getClass.getName)
  private val forwardLinkMap: mutable.HashMap[MessageChannel, MessageChannel] = new mutable.HashMap()
  private val backwardLinkMap: mutable.HashMap[MessageChannel, MessageChannel] = new mutable.HashMap()

  def linkChannel(origin: MessageChannel, dest: MessageChannel): Unit =
    logger.info(s"Linked from channel '${origin.getId}' to channel '${origin.getId}'")
    forwardLinkMap.addOne((origin, dest))
    backwardLinkMap.addOne((dest, origin))

  def unlinkChannel(origin: MessageChannel, dest: MessageChannel): Unit =
    logger.info(s"Unlinked from channel '${origin.getId}' to channel '${origin.getId}'")
    forwardLinkMap.remove(origin)
    backwardLinkMap.remove(dest)

  def linkedChannelByOrigin(origin: MessageChannel): Option[MessageChannel] =
    forwardLinkMap.get(origin)

  def linkedChannelByDestination(dest: MessageChannel): Option[MessageChannel] =
    backwardLinkMap.get(dest)