package discordserverbridge

import com.typesafe.scalalogging.Logger
import discord4j.common.JacksonResources
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.RestClient

import scala.collection.JavaConverters.*
import scala.io.Source

class GlobalCommandRegistrar(val restClient: RestClient):
  val logger: Logger = Logger(getClass.getName)

  def registerCommands(command_names: List[String]): Unit =
    val appService = restClient.getApplicationService
    val appId = restClient.getApplicationId.block()
    val d4jMapper = JacksonResources.create()

    val commands = retrieveCommands(command_names, d4jMapper)

    appService.bulkOverwriteGlobalApplicationCommand(appId, commands.asJava)
      .doOnNext(cmd => logger.debug("Registered command: " ++ cmd.name()))
      .doOnError(e => logger.error("Failed to register", e))
      .subscribe()


  def retrieveCommands(command_names: List[String], d4jMapper: JacksonResources): List[ApplicationCommandRequest] =
    command_names map (n => {
      val jsonContent = retrieveResourceAsString(s"commands/$n")
      d4jMapper.getObjectMapper().readValue(jsonContent, classOf[ApplicationCommandRequest])
    })

  def retrieveResourceAsString(filename: String): String =
    Source.fromResource(filename).mkString

