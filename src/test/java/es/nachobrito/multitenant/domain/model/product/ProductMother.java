/*
 *    Copyright 2025 Nacho Brito
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package es.nachobrito.multitenant.domain.model.product;

import com.github.javafaker.Faker;
import es.nachobrito.multitenant.domain.model.category.Category;
import es.nachobrito.multitenant.domain.model.category.CategoryMother;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantMother;

/**
 * Generates test objects
 *
 * @see <a href="https://martinfowler.com/bliki/ObjectMother.html">The ObjectMother pattern</a>
 * @author nacho
 */
public class ProductMother {

  /**
   * Creates a random product for testing, with the provided merchant and category.
   *
   * @param merchant the merchant
   * @param category the category
   * @return the new Product
   */
  public static Product randomInMerchant(Merchant merchant, Category category) {
    var productId = ProductId.newRandom();
    var name = new ProductName(Faker.instance().commerce().productName());
    return Product.with(productId, merchant, name, category);
  }

  public static Product inMerchant1() {
    return Product.with(
        new ProductId("2d557797-a4b3-42e9-a15b-b163fb10c7a4"),
        MerchantMother.merchant1().getId(),
        new ProductName("Car"),
        CategoryMother.category1().getId());
  }

  public static Product inMerchant2() {
    return Product.with(
        new ProductId("87982202-54bc-41a8-b239-4d7dad5d70fd"),
        MerchantMother.merchant2().getId(),
        new ProductName("Bicycle"),
        CategoryMother.category1().getId());
  }
}
