package com.thebookstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TheBookstoreApplication

fun main(args: Array<String>) {
	runApplication<TheBookstoreApplication>(*args)
}
