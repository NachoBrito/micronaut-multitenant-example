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

package es.nachobrito.multitenant.infrastructure.inmemory;

import es.nachobrito.multitenant.domain.model.Entity;
import es.nachobrito.multitenant.domain.model.EntityRepository;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In-Memory implementation of an entity Repository
 *
 * @author nacho
 */
public abstract class InMemoryEntityRepository<E extends Entity<I>, I>
    implements EntityRepository<E, I> {
  protected final Map<I, E> storage = new HashMap<>();
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void add(List<E> items) {
    for (var item : items) {
      log.info("Storing item {}", item);
      storage.put(item.getId(), item);
    }
  }

  @Override
  public Optional<E> get(I id) {
    log.info("Looking for item {}", id);
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public void delete(I id) {
    log.info("Removing item {}", id);
    storage.remove(id);
  }
}
