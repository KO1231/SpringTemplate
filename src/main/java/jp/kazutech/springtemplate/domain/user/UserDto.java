package jp.kazutech.springtemplate.domain.user;

import java.util.Set;
import java.util.UUID;
import jp.kazutech.springtemplate.db.entity.User;
import jp.kazutech.springtemplate.security.UserAuthority;

public record UserDto(long id, UUID uuid, boolean isEnable, String name, String passHash, Set<UserAuthority> authorities) {

    public UserDto(User user) throws IllegalArgumentException {
        this(user.getId(), UUID.fromString(user.getUuid()), user.isEnable(), user.getName(), user.getPassHash(), user.getAuthorities());
    }

    public UserDto(UserModel model) {
        this(model.getDbId(), model.getId(), model.isEnable(), model.getName(), model.getPassHash(), model.getAuthorities());
    }

    public UserModel intoModel() {
        return UserModel.builder().id(uuid).dbId(id).isEnable(isEnable).name(name).passHash(passHash).authorities(authorities).build();
    }
}
