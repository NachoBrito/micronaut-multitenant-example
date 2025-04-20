package es.nachobrito.multitenant.domain.model.product.search;

import es.nachobrito.multitenant.domain.model.category.CategoryId;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.product.Product;

/**
 * @author nacho
 */
@FunctionalInterface
public interface ProductSearch {

  static ProductSearch by(String name, CategoryId category) {
    return new ProductSearchByNameOrCategory(name, category);
  }

  default ProductSearch withMerchant(MerchantId merchantId) {
    throw new IllegalStateException("This search doesn't support merchant filtering!");
  }

  boolean matches(Product product);
}
