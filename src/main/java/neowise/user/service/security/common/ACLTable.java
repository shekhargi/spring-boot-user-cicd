package neowise.user.service.security.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ACLTable {

    public static Map<AuthRole, Set<String>> acl;

    static {
        acl = new HashMap<>();
        // guest user
        Set<String> guestUserEndpoints = new HashSet<String>();
        guestUserEndpoints.add("/verification");
        guestUserEndpoints.add("/validation");
        guestUserEndpoints.add("/login");
        guestUserEndpoints.add("/signup");

        // user
        Set<String> userEndpoints = new HashSet<String>();
        userEndpoints.add("/user");
        userEndpoints.add("/verification");

        // init acl
        acl.put(AuthRole.GUEST_USER, guestUserEndpoints);
        acl.put(AuthRole.USER, userEndpoints);
    }
}
