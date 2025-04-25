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

import static org.junit.jupiter.api.Assertions.*;

import es.nachobrito.multitenant.domain.model.category.Category;
import es.nachobrito.multitenant.domain.model.category.CategoryMother;
import es.nachobrito.multitenant.domain.model.category.CategoryRepository;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantMother;
import es.nachobrito.multitenant.domain.model.merchant.MerchantRepository;
import es.nachobrito.multitenant.domain.model.product.Product;
import es.nachobrito.multitenant.domain.model.product.ProductMother;
import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import es.nachobrito.multitenant.domain.model.user.User;
import es.nachobrito.multitenant.domain.model.user.UserMother;
import es.nachobrito.multitenant.domain.model.user.UserRepository;
import es.nachobrito.multitenant.infrastructure.inmemory.InMemoryCategoryRepository;
import es.nachobrito.multitenant.infrastructure.inmemory.InMemoryMerchantRepository;
import es.nachobrito.multitenant.infrastructure.inmemory.InMemoryProductRepository;
import es.nachobrito.multitenant.infrastructure.inmemory.InMemoryUserRepository;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author nacho
 */
@MicronautTest
class ProductControllerTest {

  @Inject ApiClient apiClient;
  @Inject ProductRepository productRepository;
  @Inject MerchantRepository merchantRepository;
  @Inject CategoryRepository categoryRepository;
  @Inject ProductMapper productMapper;
  @Inject AuthenticationClientFilter authenticationClientFilter;

  // Test data:
  Merchant merchant1 = MerchantMother.random();
  Merchant merchant2 = MerchantMother.random();
  User user = UserMother.randomInMerchant(merchant1);
  Category category = CategoryMother.random();
  Product product1 = ProductMother.randomInMerchant(merchant1, category);
  Product product2 = ProductMother.randomInMerchant(merchant2, category);

  @MockBean(ProductRepository.class)
  ProductRepository productRepository() {
    return new PreloadedProductRepository(List.of(product1, product2));
  }

  @MockBean(MerchantRepository.class)
  MerchantRepository merchantRepository() {
    return new PreloadedMerchantRepository(List.of(merchant1, merchant2));
  }

  @MockBean(CategoryRepository.class)
  CategoryRepository categoryRepository() {
    return new PreloadedCategoryRepository(List.of(category));
  }

  @MockBean(UserRepository.class)
  UserRepository userRepository() {
    return new PreloadedUserRepository(List.of(user));
  }

  @Test
  @DisplayName("Expect that only products in the same Merchant are returned by the API")
  void expectProductsRetrievedById() {
    authenticationClientFilter.setAuthenticatedUser(user);
    var result1 = apiClient.getProductById(product1.getId().toUuid());
    assertNotNull(result1);
    assertEquals(productMapper.from(product1), result1);

    // product2 does not belong to the user's merchant, so it should not be visible to them:
    var result2 = apiClient.getProductById(product2.getId().toUuid());
    assertNull(result2);
  }



  static class PreloadedProductRepository extends InMemoryProductRepository {
    PreloadedProductRepository(List<Product> items) {
      items.forEach(
          item -> {
            this.storage.put(item.getId(), item);
          });
    }
  }

  static class PreloadedMerchantRepository extends InMemoryMerchantRepository {
    PreloadedMerchantRepository(List<Merchant> items) {
      items.forEach(
          item -> {
            this.storage.put(item.getId(), item);
          });
    }
  }

  static class PreloadedCategoryRepository extends InMemoryCategoryRepository {
    PreloadedCategoryRepository(List<Category> items) {
      items.forEach(
          item -> {
            this.storage.put(item.getId(), item);
          });
    }
  }

  static class PreloadedUserRepository extends InMemoryUserRepository {
    PreloadedUserRepository(List<User> items) {
      items.forEach(
          item -> {
            this.storage.put(item.getId(), item);
          });
    }
  }
}
