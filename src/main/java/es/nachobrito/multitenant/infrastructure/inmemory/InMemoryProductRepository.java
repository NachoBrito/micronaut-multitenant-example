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

package es.nachobrito.multitenant.infrastructure.inmemory;

import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import es.nachobrito.multitenant.domain.model.product.search.ProductSearch;
import jakarta.inject.Singleton;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In-Memory implementation of the Product Repository
 *
 * @author nacho
 */
@Singleton
public class InMemoryProductRepository extends InMemoryEntityRepository<Product, ProductId>
    implements ProductRepository {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public List<Product> search(ProductSearch search) {
    log.info("Searching for products: {}", search);
    return storage.values().stream().filter(search::matches).toList();
  }
}
