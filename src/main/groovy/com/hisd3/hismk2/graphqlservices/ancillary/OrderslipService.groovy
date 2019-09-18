package com.hisd3.hismk2.graphqlservices.ancillary

import com.fasterxml.jackson.databind.ObjectMapper
import com.hisd3.hismk2.dao.ancillary.OrderslipDao
import com.hisd3.hismk2.dao.ancillary.dto.DiagnosticsResultsDto
import com.hisd3.hismk2.domain.ancillary.Orderslip
import com.hisd3.hismk2.services.GeneratorService
import groovy.transform.TypeChecked
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TypeChecked
@Component
@GraphQLApi
class OrderslipService {

	@Autowired
	OrderslipDao orderslipDao

	@Autowired
	GeneratorService generatorService

	@Autowired
	ObjectMapper objectMapper

	//============== All Queries ====================

	@GraphQLQuery(name = "orderslips", description = "Get All Orderslips")
	List<Orderslip> findAll() {
		orderslipDao.findAll()
	}

	@GraphQLQuery(name = "orderslipsByDepartment", description = "Get All Orderslips by Department")
	List<Orderslip> findByDepartment(
			@GraphQLArgument(name = "id") String id = ""
	) {

		return orderslipDao.findByDepartment(id)
	}

	@GraphQLQuery(name = "orderslipsByCase", description = "Get All Orderslips by case")
	List<DiagnosticsResultsDto> findByCase(
			@GraphQLArgument(name = "id") String id
	) {

		return orderslipDao.findByCase(id)
	}

	@GraphQLQuery(name = "orderslipsByCaseAndDepartment", description = "Get All Orderslips filter by case and department")
	List<DiagnosticsResultsDto> findByCaseAndDeparment(
			@GraphQLArgument(name = "id") String id,
			@GraphQLArgument(name = "departmentId") String departmentId
	) {
		return orderslipDao.findByCaseAndDepartment(id, departmentId)

	}

	//============== All Mutations ====================

	@GraphQLMutation
	List<Orderslip> addOrderslip(
			@GraphQLArgument(name = "fields") Map<String, Object> fields
	) {
		println(fields)

		List<Orderslip> orderslips = []
		def orders
		orders = fields.get("requested") as ArrayList<Orderslip>
		orders.each {
			it ->
				def order = objectMapper.convertValue(it, Orderslip)
				order.submittedViaHl7 = false
				order.posted = false
				order.status = "NEW"
				order.deleted = false
				orderslips.add(order)
		}

		return orderslipDao.addOrderslip(orderslips)
	}
}

