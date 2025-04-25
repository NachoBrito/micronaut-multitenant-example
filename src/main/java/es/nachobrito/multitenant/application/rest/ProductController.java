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

package es.nachobrito.multitenant.application.rest;

import es.nachobrito.multitenant.application.ProductService;
import es.nachobrito.multitenant.domain.model.product.ProductId;
import io.micronaut.http.annotation.Controller;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author nacho
 */
@Controller
public class ProductController implements ProductApi {
  private final ProductService productService;
  private final ProductMapper productMapper;

  public ProductController(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @Override
  public Product addProduct(Product product) {
    return null;
  }

  @Override
  public void deleteProduct(UUID productId) {}

  @Override
  public Product getProductById(UUID productId) {
    return productService.getProduct(ProductId.of(productId)).map(productMapper::from).orElse(null);
  }

  @Override
  public List<@Valid Product> productSearch(String nameContains, String categoryId) {
    return productService.searchProducts(nameContains, categoryId).stream()
        .map(productMapper::from)
        .toList();
  }

  @Override
  public Product updateProduct(Product product) {
    return null;
  }

  @Override
  public void updateProductWithForm(UUID productId, String name, String categoryId) {}
}
