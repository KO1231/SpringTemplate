package jp.kazutech.springtemplate.db;

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import jp.kazutech.springtemplate.domain.user.UserModel;
import jp.kazutech.springtemplate.security.UserAuthority;
import jp.kazutech.springtemplate.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder(builderMethodName = "")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TestUser {

    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @Builder.Default
    private boolean isEnable = true;

    private String name;

    @Builder.Default
    private String rawPassword = generateRandomPass();

    private String passHash;

    @Builder.Default
    private Set<UserAuthority> authorities = Set.of();

    public void setPassHash(PasswordEncoder encoder) {
        this.passHash = encoder.encode(rawPassword);
    }

    public static TestUserBuilder builder(String name) {
        return new TestUserBuilder().name(name);
    }

    private final static Random random = new Random();

    private static String generateRandomPass() {
        final int min = 32, max = 64;
        StringBuilder sb = new StringBuilder(RandomStringUtils.secure().nextAlphanumeric(min, max + 1));
        // 記号
        sb.setCharAt(random.nextInt(sb.length()),
                UserModel.ALLOWED_PASSWORD_SPECIAL_CHARS.get(random.nextInt(UserModel.ALLOWED_PASSWORD_SPECIAL_CHARS.size())));
        return StringUtil.shuffleString(sb.toString());
    }
}
