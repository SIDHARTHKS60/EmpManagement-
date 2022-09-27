package com.employee.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmployeeManagementApplication

fun main(args: Array<String>) {
	runApplication<EmployeeManagementApplication>(*args)
}
