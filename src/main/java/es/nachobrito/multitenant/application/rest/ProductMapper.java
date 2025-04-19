package es.nachobrito.multitenant.application.rest;

import es.nachobrito.multitenant.domain.model.category.CategoryRepository;
import jakarta.inject.Singleton;

/**
 * Translates Product domain entities to the Product representation in the REST layer.
 *
 * @author nacho
 */
@Singleton
public class ProductMapper {
  private final CategoryRepository categoryRepository;

  public ProductMapper(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  Product from(es.nachobrito.multitenant.domain.model.product.Product domainProduct) {
    var category = categoryRepository.get(domainProduct.getCategoryId()).orElseThrow();
    return new Product(
        domainProduct.getId().toUuid(),
        domainProduct.getName().value(),
        new Category(category.getId().toUuid(), category.getName().value()));
  }
}
