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
}
