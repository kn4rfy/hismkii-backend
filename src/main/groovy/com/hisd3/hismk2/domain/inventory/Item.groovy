package com.hisd3.hismk2.domain.inventory

import io.leangen.graphql.annotations.GraphQLQuery
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "inventory", name = "item")
class Item {

    @GraphQLQuery
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    UUID id

    @GraphQLQuery
    @Column(name = "stock_code")
    String stockCode

    @GraphQLQuery
    @Column(name = "desc_long")
    String descLong

    @GraphQLQuery
    @Column(name = "unit_measure")
    String unitMeasure

    @GraphQLQuery
    @Column(name = "minimum_level")
    String minimumLevel

    @GraphQLQuery
    @Column(name = "economic_order_qty")
    BigDecimal economicOrderQty

    @GraphQLQuery
    @Column(name = "active")
    Boolean active

    @GraphQLQuery
    @Column(name = "stock_classification")
    String stockClassification

    @GraphQLQuery
    @Column(name = "indication_code")
    BigDecimal indicationCode

    @GraphQLQuery
    @Column(name = "brand")
    String brand

    @GraphQLQuery
    @Column(name = "generic_name")
    String genericName

    @GraphQLQuery
    @Column(name = "is_medicine")
    Boolean isMedicine

    @GraphQLQuery
    @Column(name = "indication_desc")
    String indicationDesc

    @GraphQLQuery
    @Column(name = "medication_description")
    String medicationDescription

    @GraphQLQuery
    @Column(name = "sku")
    String sku

    @GraphQLQuery
    @Column(name = "route")
    String route

    @GraphQLQuery
    @Column(name = "package_name")
    String packageName

    @GraphQLQuery
    @Column(name = "package_content_count")
    BigDecimal packageContentCount

    @GraphQLQuery
    @Column(name = "dose")
    BigDecimal dose

    @GraphQLQuery
    @Column(name = "dose_unit")
    BigDecimal doseUnit

    @GraphQLQuery
    @Column(name = "s2_drug")
    Boolean s2Drug

    @GraphQLQuery
    @Column(name = "form")
    String form

    @GraphQLQuery
    @Column(name = "vatable")
    String vatable

    @GraphQLQuery
    @Column(name = "preparation")
    String preparation

    @GraphQLQuery
    @Column(name = "distributor")
    String distributor

    @GraphQLQuery
    @Column(name = "advocate")
    String advocate

    @GraphQLQuery
    @Column(name = "category")
    String category

    @GraphQLQuery
    @Column(name = "sub_category")
    String subCategory

    @GraphQLQuery
    @Column(name = "sub_sub_category")
    String subSubCategory

    @GraphQLQuery
    @Column(name = "formulary_item")
    String formularyItem

    @GraphQLQuery
    @Column(name = "fluid")
    Boolean fluid

    @GraphQLQuery
    @Column(name = "gas")
    Boolean gas

    @GraphQLQuery
    @Column(name = "direct_asset")
    Boolean directAsset

    @GraphQLQuery
    @Column(name = "consignor")
    String consignor

    @GraphQLQuery
    @Column(name = "consignment_item")
    Boolean consignmentItem

    @GraphQLQuery
    @Column(name = "consignor_name")
    String consignorName

    @GraphQLQuery
    @Column(name = "comlogic_medicine_code")
    String comlogicMedicineCode

    @GraphQLQuery
    @Column(name = "comlogic_medicine_generic_code")
    String comlogicMedicineGenericCode

    @GraphQLQuery
    @Column(name = "created_by")
    String created_by

    @GraphQLQuery
    @Column(name = "created_date", nullable = true)
    LocalDateTime created_date

    @GraphQLQuery
    @Column(name = "last_modified_by")
    String last_modified_by

    @GraphQLQuery
    @Column(name = "last_modified_date", nullable = true)
    LocalDateTime last_modified_date
}