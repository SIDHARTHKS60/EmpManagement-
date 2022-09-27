package com.employee.demo.controller

import com.employee.demo.model.Employee
import com.employee.demo.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CrossOrigin(origins = ["http://localhost:3002/"])
@RestController
@RequestMapping("api/")
class EmployeeController(@Autowired val empSer: EmployeeService) {

    @PostMapping("/emp/add")
    fun addEmployee(@RequestBody emp: Employee): Mono<Employee> {
        return empSer.addEmployee(emp)
    }

    @GetMapping("/emp/list")
    fun employeeList(): Flux<Employee> {
        return empSer.findAllEmployees()
    }

    @GetMapping("/emp/find/{empId}")
     fun findEmployee(@PathVariable("empId") empId: Int, @RequestBody emp: Employee): Mono<Employee> {
        return empSer.findById(empId)
    }

    @PutMapping("/emp/update/{empId}")
    fun updateEmployeeById(@PathVariable("empId") empId: Int, @RequestBody emp: Employee): Mono<Employee> {
        return empSer.updateEmployee(empId, emp)
    }

    @DeleteMapping("/emp/delete/{empId}")
    fun removeById(@PathVariable("empId") empId: Int): Mono<Void> {
        return empSer.deleteById(empId)
    }

}