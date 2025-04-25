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

package es.nachobrito.multitenant.infrastructure.data.user;

import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.user.User;
import es.nachobrito.multitenant.domain.model.user.UserId;
import es.nachobrito.multitenant.domain.model.user.UserName;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TenantId;
import java.util.Objects;
import java.util.UUID;

/**
 * @author nacho
 */
@MappedEntity("USER")
class JdbcUser {
  @Id private UUID uuid;

  @TenantId private UUID merchantId;

  private String name;

  static JdbcUser of(User user) {
    var entity = new JdbcUser();
    entity.setMerchantId(user.getMerchantId().toUuid());
    entity.setName(user.getUserName().value());
    entity.setUuid(user.getId().toUuid());
    return entity;
  }

  UUID getUuid() {
    return uuid;
  }

  void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  UUID getMerchantId() {
    return merchantId;
  }

  void setMerchantId(UUID merchantId) {
    this.merchantId = merchantId;
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
    JdbcUser jdbcUser = (JdbcUser) o;
    return Objects.equals(getUuid(), jdbcUser.getUuid());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUuid());
  }

  User toDomainModel() {
    return User.with(UserId.of(uuid), MerchantId.of(merchantId), new UserName(name));
  }
}
