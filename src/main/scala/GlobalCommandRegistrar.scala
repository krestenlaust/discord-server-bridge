package discordserverbridge

import discord4j.common.JacksonResources
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.RestClient
import discord4j.rest.service.ApplicationService
import scala.compiletime.ops.string
import scala.io.Source
import scala.io.BufferedSource
import scala.collection.JavaConverters._
import com.typesafe.scalalogging.Logger

class GlobalCommandRegistrar(val restClient: RestClient):
    val logger = Logger("GlobalCommandRegistrar")

    def registerCommands(commandnames: List[String]) =
        val appService = restClient.getApplicationService()
        val appId = restClient.getApplicationId().block()
        val d4jMapper = JacksonResources.create()

        val commands = retrieveCommands(commandnames, d4jMapper)

        appService.bulkOverwriteGlobalApplicationCommand(appId, commands.asJava)
            .doOnNext(cmd => logger.debug("Registered command: " ++ cmd.name()))
            .doOnError(e => logger.error("Failed to register", e))
            .subscribe()

    
    def retrieveCommands(commandnames: List[String], d4jMapper: JacksonResources) = 
        commandnames map(n => {
            val jsonContent = retrieveResourceAsString(s"commands/$n")
            d4jMapper.getObjectMapper().readValue(jsonContent, classOf[ApplicationCommandRequest])
        })
    
    def retrieveResourceAsString(filename: String) : String =
        Source.fromResource(filename).mkString

