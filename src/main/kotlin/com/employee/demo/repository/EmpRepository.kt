package com.employee.demo.repository

import com.employee.demo.model.Employee
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface EmpRepository:ReactiveCrudRepository<Employee,Int> {

}