package com.hisd3.hismk2.repository.inventory

import com.hisd3.hismk2.domain.inventory.PurchaseOrder
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {

}