package com.assigment.moviecatalog.interceptors;
/*
 * import com.dpb.commonlibrary.security.utils.SecurityUtils; import
 * java.io.IOException; import lombok.extern.slf4j.Slf4j; import
 * org.springframework.http.HttpRequest; import
 * org.springframework.http.client.ClientHttpRequestExecution; import
 * org.springframework.http.client.ClientHttpRequestInterceptor; import
 * org.springframework.http.client.ClientHttpResponse; import
 * org.springframework.stereotype.Component;
 * 
 * @Component
 * 
 * @Slf4j public class RequestAuthorizationInterceptor implements
 * ClientHttpRequestInterceptor {
 * 
 * private static final String AUTHORIZATION = "Authorization"; private static
 * final String BEARER = "Bearer";
 * 
 * @Override public ClientHttpResponse intercept(HttpRequest request, byte[]
 * body, ClientHttpRequestExecution execution) throws IOException {
 * SecurityUtils.getCurrentUserJwt().ifPresent(s -> {
 * log.info("Request before adding Authorization {}", request.getHeaders()); if
 * (!s.isBlank() && !request.getHeaders().containsKey(AUTHORIZATION)) {
 * request.getHeaders().add(AUTHORIZATION, String.format("%s %s", BEARER, s)); }
 * log.info("Request After adding Authorization {}", request.getHeaders()); });
 * return execution.execute(request, body); } }
 */