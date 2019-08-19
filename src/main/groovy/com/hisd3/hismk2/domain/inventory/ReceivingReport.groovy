package com.hisd3.hismk2.domain.inventory

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.hisd3.hismk2.domain.AbstractAuditingEntity
import io.leangen.graphql.annotations.GraphQLQuery
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "inventory", name = "receiving_report")
@JsonIgnoreProperties(ignoreUnknown = true)
class ReceivingReport extends AbstractAuditingEntity implements Serializable{

	@GraphQLQuery
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "uuid")
	@Type(type = "pg-uuid")
	UUID id
	
	@GraphQLQuery
	@Column(name = "supplier", columnDefinition = 'varchar')
	String supplier
	
	@GraphQLQuery
	@Column(name = "ref_no", columnDefinition = 'varchar')
	String refNo
	
	@GraphQLQuery
	@Column(name = "receiving_department", columnDefinition = 'varchar')
	String receivingDepartment
	
	@GraphQLQuery
	@Column(name = "ref_qlty", columnDefinition = 'varchar')
	String refQlty
	
	@GraphQLQuery
	@Column(name = "qlty_inspection_date", nullable = true)
	LocalDateTime qltyInspectionDate
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receivingReport")
	Set<ReceivingReportItem> receivingItems = [] as Set
}
