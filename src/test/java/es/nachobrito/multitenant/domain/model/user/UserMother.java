package es.nachobrito.multitenant.domain.model.user;

import com.github.javafaker.Faker;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;

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
}
