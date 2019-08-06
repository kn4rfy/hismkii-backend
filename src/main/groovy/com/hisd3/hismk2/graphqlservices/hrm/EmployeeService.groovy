package com.hisd3.hismk2.graphqlservices.hrm

import com.fasterxml.jackson.databind.ObjectMapper
import com.hisd3.hismk2.dao.UserDao
import com.hisd3.hismk2.dao.hrm.EmployeeDao
import com.hisd3.hismk2.dao.pms.PatientDao
import com.hisd3.hismk2.domain.User
import com.hisd3.hismk2.domain.hrm.Employee
import com.hisd3.hismk2.domain.pms.Case
import com.hisd3.hismk2.domain.pms.Patient
import com.hisd3.hismk2.services.GeneratorService
import com.hisd3.hismk2.services.GeneratorType
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.execution.relay.Page
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@GraphQLApi
class EmployeeService {
	
	@Autowired
	EmployeeDao employeeDao

	@Autowired
	UserDao userDao

	@Autowired
	GeneratorService generatorService
	
	@Autowired
	ObjectMapper objectMapper
	
	//============== All Queries ====================
	
	@GraphQLQuery(name = "employees", description = "Get All Employees")
	Set<Employee> findAll() {
		employeeDao.findAll()
	}
	
	@GraphQLQuery(name = "employee", description = "Get Employee By Id")
	Employee findById(@GraphQLArgument(name = "id") String id) {
		
		return employeeDao.findById(id)
	}
	
	@GraphQLQuery(name = "employeesByPage", description = "Get All Employees By Page")
	Page<Employee> getAllEmployeesByPage(
			@GraphQLArgument(name = "first") int first,
			@GraphQLArgument(name = "after") String after = "0"
	) {

		employeeDao.getEmployeeRelayPage(first, Integer.parseInt(after))
	}
	
	@GraphQLQuery(name = "employeeCases", description = "Get All Employee Cases")
	Set<Case> getCases(@GraphQLContext Employee employee) {
		
		return employeeDao.getEmployeeCases(employee)
	}
	
	//============== All Mutations ====================
	
	@GraphQLMutation
	Employee upsertEmployee(
			@GraphQLArgument(name = "id") String id,
			@GraphQLArgument(name = "fields") Map<String, Object> fields
	) {
		
		if (id) {
			def employee = employeeDao.findById(id)
			objectMapper.updateValue(employee, fields)

			return employeeDao.save(employee)
		} else {
			def employee = objectMapper.convertValue(fields, Employee)

			def userId = fields["userId"]

			User user = userDao.findById(userId as String)

			employee.user = user

			employee.employeeNo = generatorService.getNextValue(GeneratorType.PATIENT_NO) { Long no ->
				StringUtils.leftPad(no.toString(), 5, "0")
			}

			return employeeDao.save(employee)
		}
	}
}
