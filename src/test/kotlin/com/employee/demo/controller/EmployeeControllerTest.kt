package com.employee.demo.controller

import com.employee.demo.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient


@WebFluxTest(EmployeeController::class)
@AutoConfigureWebTestClient
class EmployeeControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var userService: EmployeeService

}