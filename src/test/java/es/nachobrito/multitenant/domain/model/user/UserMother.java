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

package es.nachobrito.multitenant.domain.model.user;

import com.github.javafaker.Faker;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;

/**
 * Generates test objects
 *
 * @see <a href="https://martinfowler.com/bliki/ObjectMother.html">The ObjectMother pattern</a>
 * @author nacho
 */
public class UserMother {

  public static User randomInMerchant(Merchant merchant) {
    var userId = UserId.newRandom();
    var userName = new UserName(Faker.instance().name().username());
    return User.with(userId, merchant, userName);
  }

  public static User inMerchant1() {
    return User.with(
        new UserId("f3d2063e-5c3e-42b7-a3ac-d6ea726c5bba"),
        new MerchantId("afe252cb-3daf-457d-b9e3-e2759e707a0e"),
        new UserName("User in Merchant 1"));
  }
}
