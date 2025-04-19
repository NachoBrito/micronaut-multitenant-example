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
    var user = new User(userId);
    user.setUserName(userName);
    user.setMerchantId(merchant.getId());
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
