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

import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import es.nachobrito.multitenant.domain.model.product.search.ProductSearch;
import es.nachobrito.multitenant.domain.model.product.search.ProductSearchByNameAndCategory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * @author nacho
 */
@Singleton
@Requires(beans = javax.sql.DataSource.class)
public class ProductRepository
    implements es.nachobrito.multitenant.domain.model.product.ProductRepository {

  private final ProductJdbcRepository productJdbcRepository;

  public ProductRepository(ProductJdbcRepository productJdbcRepository) {
    this.productJdbcRepository = productJdbcRepository;
  }

  @Override
  public List<Product> search(ProductSearch productSearch) {
    if (!(productSearch instanceof ProductSearchByNameAndCategory)) {
      throw new IllegalArgumentException(
          "Unknown query type! %s".formatted(productSearch.getClass().getName()));
    }
    return searchByNameOrCategory((ProductSearchByNameAndCategory) productSearch);
  }

  private List<Product> searchByNameOrCategory(ProductSearchByNameAndCategory search) {
    if (search.category() != null && search.name() != null) {
      return productJdbcRepository
          .findByCategoryIdAndNameContains(search.category().toUuid(), search.name())
          .stream()
          .map(JdbcProduct::toDomainModel)
          .toList();
    }

    // filter by merchantId and category
    if (search.category() != null) {
      return productJdbcRepository.findByCategoryId(search.category().toUuid()).stream()
          .map(JdbcProduct::toDomainModel)
          .toList();
    }

    // filter by merchantId and name
    return productJdbcRepository.findByNameContains(search.name()).stream()
        .map(JdbcProduct::toDomainModel)
        .toList();
  }

  @Override
  public void add(List<Product> items) {
    productJdbcRepository.saveAll(items.stream().map(JdbcProduct::of).toList());
  }

  @Override
  public Optional<Product> get(ProductId entityId) {
    return productJdbcRepository.findById(entityId.toUuid()).map(JdbcProduct::toDomainModel);
  }

  @Override
  public void delete(ProductId entityId) {
    productJdbcRepository.deleteById(entityId.toUuid());
  }
}
