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

import es.nachobrito.multitenant.domain.model.Entity;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import java.util.Objects;

/**
 * @author nacho
 */
public class User implements Entity<UserId> {
  private final UserId id;

  private UserName userName;
  private MerchantId merchantId;

  private User(UserId id) {
    this.id = id;
  }

  public static User with(UserId userId, Merchant merchant, UserName userName) {
    return with(userId, merchant.getId(), userName);
  }

  public static User with(UserId userId, MerchantId merchantId, UserName userName) {
    var user = new User(userId);
    user.setUserName(userName);
    user.setMerchantId(merchantId);
    return user;
  }

  @Override
  public UserId getId() {
    return id;
  }

  public UserName getUserName() {
    return userName;
  }

  public void setUserName(UserName userName) {
    this.userName = userName;
  }

  public MerchantId getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(MerchantId merchantId) {
    this.merchantId = merchantId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", userName=" + userName + ", merchantId=" + merchantId + '}';
  }
}
