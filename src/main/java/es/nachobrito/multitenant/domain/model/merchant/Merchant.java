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

import es.nachobrito.multitenant.domain.model.Entity;
import java.util.Objects;

/**
 * @author nacho
 */
public class Merchant implements Entity<MerchantId> {
  private final MerchantId id;
  private MerchantName name;

  private Merchant(MerchantId id) {
    this.id = id;
  }

  public static Merchant with(MerchantId merchantId, MerchantName name) {
    var merchant = new Merchant(merchantId);
    merchant.setName(name);
    return merchant;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Merchant merchant = (Merchant) o;
    return Objects.equals(getId(), merchant.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public MerchantId getId() {
    return id;
  }

  public MerchantName getName() {
    return name;
  }

  public void setName(MerchantName name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Merchant{" + "id=" + id + ", name=" + name + '}';
  }
}
