package com.hisd3.hismk2.domain.ancillary

import com.hisd3.hismk2.domain.AbstractAuditingEntity
import com.hisd3.hismk2.domain.Department
import io.leangen.graphql.annotations.GraphQLQuery
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import org.hibernate.annotations.Type

import javax.persistence.*

@Entity
@Table(schema = "ancillary", name = "services")
class Service extends AbstractAuditingEntity {
	
	@GraphQLQuery
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "uuid")
	@Type(type = "pg-uuid")
	UUID id
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department", referencedColumnName = "id")
	Department department
	
	@GraphQLQuery
	@Column(name = "servicename", columnDefinition = "varchar")
	String serviceName
	
	@GraphQLQuery
	@Column(name = "service_code", columnDefinition = "varchar")
	String serviceCode
	
	@GraphQLQuery
	@Column(name = "process_code", columnDefinition = "varchar")
	String processCode
	
	@GraphQLQuery
	@Column(name = "description", columnDefinition = "varchar")
	String description
	
	@GraphQLQuery
	@Column(name = "category", columnDefinition = "varchar")
	String category
	
	@GraphQLQuery
	@Column(name = "`package`", columnDefinition = "boolean")
	Boolean packageType
	
	@GraphQLQuery
	@Column(name = "diagnostic_service", columnDefinition = "boolean")
	Boolean diagnostic
	
	@GraphQLQuery
	@Column(name = "available", columnDefinition = "boolean")
	Boolean available
	
	@GraphQLQuery
	@Column(name = "notes", columnDefinition = "varchar")
	String notes
	
	@GraphQLQuery
	@Column(name = "base_price", columnDefinition = "numeric")
	BigDecimal basePrice
	
	@GraphQLQuery
	@Column(name = "cost", columnDefinition = "numeric")
	BigDecimal cost
	
	@GraphQLQuery
	@Column(name = "markup", columnDefinition = "numeric")
	BigDecimal markup
	
	@GraphQLQuery
	@Column(name = "readersfee", columnDefinition = "numeric")
	BigDecimal readersFee
	
}
