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

import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Replaces the ProductRepository with a wrapper that will inject MerchantId into the logic.
 *
 * @author nacho
 */
@Singleton
@Requires(missingBeans = javax.sql.DataSource.class)
public class ProductRepositoryListener implements BeanCreatedEventListener<ProductRepository> {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final MerchantResolver merchantResolver;

  public ProductRepositoryListener(MerchantResolver merchantResolver) {
    this.merchantResolver = merchantResolver;
  }

  @Override
  public ProductRepository onCreated(@NonNull BeanCreatedEvent<ProductRepository> event) {
    var newRepository = new MerchantAwareProductRepository(event.getBean(), merchantResolver);
    log.info("Replacing product repository [{}] with [{}]", event.getBean(), newRepository);
    return newRepository;
  }
}
