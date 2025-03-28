package jp.kazutech.springtemplate.error;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /**
     * OTHER
     */
    UNEXPECTED_ERROR(ErrorSource.OTHER, 1, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_UNEXPECTED_ERROR, ErrorMessage.UNEXPECTED_ERROR), //

    /**
     * LOGIN
     **/
    LOGIN_FAILED(ErrorSource.LOGIN, 1, HttpStatus.UNAUTHORIZED), //
    LOGIN_USER_DENIED(ErrorSource.LOGIN, 2, HttpStatus.UNAUTHORIZED, ErrorMessage.LOGIN_USER_DENIED), //
    LOGIN_UNEXCEPTED_ERROR(ErrorSource.LOGIN, 3, HttpStatus.UNAUTHORIZED, ErrorMessage.LOGIN_UNEXCEPTED_ERROR), //
    LOGIN_REQUEST_VALIDATION_ERROR(ErrorSource.LOGIN, 4, HttpStatus.UNAUTHORIZED), //

    /**
     * VALIDATION
     **/
    VALIDATION_ERROR(ErrorSource.VALIDATION, 1, HttpStatus.BAD_REQUEST), //
    ;

    private static final int SOURCE_MARGIN = 10000;

    private final ErrorSource source;
    private final int id;
    private final HttpStatus status;

    /*
     * エラーメッセージ(内部のエラーログ等向け) ユーザー側にはMyErrorControllerでHttpStatusのreasonPhraseを返すので表示されない。
     */
    private final ErrorMessage internalMessage;

    private final ErrorMessage userOutputMessage;

    ErrorCode(ErrorSource source, int id, HttpStatus status, ErrorMessage internalMessage, ErrorMessage userOutputMessage) {
        this.source = source;
        this.id = id;
        this.status = status;
        this.internalMessage = internalMessage;
        this.userOutputMessage = userOutputMessage;
    }

    ErrorCode(ErrorSource source, int id, HttpStatus status, ErrorMessage internalMessage) {
        this.source = source;
        this.id = id;
        this.status = status;
        this.internalMessage = internalMessage;
        this.userOutputMessage = null;
    }

    ErrorCode(ErrorSource source, int id, HttpStatus status) {
        this(source, id, status, null, null);
    }

    public int getId() {
        return source.getId() * SOURCE_MARGIN + id;
    }

    public MyHttpException exception() {
        return new MyHttpException(this);
    }

    public MyHttpException exception(Throwable cause) {
        return new MyHttpException(this, cause);
    }

    public MyHttpException exception(String... internalArgs) {
        return new MyHttpException(this, null, internalArgs, null);
    }

    public MyHttpException exception(Throwable cause, String... internalArgs) {
        return new MyHttpException(this, cause, internalArgs, null);
    }

    public String getInternalMessage() {
        final var output = Optional.ofNullable(internalMessage).map(ErrorMessage::getMessage).orElse(status.getReasonPhrase());
        return "\"%s\" - ErrorCode.%s".formatted(output, this.name());
    }

    public String getInternalMessage(String... args) {
        final var output =
                Optional.ofNullable(internalMessage).map(ErrorMessage::getMessage).orElse(status.getReasonPhrase()).formatted((Object[]) args);
        return "\"%s\" - ErrorCode.%s".formatted(output, this.name());
    }

    public String getUserOutputMessage() {
        return Optional.ofNullable(userOutputMessage).map(ErrorMessage::getMessage).orElse(status.getReasonPhrase());
    }

    @Getter
    @AllArgsConstructor
    private enum ErrorSource {
        OTHER(0), //
        LOGIN(1), //
        VALIDATION(3);

        private final int id;
    }
}
