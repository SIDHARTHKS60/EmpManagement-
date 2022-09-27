package com.employee.demo.service

import com.employee.demo.model.Employee
import com.employee.demo.repository.EmpRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class EmployeeService(
    @Autowired
    private val empRepo: EmpRepository) {

    fun findAllEmployees(): Flux<Employee> {
        return empRepo.findAll()
    }


    fun addEmployee(emp: Employee): Mono<Employee> {
        return empRepo.save(emp)
    }

    fun updateEmployee(empId: Int, emp: Employee): Mono<Employee> {
        return empRepo.findById(empId)
            .flatMap {
                it.empId = emp.empId
                it.empName = emp.empName
                it.empPosition = emp.empPosition
                empRepo.save(it)
            }
    }

    fun findById(empId: Int): Mono<Employee> {
        return empRepo.findById(empId)
    }

    fun deleteById(empId: Int): Mono<Void> {
        return empRepo.deleteById(empId)
    }

}
