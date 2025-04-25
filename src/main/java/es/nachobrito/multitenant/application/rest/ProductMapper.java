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

package es.nachobrito.multitenant.application.rest;

import es.nachobrito.multitenant.domain.model.category.CategoryRepository;
import jakarta.inject.Singleton;

/**
 * Translates Product domain entities to the Product representation in the REST layer.
 *
 * @author nacho
 */
@Singleton
public class ProductMapper {
  private final CategoryRepository categoryRepository;

  public ProductMapper(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  Product from(es.nachobrito.multitenant.domain.model.product.Product domainProduct) {
    var category = categoryRepository.get(domainProduct.getCategoryId()).orElseThrow();
    return new Product(
        domainProduct.getId().toUuid(),
        domainProduct.getName().value(),
        new Category(category.getId().toUuid(), category.getName().value()));
  }
}
