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

import com.nimbusds.jwt.JWT;
import es.nachobrito.multitenant.domain.model.merchant.Merchant;
import es.nachobrito.multitenant.domain.model.merchant.MerchantId;
import es.nachobrito.multitenant.domain.model.merchant.MerchantRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import io.micronaut.multitenancy.tenantresolver.HttpRequestTenantResolver;
import io.micronaut.multitenancy.tenantresolver.TenantResolver;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.bearer.BearerTokenReader;
import io.micronaut.security.token.jwt.validator.JsonWebTokenParser;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** */
@Singleton
public class TokenTenantResolver
    implements TenantResolver, HttpRequestTenantResolver, MerchantResolver {
  private final Logger log = LoggerFactory.getLogger(getClass());

  private final BearerTokenReader bearerTokenReader;
  private final JsonWebTokenParser<JWT> jsonWebTokenParser;

  private final SecurityService securityService;
  private final MerchantRepository merchantRepository;

  public TokenTenantResolver(
      BearerTokenReader bearerTokenReader,
      JsonWebTokenParser<JWT> jsonWebTokenParser,
      SecurityService securityService,
      MerchantRepository merchantRepository) {
    this.bearerTokenReader = bearerTokenReader;
    this.jsonWebTokenParser = jsonWebTokenParser;
    this.securityService = securityService;
    this.merchantRepository = merchantRepository;
  }

  /**
   * If there is no HttpRequest available, try to get the authentication from the SecurityService
   *
   * @return the tenantId, if available.
   */
  @NonNull
  @Override
  public String resolveTenantId() {
    var authentication = securityService.getAuthentication();
    log.info(
        "Looking for the merchant Id (no HttpRequest available). Authentication: {}",
        authentication);

    return authentication
        .map(this::extractMerchantIdFromAuthentication)
        .orElseThrow(
            () ->
                new TenantNotFoundException("Tenant could not be resolved outside a web request"));
  }

  private String extractMerchantIdFromAuthentication(@NotNull Authentication authentication) {
    var merchantId = (String) authentication.getAttributes().get("merchantId");
    log.info("MerchantId detected -> {}", merchantId);
    return merchantId;
  }

  /**
   * Extract tenantId from a request, by reading the bearer authentication header.
   *
   * @param request The HTTP request
   * @return the tenantId, if any
   * @throws TenantNotFoundException if the tenant cannot be determined
   */
  @Override
  @NonNull
  public String resolveTenantId(@NonNull HttpRequest<?> request) throws TenantNotFoundException {
    log.info(
        "Looking for the merchant Id in the request authentication header: {}",
        request.getUserPrincipal());

    return bearerTokenReader.findToken(request).map(this::extractMerchantIdFromToken).orElse(null);
  }

  private String extractMerchantIdFromToken(@NotNull String tokenString) {
    log.info("Reading merchant Id from jwt token: {}", tokenString);
    return jsonWebTokenParser
        .parse(tokenString)
        .map(
            it -> {
              try {
                log.debug(it.getJWTClaimsSet().toString());
                var merchantId = it.getJWTClaimsSet().getClaimAsString("merchantId");
                log.info("MerchantId found in claims: {}", merchantId);
                return merchantId;
              } catch (ParseException e) {
                log.error("Could not parse token: {}", e.getMessage(), e);
                throw new TenantNotFoundException(e.getMessage());
              }
            })
        .orElse(null);
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
