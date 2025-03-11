package code.interview.dto.migration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MigrationResponse {
    private int remainingCount = 0;
    private Set<String> migratedEmails = new HashSet<>();
    private List<ErrResponse> errors = new ArrayList<>();

    public void addError(ErrResponse errResponse) {
        errors.add(errResponse);
    }

    public void addSuccess(String email) {
        migratedEmails.add(email);
    }

    public int getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(int remainingCount) {
        this.remainingCount = remainingCount;
    }

    public Set<String> getMigratedEmails() {
        return migratedEmails;
    }

    public void setMigratedEmails(Set<String> migratedEmails) {
        this.migratedEmails = migratedEmails;
    }

    public List<ErrResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrResponse> errors) {
        this.errors = errors;
    }
}
