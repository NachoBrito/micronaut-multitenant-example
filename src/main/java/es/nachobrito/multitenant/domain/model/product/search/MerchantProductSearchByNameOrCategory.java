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

package es.nachobrito.multitenant.domain.model.product.search;

import es.nachobrito.multitenant.domain.model.category.CategoryId;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.product.Product;
import jakarta.validation.constraints.NotNull;

/**
 * @author nacho
 */
record MerchantProductSearchByNameOrCategory(
    @NotNull MerchantId merchantId, String name, CategoryId category) implements ProductSearch {
  public boolean matches(Product product) {
    var matchesName = name == null || product.getName().contains(name);
    var matchesCategory = category == null || product.getCategoryId().equals(category);
    var matchesMerchantId = merchantId.equals(product.getMerchantId());
    return matchesMerchantId && (matchesName || matchesCategory);
  }

  @Override
  public ProductSearch withMerchant(MerchantId merchantId) {
    return new MerchantProductSearchByNameOrCategory(merchantId, this.name, this.category);
  }
}
