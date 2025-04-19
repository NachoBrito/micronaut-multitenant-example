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

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static org.junit.jupiter.api.Assertions.*;

import es.nachobrito.multitenant.domain.model.category.CategoryMother;
import es.nachobrito.multitenant.domain.model.category.CategoryRepository;
import es.nachobrito.multitenant.domain.model.merchant.MerchantMother;
import es.nachobrito.multitenant.domain.model.merchant.MerchantRepository;
import es.nachobrito.multitenant.domain.model.product.ProductMother;
import es.nachobrito.multitenant.domain.model.product.ProductRepository;
import es.nachobrito.multitenant.domain.model.user.UserMother;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
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

  @Test
  @DisplayName(
      "Expect that when a product is in the system it will be returned by the getProductById operation")
  void expectProductsRetrievedById() {
    var merchant1 = MerchantMother.random();
    var merchant2 = MerchantMother.random();
    merchantRepository.add(List.of(merchant1, merchant2));

    var user = UserMother.randomInMerchant(merchant1);
    authenticationClientFilter.setAuthenticatedUser(user);

    var category = CategoryMother.random();
    categoryRepository.add(List.of(category));

    var product1 = ProductMother.randomInMerchant(merchant1, category);
    var product2 = ProductMother.randomInMerchant(merchant2, category);
    productRepository.add(List.of(product1, product2));

    /*
    At this point there is no multitenancy support, so the same user should be able to fetch both products,
    even when only one of them is in their same merchant.
     */
    var result1 = apiClient.getProductById(product1.getId().toUuid());
    assertNotNull(result1);
    assertEquals(productMapper.from(product1), result1);

    var result2 = apiClient.getProductById(product2.getId().toUuid());
    assertNotNull(result2);
    assertEquals(productMapper.from(product2), result2);
  }

  @Client("/")
  @Header(name = ACCEPT, value = "application/vnd.github.v3+json, application/json")
  private interface ApiClient extends ProductApi {}
}
