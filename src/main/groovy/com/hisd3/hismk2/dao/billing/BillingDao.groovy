package com.hisd3.hismk2.dao.billing

import com.fasterxml.jackson.databind.ObjectMapper
import com.hisd3.hismk2.domain.billing.Billing
import com.hisd3.hismk2.domain.billing.BillingItem
import com.hisd3.hismk2.repository.DepartmentRepository
import com.hisd3.hismk2.repository.ancillary.ServiceRepository
import com.hisd3.hismk2.repository.billing.BillingItemRepository
import com.hisd3.hismk2.repository.billing.BillingRepository
import com.hisd3.hismk2.repository.inventory.ItemRepository
import com.hisd3.hismk2.repository.pms.CaseRepository
import com.hisd3.hismk2.repository.pms.PatientRepository
import com.hisd3.hismk2.services.GeneratorService
import com.hisd3.hismk2.services.GeneratorType
import groovy.transform.TypeChecked
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.Instant

@TypeChecked
@Service
@Transactional
class BillingDao {
	@Autowired
	BillingRepository billingRepository
	
	@Autowired
	BillingItemRepository billingItemRepository
	
	@Autowired
	GeneratorService generatorService
	
	@Autowired
	DepartmentRepository departmentRepository
	
	@Autowired
	ServiceRepository serviceRepository
	
	@Autowired
	ItemRepository itemRepository
	
	@Autowired
	PatientRepository patientRepository
	
	@Autowired
	CaseRepository caseRepository
	
	@Autowired
	ObjectMapper objectMapper
	
	List<Billing> getBillingByPatient(UUID patientid) {
		billingRepository.getByPatientId(patientid)
	}
	
	List<BillingItem> getBillingItemsByBill(UUID billingId) {
		billingItemRepository.getBillingItemsByBill(billingId)
	}
	
	BillingItem toggleBillingItem(String billingItemId) {
		def billingItem = billingItemRepository.findById(UUID.fromString(billingItemId)).get()
		
		if (billingItem.status == 'INACTIVE')
			billingItem.status = 'ACTIVE'
		else
			billingItem.status = 'INACTIVE'
		
		billingItemRepository.save(billingItem)
	}
	
	Billing saveBillingItems(UUID patientId, UUID caseId, UUID billingId, List<Map<String, Object>> billingItems) {
		
		if (billingId) {
			
			//.get(0) means that we get the first active billing result
			def billingDto = billingRepository.findById(billingId).get()
			
			if (billingItems) {
				billingItems.each {
					Map<String, Object> billingItem ->
						
						def billingItemDto = new BillingItem()
						billingItemDto.billing = billingDto
						
						if (billingItem.itemType == 'SERVICE') {
							
							def item = serviceRepository.findById(UUID.fromString(billingItem.get("item") as String)).get()
							Random rnd = new Random()
							
							billingItemDto.recordNo = rnd.nextInt(999999)
							billingItemDto.qty = billingItem.get("qty", 0) as Integer
							
							billingItemDto.description = item.serviceName
							billingItemDto.price = item.basePrice
							billingItemDto.status = 'ACTIVE'
							
							def department = billingItem.get("department", "") as String
							
							if (department != null && department != "") {
								billingItemDto.department = departmentRepository.findById(
										UUID.fromString(department)
								).get()
							} else {
								billingItemDto.department = departmentRepository.findById(
										item.department.id
								).get()
							}
						} else if (billingItem.itemType == 'INVENTORY') {
							
							def item = itemRepository.findById(UUID.fromString(billingItem.get("item") as String)).get()
							Random rnd = new Random()
							
							billingItemDto.recordNo = rnd.nextInt(999999)
							billingItemDto.qty = billingItem.get("qty", 0) as Integer
							
							billingItemDto.description = item.descLong
							billingItemDto.price = item.basePrice
							billingItemDto.status = 'ACTIVE'
							
							def department = billingItem.get("department", "") as String
							
							billingItemDto.department = departmentRepository.findById(
									UUID.fromString(department)
							).get()
						}
						
						billingItemRepository.save(billingItemDto)
				}
			}
			
			return billingDto
		} else {
			def newBilling = new Billing()
			def patientDto = patientRepository.findById(patientId).get()
			newBilling.patient = patientDto
			newBilling.patientCase = caseRepository.findById(caseId).get()
			newBilling.entryDateTime = Instant.now()
			newBilling.status = "ACTIVE"
			newBilling.billingNo = generatorService.getNextValue(GeneratorType.RR_NO) { Long no ->
				StringUtils.leftPad(no.toString(), 5, "0")
			}
			
			def newBilling2 = billingRepository.save(newBilling)
			
			if (billingItems) {
				billingItems.each {
					Map<String, Object> billingItem ->
						
						def billingItemDto = new BillingItem()
						billingItemDto.billing = newBilling
						
						def item = serviceRepository.findById(UUID.fromString(billingItem.get("item") as String)).get()
						Random rnd = new Random()
						
						billingItemDto.recordNo = rnd.nextInt(999999)
						billingItemDto.qty = billingItem.get("qty", 0) as Integer
						
						if (billingItem.itemType == 'SERVICE') {
							billingItemDto.description = item.serviceName
							billingItemDto.price = item.basePrice
							billingItemDto.status = 'ACTIVE'
							
							def department = billingItem.get("department", "") as String
							
							if (department != null && department != "") {
								billingItemDto.department = departmentRepository.findById(
										UUID.fromString(department)
								).get()
							} else {
								billingItemDto.department = departmentRepository.findById(
										item.department.id
								).get()
							}
						}
						
						billingItemRepository.save(billingItemDto)
				}
				
				return newBilling2
			}
		}
	}
}