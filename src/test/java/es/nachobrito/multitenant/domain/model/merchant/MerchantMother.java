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

package es.nachobrito.multitenant.domain.model.merchant;

import com.github.javafaker.Faker;

/**
 * Generates test objects
 *
 * @see <a href="https://martinfowler.com/bliki/ObjectMother.html">The ObjectMother pattern</a>
 * @author nacho
 */
public class MerchantMother {
  public static Merchant random() {
    var merchantId = MerchantId.newRandom();
    var name = new MerchantName(Faker.instance().company().name());
    return Merchant.with(merchantId, name);
  }

  public static Merchant merchant1() {
    return Merchant.with(
        new MerchantId("afe252cb-3daf-457d-b9e3-e2759e707a0e"), new MerchantName("Merchant 1"));
  }

  public static Merchant merchant2() {
    return Merchant.with(
        new MerchantId("2b682729-29a9-4b13-a903-53b32590ea05"), new MerchantName("Merchant 2"));
  }
}
