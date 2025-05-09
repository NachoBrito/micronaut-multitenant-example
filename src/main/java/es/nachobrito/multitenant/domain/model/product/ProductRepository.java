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

import es.nachobrito.multitenant.domain.model.EntityRepository;
import es.nachobrito.multitenant.domain.model.product.search.ProductSearch;
import java.util.List;

/**
 * @author nacho
 */
public interface ProductRepository extends EntityRepository<Product, ProductId> {

  /**
   * Search Products matching the provided search parameters
   *
   * @param search parameters for product search
   * @return the list of products matching the search parameters
   */
  List<Product> search(ProductSearch search);
}
