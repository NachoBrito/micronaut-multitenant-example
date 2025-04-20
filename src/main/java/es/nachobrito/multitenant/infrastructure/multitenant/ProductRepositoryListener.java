package es.nachobrito.multitenant.infrastructure.multitenant;

import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Replaces the ProductRepository with a wrapper that will inject MerchantId into the logic.
 *
 * @author nacho
 */
@Singleton
public class ProductRepositoryListener implements BeanCreatedEventListener<ProductRepository> {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final MerchantResolver merchantResolver;

  public ProductRepositoryListener(MerchantResolver merchantResolver) {
    this.merchantResolver = merchantResolver;
  }

  @Override
  public ProductRepository onCreated(@NonNull BeanCreatedEvent<ProductRepository> event) {
    var newRepository = new MerchantAwareProductRepository(event.getBean(), merchantResolver);
    log.info("Replacing product repository [{}] with [{}]", event.getBean(), newRepository);
    return newRepository;
  }
}
