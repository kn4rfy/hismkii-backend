package com.hisd3.hismk2.graphqlservices

import com.fasterxml.jackson.databind.ObjectMapper
import com.hisd3.hismk2.domain.Department
import com.hisd3.hismk2.domain.bms.Room
import com.hisd3.hismk2.repository.DepartmentRepository
import com.hisd3.hismk2.repository.bms.RoomRepository
import com.hisd3.hismk2.services.GeneratorService
import groovy.transform.TypeChecked
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TypeChecked
@Component
@GraphQLApi
class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository
	
	@Autowired
	private RoomRepository roomRepository
	
	@Autowired
	GeneratorService generatorService
	
	@Autowired
	ObjectMapper objectMapper
	
	//============== All Queries ====================
	
	@GraphQLQuery(name = "departments", description = "Get All Departments")
	List<Department> findAll() {
		departmentRepository.findAll()
	}
	
	@GraphQLQuery(name = "department", description = "Get Department By Id")
	Department findById(@GraphQLArgument(name = "id") UUID id) {
		return departmentRepository.findById(id).get()
	}
	
	@GraphQLQuery(name = "departmentsByFilter", description = "Get Departments by filter")
	List<Department> departmentsByFilter(@GraphQLArgument(name = "filter") String filter) {
		departmentRepository.departmentsByFilter(filter)
	}
	
	@GraphQLQuery(name = "departmentsWithRooms", description = "Get Departments with Rooms")
	List<Department> getDepartmentWithRooms() {
		departmentRepository.getDepartmentWithRooms()
	}
	
	@GraphQLQuery(name = "parentDepartments", description = "Get Parent Departments")
	List<Department> getParentDepartments() {
		departmentRepository.getParentDepartments()
	}
	
	@GraphQLQuery(name = "isDepartmentCodeUnique", description = "Check if departmentCode exists")
	Boolean findOneByDepartmentCode(@GraphQLArgument(name = "departmentCode") String departmentCode) {
		return !departmentRepository.findOneByDepartmentCode(departmentCode)
	}
	
	@GraphQLQuery(name = "isDepartmentNameUnique", description = "Check if departmentName exists")
	Boolean findOneByDepartmentName(@GraphQLArgument(name = "departmentName") String departmentName) {
		return !departmentRepository.findOneByDepartmentName(departmentName)
	}
	
	@GraphQLQuery(name = "availableDepartmentRooms", description = "Get all available Department Rooms")
	List<Room> getAvailableRoomsByDepartment(@GraphQLContext Department department) {
		return roomRepository.getAvailableRoomsByDepartment(department.id).sort { it.roomName }
	}
	
	@GraphQLQuery(name = "departmentRooms", description = "Get all Department Rooms")
	List<Room> getRoomsByDepartment(@GraphQLContext Department department) {
		return roomRepository.getRoomsByDepartment(department.id).sort { it.roomName }
	}
}