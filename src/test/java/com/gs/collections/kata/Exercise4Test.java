/*
 * Copyright 2015 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.kata;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.ArrayIterate;
import com.gs.collections.impl.utility.Iterate;

public class Exercise4Test extends CompanyDomainForKata
{
	/**
	 * Solve this without changing the return type of {@link Company#getSuppliers()}. Find the appropriate method on
	 * {@link ArrayIterate}.
	 */
	@Test
	public void findSupplierNames()
	{

		MutableList<String> supplierNames = FastList.newListWith(this.company.getSuppliers())
				.collect(Supplier::getName);

		MutableList<String> expectedSupplierNames = FastList.newListWith(
				"Shedtastic",
				"Splendid Crocks",
				"Annoying Pets",
				"Gnomes 'R' Us",
				"Furniture Hamlet",
				"SFD",
				"Doxins");
		Assert.assertEquals(expectedSupplierNames, supplierNames);
	}

	/**
	 * Create a {@link Predicate} for Suppliers that supply more than 2 items. Find the number of suppliers that
	 * satisfy that Predicate.
	 */
	@Test
	public void countSuppliersWithMoreThanTwoItems()
	{
		//    	c -> c.getCity().equals("London");
		Predicate<Supplier> moreThanTwoItems = c -> c.getItemNames().length > 2;
		int suppliersWithMoreThanTwoItems = FastList.newListWith(this.company.getSuppliers()).count(moreThanTwoItems);
		Assert.assertEquals("suppliers with more than 2 items", 5, suppliersWithMoreThanTwoItems);
	}

	/**
	 * Try to solve this without changing the return type of {@link Supplier#getItemNames()}.
	 */
	@Test
	public void whoSuppliesSandwichToaster()
	{
		// Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".
		Predicate<Supplier> suppliesToaster = c -> Arrays.asList(c.getItemNames()).contains("sandwich toaster");

		// Find one supplier that supplies toasters.
		//Supplier toasterSupplier = FastList.newListWith(this.company.getSuppliers()).;
		Supplier toasterSupplier = FastList.newListWith(this.company.getSuppliers()).detect(suppliesToaster);

		Assert.assertNotNull("toaster supplier", toasterSupplier);
		Assert.assertEquals("Doxins", toasterSupplier.getName());
	}

	@Test
	public void filterOrderValues()
	{
		List<Order> orders = this.company.getMostRecentCustomer().getOrders();
		/**
		 * Get the order values that are greater than 1.5.
		 */

		MutableList<Double> orderValues = new FastList<Order>(orders).collect(c -> c.getValue());

		MutableList<Double> filtered = orderValues.select( c -> c > 1.5);

		Assert.assertEquals(FastList.newListWith(372.5, 1.75), filtered);
	}

	@Test
	public void filterOrders()
	{
		List<Order> orders = this.company.getMostRecentCustomer().getOrders();
		/**
		 * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
		 */
		MutableList<Order> filtered =  new FastList<Order>(orders).select(c -> c.getValue() > 2.0);
		Assert.assertEquals(FastList.newListWith(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders())),
				filtered);
	}
}
