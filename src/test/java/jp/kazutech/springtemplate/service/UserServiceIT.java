package jp.kazutech.springtemplate.service;

import java.util.Set;
import jp.kazutech.springtemplate.db.TestUser;
import jp.kazutech.springtemplate.security.UserAuthority;
import jp.kazutech.springtemplate.type.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceIT extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void 正_ユーザーを取得できる() throws Exception {
        final var expected = registerUser(TestUser.builder("sample-user-123") //
                .authorities(Set.of(UserAuthority.CONTACT_POST)) //
                .build());

        // execute
        final var result = userService.getUserByName(expected.getName(), false);
        assertThat(result.isPresent()).isTrue();

        final var user = result.get().intoModel();
        assertThat(user).isEqualTo(expected);
    }

    @Test
    void 正_無効なユーザーをフィルタできる() throws Exception {
        final var expected = registerUser(TestUser.builder("sample-user-disable") //
                .isEnable(false) //
                .build());
        // execute
        final var result = userService.getUserByName(expected.getName(), true);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void 異_存在しないユーザー名で取得できない() throws Exception {
        clearUsers();

        // execute
        assertThat(userService.getUserByName("notfound-user-name", true).isEmpty()).isTrue();
        assertThat(userService.getUserByName("notfound-user-name", false).isEmpty()).isTrue();
    }
}
