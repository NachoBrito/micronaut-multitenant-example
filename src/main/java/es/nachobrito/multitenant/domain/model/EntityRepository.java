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

package es.nachobrito.multitenant.domain.model;

import java.util.List;
import java.util.Optional;

/**
 * Defines generic interface for entity repositories
 *
 * @author nacho
 */
public interface EntityRepository<E extends Entity<I>, I> {

  /**
   * Add one or more new objects of type T to the store.
   *
   * @param items objects of type T to add
   */
  void add(List<E> items);

  /**
   * Get an item from the store
   *
   * @param entityId the value to search the item for
   * @return the product, if any
   */
  Optional<E> get(I entityId);

  /**
   * Deletes a product in the store, if exists.
   *
   * @param entityId the value of the item to remove.
   */
  void delete(I entityId);
}
