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

package es.nachobrito.multitenant.application;

import es.nachobrito.multitenant.domain.model.category.CategoryId;
import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import es.nachobrito.multitenant.domain.model.product.search.ProductSearch;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * Implements application logic for Product entities
 *
 * @author nacho
 */
@Singleton
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Optional<Product> getProduct(ProductId productId) {
    return productRepository.get(productId);
  }

  public List<Product> searchProducts(String nameContains, String categoryId) {
    var search =
        ProductSearch.by(nameContains, categoryId != null ? new CategoryId(categoryId) : null);
    return productRepository.search(search);
  }
}
