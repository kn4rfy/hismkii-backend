package com.hisd3.hismk2.graphqlservices.inventory

import com.hisd3.hismk2.dao.inventory.ReceivingDao
import com.hisd3.hismk2.dao.pms.PatientDao
import com.hisd3.hismk2.domain.inventory.ReceivingReport
import com.hisd3.hismk2.domain.inventory.ReceivingReportItem
import com.hisd3.hismk2.repository.inventory.ReceivingReportRepository
import io.leangen.graphql.annotations.GraphQLContext
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
@GraphQLApi
class ReceivingReportService {

    @Autowired
    ReceivingReportRepository receivingReportRepository

    @Autowired
    ReceivingDao receivingDao

    @GraphQLQuery(name = "receivingReport", description = "list of receiving report")
    List<ReceivingReport> receivingReportList (){
       return  receivingDao.findAll()
    }

    @GraphQLQuery(name = "receivingItems", description = "get list of items")
    Set<ReceivingReportItem> getItems(@GraphQLContext ReceivingReport receivingReport){
        return receivingDao.getReceivingItems(receivingReport)
    }
}
