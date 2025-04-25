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

package es.nachobrito.multitenant.application.rest;

import es.nachobrito.multitenant.domain.model.user.User;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.TokenGenerator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Intercepts requests to add bearer authentication
 *
 * @see <a
 *     href="https://guides.micronaut.io/latest/micronaut-http-client-gradle-java.html#http-client-filter">docs</a>
 * @author nacho
 */
@ClientFilter("/**")
public class AuthenticationClientFilter {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationClientFilter.class);
  private static final String ROLE_USER = "ROLE_USER";
  private static final Integer _1H_SECONDS = 3600;
  private final TokenGenerator tokenGenerator;

  /** The logged-in user that will be authenticating the requests made */
  private User authenticatedUser;

  public AuthenticationClientFilter(TokenGenerator tokenGenerator) {
    this.tokenGenerator = tokenGenerator;
  }

  @RequestFilter
  public void doFilter(MutableHttpRequest<?> request) {
    if (authenticatedUser == null) {
      log.warn("No user registered, requests will be anonymous!");
      return;
    }

    var roles = List.of(ROLE_USER);
    Map<String, Object> attributes =
        Map.of("merchantId", authenticatedUser.getMerchantId().value());
    var authorization =
        Authentication.build(authenticatedUser.getUserName().value(), roles, attributes);
    var token = tokenGenerator.generateToken(authorization, _1H_SECONDS);
    log.info("Token generated: {}", token);
    request.bearerAuth(token.orElseThrow());
    request.getHeaders().asMap().forEach((key, value) -> log.info("{} -> {}", key, value));
  }

  public void setAuthenticatedUser(User user) {
    log.info("Authenticated user for the next request -> {}", user);
    this.authenticatedUser = user;
  }
}
