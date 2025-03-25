package jp.kazutech.springtemplate.util;

import jp.kazutech.springtemplate.domain.user.UserModel;
import jp.kazutech.springtemplate.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthTokenUtil {

    private final JwtTokenProvider tokenProvider;

    public String generateToken(UserModel model) {
        final var userDetails = model.intoSpringUser();
        final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return tokenProvider.generateToken(authentication);
    }
}
