package com.hisd3.hismk2.dao.pms

import com.hisd3.hismk2.domain.pms.PatientCase
import com.hisd3.hismk2.domain.pms.VitalSign
import com.hisd3.hismk2.repository.pms.VitalSignRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@Transactional
class VitalSignDao {
	
	@Autowired
	private VitalSignRepository vitalSignRepository
	
	@PersistenceContext
	EntityManager entityManager
	
	List<VitalSign> getVitalSigns() {
		return vitalSignRepository.getVitalSigns()
	}
	
	Set<VitalSign> getVitalSignsByPatientCase(PatientCase patientCase) {
		
		def mergedPatientCase = entityManager.merge(patientCase)
		mergedPatientCase.vitalSigns.size()
		return mergedPatientCase.vitalSigns as Set
	}
}
