package code.interview.service.auth;

import code.interview.data.User;
import code.interview.data.UserRepository;
import code.interview.dto.SignInResponse;
import code.interview.dto.UserSignInRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private SignInService signInService;

    @Mock
    private UserRepository repository;

    @Test
    void testSignIn() {
        UserSignInRequest request = new UserSignInRequest("testUsername", "testPassword");
        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        when(signInService.signIn(request)).thenReturn(AdminInitiateAuthResponse.builder()
                .authenticationResult(AuthenticationResultType.builder().accessToken("testAccessToken").build())
                .build());

        SignInResponse cognitoResponse = authService.signInUser(request);
        assertNotNull(cognitoResponse);
        assertEquals(cognitoResponse.accessToken(), "testAccessToken");
    }
}
