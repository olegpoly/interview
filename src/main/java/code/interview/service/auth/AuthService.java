package code.interview.service.auth;

import code.interview.data.User;
import code.interview.data.UserRepository;
import code.interview.dto.SignInResponse;
import code.interview.dto.UserSignInRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

import java.util.Optional;

import static software.amazon.awssdk.services.cognitoidentityprovider.model.ChallengeNameType.NEW_PASSWORD_REQUIRED;

@Service
public class AuthService {

  private final Logger log = LoggerFactory.getLogger(AuthService.class);

  @Autowired
  private SignInService cognitoService;

  @Autowired
  private UserRepository repository;

  public SignInResponse signInUser(UserSignInRequest r) {
    log.error("Authenticating user");
    validate(r);
    var response = cognitoService.signIn(r);

    if (response.challengeName() == NEW_PASSWORD_REQUIRED) {
      throw new IllegalStateException("Invalid challenge type");
    }

    AuthenticationResultType result = response.authenticationResult();
    log.debug("Successful login");
    return new SignInResponse(result.accessToken(), result.idToken(), result.refreshToken(), result.expiresIn());
  }

  private void validate(UserSignInRequest signInRequest) {
    Optional<User> user = repository.findByEmail(signInRequest.getEmail());
    if (user.isPresent()) {
      throw new IllegalStateException("User not found");
    }
  }

}
