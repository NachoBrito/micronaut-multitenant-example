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
package es.nachobrito.multitenant.infrastructure.multitenant;

import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.merchant.MerchantRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import io.micronaut.multitenancy.tenantresolver.HttpRequestTenantResolver;
import io.micronaut.multitenancy.tenantresolver.TenantResolver;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** */
@Singleton
public class TokenTenantResolver
    implements TenantResolver, HttpRequestTenantResolver, MerchantResolver {
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final SecurityService securityService;
  private final MerchantRepository merchantRepository;

  public TokenTenantResolver(
      SecurityService securityService, MerchantRepository merchantRepository) {
    this.securityService = securityService;
    this.merchantRepository = merchantRepository;
  }

  @NonNull
  @Override
  public String resolveTenantId() {
    var authentication = securityService.getAuthentication();
    return authentication
        .map(this::extractMerchantId)
        .orElseThrow(
            () ->
                new TenantNotFoundException("Tenant could not be resolved outside a web request"));
  }

  private String extractMerchantId(@NotNull Authentication authentication) {
    return (String) authentication.getAttributes().get("merchantId");
  }

  @Override
  @NonNull
  public String resolveTenantId(@NonNull HttpRequest<?> request) throws TenantNotFoundException {
    return resolveTenantId();
  }

  @Override
  public Optional<Merchant> getCurrentMerchant() {
    try {
      var merchantId = resolveTenantId();
      return merchantRepository.get(new MerchantId(merchantId));
    } catch (TenantNotFoundException ex) {
      log.warn("No merchant detected!");
      return Optional.empty();
    }
  }

  @NonNull
  @Override
  @Deprecated(forRemoval = true, since = "5.5.0")
  public Serializable resolveTenantIdentifier() {
    return resolveTenantId();
  }

  @Override
  @NonNull
  @Deprecated(forRemoval = true, since = "5.5.0")
  public Serializable resolveTenantIdentifier(@NonNull HttpRequest<?> request)
      throws TenantNotFoundException {
    return resolveTenantId(request);
  }
}
