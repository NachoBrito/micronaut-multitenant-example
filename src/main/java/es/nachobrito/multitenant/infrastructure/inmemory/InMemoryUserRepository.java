package es.nachobrito.multitenant.infrastructure.inmemory;

import es.nachobrito.multitenant.domain.model.user.User;
import es.nachobrito.multitenant.domain.model.user.UserId;
import es.nachobrito.multitenant.domain.model.user.UserRepository;
import jakarta.inject.Singleton;

/**
 * In-Memory implementation of the Product Repository
 *
 * @author nacho
 */
@Singleton
public class InMemoryUserRepository extends InMemoryEntityRepository<User, UserId>
    implements UserRepository {}
