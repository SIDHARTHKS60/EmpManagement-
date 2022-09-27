package com.employee.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee (
    @Id
    var empId:Int?,
    var empName:String,
    var empPosition:String
)