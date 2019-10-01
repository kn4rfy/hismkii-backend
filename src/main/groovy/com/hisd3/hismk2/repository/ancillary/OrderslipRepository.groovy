package com.hisd3.hismk2.repository.ancillary

import com.hisd3.hismk2.domain.ancillary.Orderslip
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderslipRepository extends JpaRepository<Orderslip, UUID> {

	@Query(
			value = "Select o from Orderslip o where o.parentCase.id =:id"
	)
	List<Orderslip> findByCase(@Param("id") UUID id)


	@Query(value = '''Select o from Orderslip o where 
            lower(o.parentCase.patient.fullName) like lower(concat('%',:filter,'%')) and o.parentCase.registryType =:patientType
            ''')

	List<Orderslip> filterByPatientType(@Param("patientType") String patientType,@Param("filter") String filter)
	@Query(
			value = "Select o from Orderslip o where o.parentCase.id =:id and o.service.department.id =:departmentId"
	)
	List<Orderslip> findByCaseAndDepartment(@Param("id") UUID id, @Param("departmentId") UUID departmentId)

	@Query(
			value = "Select o from Orderslip o where  o.service.department.id =:id"
	)
	List<Orderslip> findByDepartment(@Param("id") UUID id)

}
