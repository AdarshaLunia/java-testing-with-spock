package com.manning.spock.chapter6.stubs

import spock.lang.*

import com.manning.spock.chapter6.Basket
import com.manning.spock.chapter6.Product

@Subject(Basket.class)
class SequenceStubbingSpec extends spock.lang.Specification{


	def "Inventory is always checked in the last possible moment"() {
		given: "an basket and a TV"
		Product tv = new Product(name:"bravia",price:1200,weight:18)
		Basket basket = new Basket()

		and:"a warehouse with real time stock updates"
		WarehouseInventory inventory = Stub(WarehouseInventory)
		inventory.availableOfProduct( "bravia", _) >>> [true,false]
		basket.setWarehouseInventory(inventory)

		when: "user checks out the tv"
		basket.addProduct tv

		then: "order can be shipped right away"
		basket.canShipCompletely()
		
		when: "user wants another TV"
		basket.addProduct tv

		then: "order can be shipped right away"
		!basket.canShipCompletely()
	}
}

