package jp.kazutech.springtemplate.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    /**
     * OTHER
     */
    INTERNAL_UNEXPECTED_ERROR("Unexpected error occurred. Message: %s"), //
    UNEXPECTED_ERROR("Some error occurred. Please try again later. If you have this error repeatedly, please contact to the inviter."), //

    /**
     * LOGIN
     **/
    LOGIN_USER_DENIED("Denied user tried to login. (User: %s)"), //
    LOGIN_UNEXCEPTED_ERROR("Unexpected error occurred."), //

    ;

    private final String message;
}
