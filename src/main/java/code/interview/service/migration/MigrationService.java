package code.interview.service.migration;

import code.interview.data.User;
import code.interview.data.UserRepository;
import code.interview.dto.migration.ErrResponse;
import code.interview.dto.migration.MigrationResponse;
import code.interview.service.auth.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;

import java.util.List;
import java.util.Optional;

@Service
public class MigrationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SignInService cognitoService;

    public MigrationResponse migrateUsers() {
        List<User> users = repository.findByMigratedFalse();

        MigrationResponse response = new MigrationResponse();
        processUsersMigration(users, response);
        getRemainingUsersToMigrate(response);

        return response;
    }

    private void getRemainingUsersToMigrate(MigrationResponse response) {
        int remainingUsersToMigrate = repository.findByMigratedFalse().size();
        response.setRemainingCount(remainingUsersToMigrate);
    }


    private void processUsersMigration(List<User> users, MigrationResponse response) {
        users.forEach(
                user -> {
                    Optional<ErrResponse> error = process(user);
                    if (error.isPresent()) {
                        response.addError(error.get());
                    } else {
                        response.addSuccess(user.getEmail());
                    }
                });
    }

    private Optional<ErrResponse> process(User user) {
        AdminCreateUserResponse adminCreateUserResponse = cognitoService.migrateToCognito(user);
        if (adminCreateUserResponse.sdkHttpResponse().isSuccessful()) {
            return Optional.empty();
        } else {
            return Optional.of(new ErrResponse(user.getId(), user.getEmail(), adminCreateUserResponse.sdkHttpResponse().statusText().get()));
        }
    }
}
