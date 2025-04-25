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

import es.nachobrito.multitenant.domain.model.user.User;
import es.nachobrito.multitenant.domain.model.user.UserId;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

/**
 * @author nacho
 */
@Singleton
@Requires(beans = javax.sql.DataSource.class)
public class UserRepository implements es.nachobrito.multitenant.domain.model.user.UserRepository {
  private final UserJdbcRepository userJdbcRepository;

  public UserRepository(UserJdbcRepository userJdbcRepository) {
    this.userJdbcRepository = userJdbcRepository;
  }

  @Override
  public void add(List<User> items) {
    userJdbcRepository.saveAll(items.stream().map(JdbcUser::of).toList());
  }

  @Override
  public Optional<User> get(UserId entityId) {
    return userJdbcRepository.findById(entityId.toUuid()).map(JdbcUser::toDomainModel);
  }

  @Override
  public void delete(UserId entityId) {
    userJdbcRepository.deleteById(entityId.toUuid());
  }
}
