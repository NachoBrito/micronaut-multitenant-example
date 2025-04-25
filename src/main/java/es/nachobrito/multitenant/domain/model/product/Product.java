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

import es.nachobrito.multitenant.domain.model.Entity;
import es.nachobrito.multitenant.domain.model.category.Category;
import es.nachobrito.multitenant.domain.model.category.CategoryId;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import java.util.Objects;

/**
 * @author nacho
 */
public class Product implements Entity<ProductId> {
  private final ProductId id;
  private MerchantId merchantId;
  private CategoryId categoryId;
  private ProductName name;

  private Product(ProductId id) {
    this.id = id;
  }

  public static Product with(
      ProductId productId, Merchant merchant, ProductName name, Category category) {
    return with(productId, merchant.getId(), name, category.getId());
  }

  public static Product with(
      ProductId productId, MerchantId merchantId, ProductName name, CategoryId categoryId) {
    var product = new Product(productId);
    product.setMerchantId(merchantId);
    product.setCategoryId(categoryId);
    product.setName(name);
    return product;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(getId(), product.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public ProductId getId() {
    return id;
  }

  public MerchantId getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(MerchantId merchantId) {
    this.merchantId = merchantId;
  }

  public CategoryId getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(CategoryId categoryId) {
    this.categoryId = categoryId;
  }

  public ProductName getName() {
    return name;
  }

  public void setName(ProductName name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", merchantId="
        + merchantId
        + ", categoryId="
        + categoryId
        + ", name="
        + name
        + '}';
  }
}
