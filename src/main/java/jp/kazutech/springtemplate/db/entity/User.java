package jp.kazutech.springtemplate.db.entity;

import java.util.Set;
import jp.kazutech.springtemplate.security.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User {

    private final long id;

    private final String uuid;

    private final boolean isEnable;

    private final String name;

    private final String passHash;

    private final Set<UserAuthority> authorities;
}
