package neowise.user.service.security.authorization;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import neowise.user.service.common.JwtUtils;
import neowise.user.service.exceptions.AuthorizationException;
import neowise.user.service.security.authentication.AuthenticationToken;
import neowise.user.service.security.common.AuthAttributes;
import neowise.user.service.security.common.AuthRole;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    private boolean isUrlPublic(String requestUri) {
        return requestUri.contains("test");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {

            String requestUri = request.getRequestURI();

            if (!isUrlPublic(requestUri)) {
                String authorizationHeader = request.getHeader("authorization");
                String token = JwtUtils.extractBearerToken(authorizationHeader);
                DecodedJWT decodedJwt = AuthenticationToken.verifyAndDecode(token);
                String roleClaim = decodedJwt.getClaim(AuthAttributes.ROLE).as(TextNode.class).asText();
                AccessControl.enforceAccessControl(requestUri, roleClaim);

                if (AccessControl.roleClaimMatches(roleClaim, AuthRole.USER)) {
                    String userId = decodedJwt.getClaim(AuthAttributes.USER_ID).as(TextNode.class).asText();
                    request.setAttribute(AuthAttributes.USER_ID, userId);
                }

            }

            chain.doFilter(request, response);
        } catch (Exception exception) {
            AuthorizationException e = new AuthorizationException();
            exception.printStackTrace();
            response.resetBuffer();
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            response.getOutputStream().print(new ObjectMapper().writeValueAsString(e.unpack()));
            response.flushBuffer();
        }
    }

}
