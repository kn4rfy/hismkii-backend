package com.hisd3.hismk2.graphqlservices.pms

import com.hisd3.hismk2.domain.pms.Administration
import com.hisd3.hismk2.domain.pms.Medication
import com.hisd3.hismk2.repository.pms.AdministrationRepository
import com.hisd3.hismk2.repository.pms.MedicationRepository
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
class MedicationService {
	
	@Autowired
	private MedicationRepository medicationRepository
	
	@Autowired
	private AdministrationRepository administrationRepository
	
	//============== All Queries ====================
	
	@GraphQLQuery(name = "medications", description = "Get all Medications")
	List<Medication> findAll() {
		return medicationRepository.findAll().sort { it.entryDateTime }
	}
	
	@GraphQLQuery(name = "medication", description = "Get Medication By Id")
	Medication findById(@GraphQLArgument(name = "id") UUID id) {
		return medicationRepository.findById(id).get()
	}
	
	@GraphQLQuery(name = "medicationsByCase", description = "Get all Medications by Case Id")
	List<Medication> getMedicationsByCase(@GraphQLArgument(name = "caseId") UUID caseId) {
		return medicationRepository.getMedicationsByCase(caseId).sort { it.entryDateTime }
	}
	
	@GraphQLQuery(name = "medicationsByCaseAndType", description = "Get all Medications by Case Id and Type")
	List<Medication> getMedicationsByCaseAndType(@GraphQLArgument(name = "caseId") UUID caseId, @GraphQLArgument(name = "type") String type) {
		return medicationRepository.getMedicationsByCaseAndType(caseId, type).sort { it.entryDateTime }
	}
	
	@GraphQLQuery(name = "medicationAdministrations", description = "Get all Medication Administrations")
	List<Administration> getAdministrations(@GraphQLContext Medication medication) {
		return administrationRepository.getMedicationAdministrations(medication.id).sort { it.entryDateTime }
	}
}
