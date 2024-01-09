package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.common.JacksonResources
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.RestClient

import scala.collection.JavaConverters.*
import scala.io.Source

class GlobalCommandRegistrar(val restClient: RestClient, val commandsRoot: String, val metadataFilenames: List[String]):
  val logger: Logger = Logger(getClass.getName)

  def registerCommands(): Unit =
    val appService = restClient.getApplicationService
    val appId = restClient.getApplicationId.block()
    val d4jMapper = JacksonResources.create()

    val commands = retrieveCommands(d4jMapper)

    appService.bulkOverwriteGlobalApplicationCommand(appId, commands.asJava)
      .doOnNext(cmd => logger.debug("Registered command: " ++ cmd.name()))
      .doOnError(e => logger.error("Failed to register", e))
      .subscribe()

  private def retrieveCommands(d4jMapper: JacksonResources): List[ApplicationCommandRequest] =
    metadataFilenames map (n => {
      val jsonContent = retrieveResourceAsString(s"$commandsRoot/$n")
      d4jMapper.getObjectMapper().readValue(jsonContent, classOf[ApplicationCommandRequest])
    })

  private def retrieveResourceAsString(filepath: String): String =
    Source.fromResource(filepath).mkString

