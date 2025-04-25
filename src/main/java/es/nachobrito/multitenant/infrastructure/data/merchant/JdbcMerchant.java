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
import es.nachobrito.multitenant.domain.model.merchant.MerchantName;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.util.Objects;
import java.util.UUID;

/**
 * @author nacho
 */
@MappedEntity("MERCHANT")
class JdbcMerchant {
  @Id private UUID uuid;

  private String name;

  static JdbcMerchant of(Merchant merchant) {
    var entity = new JdbcMerchant();
    entity.setName(merchant.getName().name());
    entity.setUuid(merchant.getId().toUuid());
    return entity;
  }

  UUID getUuid() {
    return uuid;
  }

  void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    JdbcMerchant jdbcMerchant = (JdbcMerchant) o;
    return Objects.equals(getUuid(), jdbcMerchant.getUuid());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUuid());
  }

  Merchant toDomainModel() {
    return Merchant.with(MerchantId.of(this.uuid), new MerchantName(this.name));
  }

  @Override
  public String toString() {
    return "JdbcMerchant{" + "uuid=" + uuid + ", name='" + name + '\'' + '}';
  }
}
