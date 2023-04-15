package beast.auth.user;

import lombok.Builder;

@Builder
record UserRecord(String username, String email, String password) {
}
