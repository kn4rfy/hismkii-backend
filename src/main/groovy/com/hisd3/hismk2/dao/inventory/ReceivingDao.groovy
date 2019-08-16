package com.hisd3.hismk2.dao.inventory

import com.fasterxml.jackson.databind.ObjectMapper
import com.hisd3.hismk2.domain.inventory.ReceivingReport
import com.hisd3.hismk2.domain.inventory.ReceivingReportItem
import com.hisd3.hismk2.repository.inventory.ReceivingReportItemRepository
import com.hisd3.hismk2.repository.inventory.ReceivingReportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Service
@Transactional
class ReceivingDao {
	@Autowired
	ReceivingReportRepository receivingReportRepository
	@Autowired
	ReceivingReportItemRepository receivingReportItemRepository
	@Autowired
	ObjectMapper objectMapper
	@PersistenceContext
	EntityManager entityManager
	
	List<ReceivingReport> findAll() {
		return receivingReportRepository.findAll()
	}
	
	Set<ReceivingReportItem> getReceivingItems(ReceivingReport receivingReport) {
		def mergedItem = entityManager.merge(receivingReport)
		mergedItem.receivingItems.size()
		return mergedItem.receivingItems as Set
	}
	
	ReceivingReport getReceivingReport(UUID id) {
		return receivingReportRepository.findById(id).get()
	}
	
	ReceivingReport save(ReceivingReport receivingReport) {
		receivingReport.receivingItems.each { ReceivingReportItem i ->
			if (!i.id) {
				i.receivingReport = receivingReport
				receivingReportItemRepository.save(i)
			}
		}
		return receivingReportRepository.save(receivingReport)
	}
	
	ReceivingReport deleteItems(UUID id, List<Map<String, Object>> items) {
		ReceivingReport receivingReport = receivingReportRepository.findById(id).get()
		
		items.each { it ->
			def receivingItem = objectMapper.convertValue(it, ReceivingReportItem)
			
			receivingReportItemRepository.delete(receivingItem)
		}
		
		return receivingReport
	}
	
	ReceivingReport addItems(UUID id, ReceivingReportItem item) {
		ReceivingReport receivingReport = receivingReportRepository.findById(id).get()
		
		item.receivingReport = receivingReport
		receivingReportItemRepository.save(item)
		
		return receivingReport
	}
}
