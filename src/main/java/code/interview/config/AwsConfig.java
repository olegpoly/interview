package code.interview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class AwsConfig {

  @Bean
  public CognitoIdentityProviderClient cognitoIdentityClient() {
    return CognitoIdentityProviderClient.builder().build();
  }
}
