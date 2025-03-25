package jp.kazutech.springtemplate.db.repository;

import java.util.Optional;
import jp.kazutech.springtemplate.db.mapper.UserMapper;
import jp.kazutech.springtemplate.domain.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public Optional<UserDto> getUserByName(String name) {
        return Optional.ofNullable(userMapper.getUserByName(name)) //
                .map(UserDto::new);
    }

}
