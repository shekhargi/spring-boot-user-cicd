package neowise.user.service.security.authorization;

import java.util.Set;

import neowise.user.service.config.ServiceConfig;
import neowise.user.service.exceptions.AuthorizationException;
import neowise.user.service.security.common.ACLTable;
import neowise.user.service.security.common.AuthRole;

public class AccessControl {

    public static void enforceAccessControl(String requestUri, AuthRole authRole) throws AuthorizationException {
        Set<String> allowedEndpoints = ACLTable.acl.get(authRole);
        boolean accessDenied = true;
        for (String allowedEndpoint : allowedEndpoints) {
            if (requestUri.startsWith(ServiceConfig.SERVICE_ENDPOINT + allowedEndpoint)) {
                accessDenied = false;
            }
        }

        if (accessDenied) {
            throw new AuthorizationException();
        }

    }

    private static AuthRole parseClaim(String roleClaim) {
        for (AuthRole authRole : AuthRole.values()) {
            if (roleClaimMatches(roleClaim, authRole)) {
                return authRole;
            }
        }
        return null;
    }

    public static boolean roleClaimMatches(String roleClaim, AuthRole role) {
        return roleClaim.equals(role.toString());
    }

    public static void enforceAccessControl(String requestUri, String roleClaim) throws AuthorizationException {
        AuthRole authRole = parseClaim(roleClaim);
        enforceAccessControl(requestUri, authRole);
    }
}
