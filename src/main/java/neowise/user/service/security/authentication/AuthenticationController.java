package neowise.user.service.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import neowise.user.service.exceptions.AuthenticationException;
import neowise.user.service.exceptions.ServiceException;
import neowise.user.service.security.dto.AuthenticationRequestDTO;
import neowise.user.service.security.dto.AuthenticationResponseDTO;

@RestController
@RequestMapping(path = AuthenticationResource.ENDPOINT)
public class AuthenticationController {

    // private Logger logger =
    // LoggerFactory.getLogger(AuthenticationController.class);

    private ServiceException handledException(Exception e, ServiceException se) throws ServiceException {
        se.setSystemException(e);
        return se;
    }

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "/app")
    @ResponseStatus(value = HttpStatus.OK)
    public AuthenticationResponseDTO authenticateApp(@RequestBody AuthenticationRequestDTO authenticationDTO)
            throws ServiceException {
        try {
            String authenticationToken = authenticationDTO.getAuthenticationToken();
            String serviceToken = authenticationService.authenticateAppAndGetServiceToken(authenticationToken);
            AuthenticationResponseDTO authenticationResponse = new AuthenticationResponseDTO(serviceToken);
            return authenticationResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw handledException(e, new AuthenticationException());
        }
    }

}
