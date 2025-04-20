package es.nachobrito.multitenant.infrastructure.multitenant;

import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import java.util.Optional;

/**
 * @author nacho
 */
public interface MerchantResolver {
  Optional<Merchant> getCurrentMerchant();
}
