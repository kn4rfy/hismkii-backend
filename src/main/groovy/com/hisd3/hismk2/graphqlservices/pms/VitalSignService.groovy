package com.hisd3.hismk2.graphqlservices.pms

import com.hisd3.hismk2.dao.pms.VitalSignDao
import com.hisd3.hismk2.domain.pms.VitalSign
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@GraphQLApi
class VitalSignService {
	
	@Autowired
	VitalSignDao vitalSignDao
	
	//============== All Queries ====================
	
	@GraphQLQuery(name = "vitalSigns", description = "Get all nurse notes")
	List<VitalSign> findAll() {
		return vitalSignDao.findAll()
	}
}
