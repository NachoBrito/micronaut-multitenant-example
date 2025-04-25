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

package es.nachobrito.multitenant.infrastructure.data.product;

import es.nachobrito.multitenant.domain.model.category.CategoryId;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import es.nachobrito.multitenant.domain.model.product.ProductName;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TenantId;
import java.util.Objects;
import java.util.UUID;

/**
 * @author nacho
 */
@MappedEntity("PRODUCT")
public class JdbcProduct {
  @Id private UUID uuid;
  @TenantId private UUID merchantId;
  private UUID categoryId;
  private String name;

  static JdbcProduct of(Product product) {
    var entity = new JdbcProduct();
    entity.setName(product.getName().value());
    entity.setCategoryId(product.getCategoryId().toUuid());
    entity.setMerchantId(product.getMerchantId().toUuid());
    entity.setUuid(product.getId().toUuid());
    return entity;
  }

  UUID getUuid() {
    return uuid;
  }

  void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  UUID getMerchantId() {
    return merchantId;
  }

  void setMerchantId(UUID merchantId) {
    this.merchantId = merchantId;
  }

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  UUID getCategoryId() {
    return categoryId;
  }

  void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    JdbcProduct jdbcProduct = (JdbcProduct) o;
    return Objects.equals(getUuid(), jdbcProduct.getUuid());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUuid());
  }

  Product toDomainModel() {
    return Product.with(
        ProductId.of(this.uuid),
        MerchantId.of(this.merchantId),
        new ProductName(this.name),
        CategoryId.of(categoryId));
  }

  @Override
  public String toString() {
    return "JdbcProduct{"
        + "uuid="
        + uuid
        + ", merchantId="
        + merchantId
        + ", categoryId="
        + categoryId
        + ", name='"
        + name
        + '\''
        + '}';
  }
}
