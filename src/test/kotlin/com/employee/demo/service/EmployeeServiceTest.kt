package com.employee.demo.service

import com.employee.demo.model.Employee
import com.employee.demo.repository.EmployeeRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class EmployeeServiceTest {
    // mocking the repository layer response
    val emp1 = Employee(999, "Aaaaa A A",  "Manager A")
    val emp2 = Employee(888, "Bbbbb B B",  "Manager B")

    private val employeeRepository = mockk<EmployeeRepository>() {

        every {
            findAll()
        } returns Flux.just(emp1, emp2)

        every {
            findById(999)
        }returns Mono.just(emp1)
    }

    private val userService = EmployeeService(employeeRepository)

    @Test
    fun `should return users when findAllUsers  method is called`() {

        val firstUser = userService.findAllEmployees().blockFirst()
        val secondUser = userService.findAllEmployees().blockLast()

        if (firstUser != null) {
            firstUser shouldBe emp1
        }
        if (secondUser != null) {
            secondUser shouldBe emp2
        }
    }

    @Test
    fun `test adding User`() {
        val user1 = Employee(999, "Aaaaa A A",  "Manager A")

        every{
            employeeRepository.save(user1)
        }returns Mono.just(user1)

        val addedUser = userService.addEmployee(user1).block()

        addedUser shouldBe user1
    }

    @Test
    fun `test Find User By Id`() {

        val result=userService.findById(999).block()

        result shouldBe emp1

    }

    @Test
    fun `delete User By Id`() {

        every{
            employeeRepository.deleteById(999)
        }returns Mono.empty()
    }

    @Test
    fun `test update User`() {

        // val user1 = User(999,"Rahul K",9999999999,"Aaaaa@aaa")
        every{
            employeeRepository.save(emp1)
        }returns Mono.just(emp1)
        val updatedUser = userService.updateEmployee(999,emp1).block()

        updatedUser shouldBe emp1
    }
}
