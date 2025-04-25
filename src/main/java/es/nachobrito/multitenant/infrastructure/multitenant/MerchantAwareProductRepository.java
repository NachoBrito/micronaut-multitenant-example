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

package es.nachobrito.multitenant.infrastructure.multitenant;

import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import es.nachobrito.multitenant.domain.model.product.search.ProductSearch;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nacho
 */
public class MerchantAwareProductRepository implements ProductRepository {
  private final Logger log = LoggerFactory.getLogger(getClass());

  private final ProductRepository delegate;
  private final MerchantResolver merchantResolver;

  public MerchantAwareProductRepository(
      ProductRepository delegate, MerchantResolver merchantResolver) {
    this.delegate = delegate;
    this.merchantResolver = merchantResolver;
  }

  @Override
  public List<Product> search(ProductSearch search) {
    var merchant = merchantResolver.getCurrentMerchant();
    if (merchant.isPresent()) {
      log.info(
          "Merchant resolved: {}. Injecting merchant into the product search",
          merchant.get().getName().name());
      search = search.withMerchant(merchant.get().getId());
    }
    return delegate.search(search);
  }

  @Override
  public void add(List<Product> items) {
    merchantResolver
        .getCurrentMerchant()
        .ifPresent(
            merchant -> {
              log.info("Adding {} products to merchant {}", items.size(), merchant.getId());
              items.forEach(product -> product.setMerchantId(merchant.getId()));
            });

    delegate.add(items);
  }

  @Override
  public Optional<Product> get(ProductId productId) {
    // return the product only if it's in the current merchant
    return delegate.get(productId).filter(this::matchesMerchant);
  }

  /**
   * Verifies if the product matches the merchant
   *
   * @param product the product
   * @return whether the provided product belongs to the current merchant. If there is no active
   *     merchant, this method returns true.
   */
  private boolean matchesMerchant(@NotNull Product product) {
    var merchant = merchantResolver.getCurrentMerchant();
    var matches = merchant.map(value -> value.getId().equals(product.getMerchantId()));
    log.info("Product {} matches current merchant {}? {}", product, merchant, matches);
    return matches.orElse(true);
  }

  @Override
  public void delete(ProductId productId) {
    // delete the product only if it is found by the get method (verifying merchant)
    this.get(productId).map(Product::getId).ifPresent(delegate::delete);
  }
}
