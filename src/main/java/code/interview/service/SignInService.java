package code.interview.service;

import code.interview.data.User;
import code.interview.dto.UserSignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Service
public class SignInService {

  @Autowired
  private CognitoIdentityProviderClient cognitoClient;

  @Value("${cloud.aws.cognito.user-pool-id}")
  private String userpoolId;

  @Value("${cloud.aws.cognito.api-client}")
  private String apiClientId;

  public AdminInitiateAuthResponse signIn(UserSignInRequest r) {
    return cognitoClient.adminInitiateAuth(AdminInitiateAuthRequest.builder()
            .authFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
            .clientId(apiClientId)
            .userPoolId(userpoolId)
            .authParameters(Map.of(
                    "username", r.getEmail(),
                    "password", r.getPassword()))
            .build()
    );
  }

  public AdminCreateUserResponse migrateToCognito(User user) {
    return cognitoClient.adminCreateUser(AdminCreateUserRequest.builder()
            .userPoolId(userpoolId)
            .username(user.getFirstName() + " " + user.getLastName())
            .messageAction(MessageActionType.SUPPRESS)
            .build());
  }
}
