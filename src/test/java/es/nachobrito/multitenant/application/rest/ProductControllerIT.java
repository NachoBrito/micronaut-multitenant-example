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
import static org.junit.jupiter.api.Assertions.assertNull;

import es.nachobrito.multitenant.domain.model.product.ProductMother;
import es.nachobrito.multitenant.domain.model.user.UserMother;
import io.micronaut.test.annotation.Sql;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integration test that verifies the multi-tenancy control access with a real SQL database as
 * persistence mechanism.
 *
 * <p>The environment "it" triggers the use of the datasource defined in application-it.properties,
 * and the test-data-up.sql and test-data-down.sql will create and remove data before and after each
 * test.
 *
 * <p>Factory methods in {@link es.nachobrito.multitenant.domain.model.merchant.MerchantMother},
 * {@link es.nachobrito.multitenant.domain.model.category.CategoryMother}, {@link ProductMother} and
 * {@link UserMother} can be used to access the artifacts created from the sql scripts.
 *
 * @author nacho
 */
@MicronautTest(environments = {"it"})
@Sql(scripts = "classpath:sql/test-data-up.sql", phase = Sql.Phase.BEFORE_EACH)
@Sql(scripts = "classpath:sql/test-data-down.sql", phase = Sql.Phase.AFTER_EACH)
public class ProductControllerIT {
  @Inject ApiClient apiClient;
  @Inject AuthenticationClientFilter authenticationClientFilter;

  @Inject ProductMapper productMapper;

  @Test
  @DisplayName("Expect that only products in the same Merchant are returned by the API")
  void expectProductsRetrievedById() {
    var user = UserMother.inMerchant1();
    var product1 = ProductMother.inMerchant1();
    var product2 = ProductMother.inMerchant2();

    authenticationClientFilter.setAuthenticatedUser(user);
    var result1 = apiClient.getProductById(product1.getId().toUuid());
    assertNotNull(result1);
    assertEquals(productMapper.from(product1), result1);

    // product2 does not belong to the user's merchant, so it should not be visible to them:
    var result2 = apiClient.getProductById(product2.getId().toUuid());
    assertNull(result2);
  }
}
