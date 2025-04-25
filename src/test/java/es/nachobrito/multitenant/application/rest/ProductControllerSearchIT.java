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

import es.nachobrito.multitenant.domain.model.product.ProductMother;
import es.nachobrito.multitenant.domain.model.user.UserMother;
import io.micronaut.test.annotation.Sql;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author nacho
 */
@MicronautTest(environments = {"it"})
@Sql(scripts = "classpath:sql/test-data-up.sql", phase = Sql.Phase.BEFORE_EACH)
@Sql(scripts = "classpath:sql/test-data-down.sql", phase = Sql.Phase.AFTER_EACH)
public class ProductControllerSearchIT {
  @Inject ApiClient apiClient;
  @Inject AuthenticationClientFilter authenticationClientFilter;

  @Inject ProductMapper productMapper;

  @Test
  @DisplayName("Expect that only products in the right tenancy are returned by the search")
  void expectProductsRetrievedById() {
    var user = UserMother.inMerchant1();
    var product1 = ProductMother.inMerchant1();
    var product2 = ProductMother.inMerchant2();
    authenticationClientFilter.setAuthenticatedUser(user);

    var result1 =
        apiClient.productSearch(product1.getName().value(), product1.getCategoryId().value());
    assertEquals(1, result1.size());
    assertEquals(productMapper.from(product1), result1.getFirst());

    var result2 = apiClient.productSearch(product1.getName().value(), null);
    assertEquals(1, result1.size());
    assertEquals(productMapper.from(product1), result1.getFirst());

    var result3 = apiClient.productSearch(product2.getName().value(), null);
    assertEquals(0, result3.size());

    var result4 = apiClient.productSearch("Random Name", null);
    assertEquals(0, result3.size());
  }
}
