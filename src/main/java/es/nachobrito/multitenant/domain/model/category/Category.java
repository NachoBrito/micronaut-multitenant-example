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

package es.nachobrito.multitenant.domain.model.category;

import es.nachobrito.multitenant.domain.model.Entity;
import java.util.Objects;

/**
 * @author nacho
 */
public class Category implements Entity<CategoryId> {
  private final CategoryId id;
  private CategoryName name;

  private Category(CategoryId id) {
    this.id = id;
  }

  public static Category with(CategoryId categoryId, CategoryName name) {
    var category = new Category(categoryId);
    category.setName(name);
    return category;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(getId(), category.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public CategoryId getId() {
    return id;
  }

  public CategoryName getName() {
    return name;
  }

  public void setName(CategoryName name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Category{" + "id=" + id + ", name=" + name + '}';
  }
}
