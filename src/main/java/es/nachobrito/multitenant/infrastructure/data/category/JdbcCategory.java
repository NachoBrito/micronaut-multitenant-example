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

package es.nachobrito.multitenant.infrastructure.data.category;

import es.nachobrito.multitenant.domain.model.category.Category;
import es.nachobrito.multitenant.domain.model.category.CategoryId;
import es.nachobrito.multitenant.domain.model.category.CategoryName;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.util.Objects;
import java.util.UUID;

/**
 * @author nacho
 */
@MappedEntity("CATEGORY")
class JdbcCategory {
  @Id private UUID uuid;

  private String name;

  static JdbcCategory of(Category category) {
    var jdbcEntity = new JdbcCategory();
    jdbcEntity.setName(category.getName().value());
    jdbcEntity.setUuid(category.getId().toUuid());
    return jdbcEntity;
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
    JdbcCategory jdbcCategory = (JdbcCategory) o;
    return Objects.equals(getUuid(), jdbcCategory.getUuid());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUuid());
  }

  Category toDomainModel() {
    return Category.with(CategoryId.of(this.uuid), new CategoryName(this.name));
  }
}
