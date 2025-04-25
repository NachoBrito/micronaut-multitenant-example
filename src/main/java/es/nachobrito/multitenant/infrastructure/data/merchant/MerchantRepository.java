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

package es.nachobrito.multitenant.infrastructure.data.merchant;

import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * @author nacho
 */
@Singleton
@Requires(beans = javax.sql.DataSource.class)
public class MerchantRepository
    implements es.nachobrito.multitenant.domain.model.merchant.MerchantRepository {
  private final MerchantJdbcRepository merchantJdbcRepository;

  public MerchantRepository(MerchantJdbcRepository merchantJdbcRepository) {
    this.merchantJdbcRepository = merchantJdbcRepository;
  }

  @Override
  public void add(List<Merchant> items) {
    merchantJdbcRepository.saveAll(items.stream().map(JdbcMerchant::of).toList());
  }

  @Override
  public Optional<Merchant> get(MerchantId entityId) {
    return merchantJdbcRepository.findById(entityId.toUuid()).map(JdbcMerchant::toDomainModel);
  }

  @Override
  public void delete(MerchantId entityId) {
    merchantJdbcRepository.deleteById(entityId.toUuid());
  }
}
