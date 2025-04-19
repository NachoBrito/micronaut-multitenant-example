package es.nachobrito.multitenant.application;

import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import jakarta.inject.Singleton;
import java.util.Optional;

/**
 * Implements application logic for Product entities
 *
 * @author nacho
 */
@Singleton
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Optional<Product> getProduct(ProductId productId) {
    return productRepository.get(productId);
  }
}
