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
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * @author nacho
 */
@Singleton
@Requires(beans = javax.sql.DataSource.class)
public class CategoryRepository
    implements es.nachobrito.multitenant.domain.model.category.CategoryRepository {
  private final CategoryJdbcRepository categoryJdbcRepository;

  public CategoryRepository(CategoryJdbcRepository categoryJdbcRepository) {
    this.categoryJdbcRepository = categoryJdbcRepository;
  }

  @Override
  public void add(List<Category> items) {
    categoryJdbcRepository.saveAll(items.stream().map(JdbcCategory::of).toList());
  }

  @Override
  public Optional<Category> get(CategoryId entityId) {
    return categoryJdbcRepository.findById(entityId.toUuid()).map(JdbcCategory::toDomainModel);
  }

  @Override
  public void delete(CategoryId entityId) {
    categoryJdbcRepository.deleteById(entityId.toUuid());
  }
}
