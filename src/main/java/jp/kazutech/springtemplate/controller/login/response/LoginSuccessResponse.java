package jp.kazutech.springtemplate.controller.login.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoginSuccessResponse {

    String token;

}
