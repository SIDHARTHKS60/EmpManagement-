package com.employee.demo.controller

import com.employee.demo.model.Employee
import com.employee.demo.service.EmployeeService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@WebFluxTest(EmployeeController::class)
@AutoConfigureWebTestClient
class EmployeeControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var employeeService: EmployeeService
    @Test
    fun `should return list of employees`() {
        val emp1 = Employee(999, "Aaaaa A A","Manager A")
        val emp2 = Employee(888, "Bbbbb B B", "Manager B")

        val expectedResult = listOf(
            mapOf(
                "empId" to 999,
                "empName" to "Aaaaa A A",
                "empPosition" to "Manager A"
            ),
            mapOf(
                "empId" to 888,
                "empName" to "Bbbbb B B",
                "empPosition" to "Manager B"
            ),
        )

        every {
            employeeService.findAllEmployees()
        } returns Flux.just(emp1,emp2)

        val response = client.get()
            .uri("/api/emp/list")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody


        response.blockFirst() shouldBe expectedResult[0]
        //response.blockLast() shouldBe expectedResult[1]

        verify(exactly = 1) {
            employeeService.findAllEmployees()
        }
    }

    @Test
    fun `should create user when create api is being called`() {

        val exepectedResponse = mapOf(
            "empId" to 999,
            "empName" to "Aaaaa A A",
            "empPosition" to "Manager A")

        val emp1 = Employee(999, "Aaaaa A A","Manager A")

        every {
            employeeService.addEmployee(emp1)
        } returns Mono.just(emp1)

        val response = client.post()
            .uri("/api/emp/add")
            .bodyValue(emp1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            employeeService.addEmployee(emp1)
        }
    }

    @Test
    fun `should be able to update the user`() {

        val expectedResult = mapOf(
            "empId" to 999,
            "empName" to "Aaaaa A A",
            "empPosition" to "Manager A")

        val emp1 = Employee(999, "Aaaaa A A","Manager A")

        every {
            employeeService.updateEmployee(999,emp1)
        } returns Mono.just(emp1)

        val response = client.put()
            .uri("/api/emp/update/999")
            .bodyValue(emp1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            employeeService.updateEmployee(999,emp1)
        }
    }

    @Test
    fun `should be able to delete the user`() {

        val expectedResult = mapOf(
            "empId" to 999,
            "empName" to "Aaaaa A A",
            "empPosition" to "Manager A")

        val emp1 = Employee(999, "Aaaaa A A","Manager A")

        every {
            employeeService.deleteById(999) } returns  Mono.empty()

        val response = client.delete()
            .uri("/api/emp/delete/999")
            .exchange()
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            employeeService.deleteById(999)
        }
    }

    @Test
    fun `should return a single user`() {

        val expectedResult = mapOf(
            "empId" to 999,
            "empName" to "Aaaaa A A",
            "empPosition" to "Manager A")

        val emp1 = Employee(999, "Aaaaa A A","Manager A")

        every {
            employeeService.findById(999)
        } returns Mono.just(emp1)

        val response = client.get()
            .uri("/api/emp/find/999")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            employeeService.findById(999)
        }

    }
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun employeeService() = mockk<EmployeeService>()
    }

}