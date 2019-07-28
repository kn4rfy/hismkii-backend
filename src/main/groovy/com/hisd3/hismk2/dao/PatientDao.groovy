package com.hisd3.hismk2.dao

import com.hisd3.hismk2.domain.Patient
import com.hisd3.hismk2.domain.PatientCase
import com.hisd3.hismk2.repository.PatientRepository
import com.hisd3.hismk2.utils.OffsetBasedPageRequest
import io.leangen.graphql.execution.relay.Page
import io.leangen.graphql.execution.relay.generic.PageFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Service
@Transactional
class PatientDao {

    @Autowired
    private PatientRepository patientRepository


    @PersistenceContext
    EntityManager entityManager

    List<Patient> getAllPatients(){
        return patientRepository.findAll()
    }

    Patient findById(String id){
        return patientRepository.findById(UUID.fromString(id)).get()
    }

    Page<Patient> getPatientRelayPage(int first, int offset){
      def pageable=  patientRepository.findAll(new OffsetBasedPageRequest(offset,first))

        PageFactory.createOffsetBasedPage(pageable.content, pageable.totalElements, offset)

    }

    Set<PatientCase> getPatientCases(Patient patient){

        def epatient = entityManager.merge(patient)
        epatient.patientCases.size()
        return epatient.patientCases as Set
    }


    Patient save(Patient patient){
        patientRepository.save(patient)
    }


}