package cc.tkmr.avengers.api

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AvengersApiApplication

fun main(args: Array<String>) {
	Dotenv.configure().load()
	runApplication<AvengersApiApplication>(*args)
}