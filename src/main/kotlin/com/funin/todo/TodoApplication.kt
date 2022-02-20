package com.funin.todo

import com.funin.todo.TodoApplication.Companion.APPLICATION_LOCATIONS
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoApplication {

	companion object {
		const val APPLICATION_LOCATIONS = "spring.config.location=" +
				"classpath:application.yml," +
				"/app/config/funin-todo/real-application.yml"
	}

}

fun main(args: Array<String>) {
	SpringApplicationBuilder(TodoApplication::class.java)
		.properties(APPLICATION_LOCATIONS)
		.run(*args)
}
