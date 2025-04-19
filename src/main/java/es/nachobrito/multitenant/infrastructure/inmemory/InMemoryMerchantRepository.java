package es.nachobrito.multitenant.infrastructure.inmemory;

import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.merchant.MerchantRepository;
import jakarta.inject.Singleton;

/**
 * In-Memory implementation of the Merchant Repository
 *
 * @author nacho
 */
@Singleton
public class InMemoryMerchantRepository extends InMemoryEntityRepository<Merchant, MerchantId>
    implements MerchantRepository {}
