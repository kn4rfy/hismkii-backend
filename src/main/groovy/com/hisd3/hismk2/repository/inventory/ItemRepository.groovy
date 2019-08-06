package com.hisd3.hismk2.repository.inventory

import com.hisd3.hismk2.domain.inventory.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository extends JpaRepository<Item, UUID> {

}