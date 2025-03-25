package jp.kazutech.springtemplate.service;

import java.util.Optional;
import jp.kazutech.springtemplate.db.repository.UserRepository;
import jp.kazutech.springtemplate.domain.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDto> getUserByName(String name, boolean onlyEnable) {
        return userRepository.getUserByName(name) //
                .filter(userEntity -> !onlyEnable || userEntity.isEnable());
    }

    public Optional<UserDto> getUserByName(String name) {
        return getUserByName(name, true);
    }
}
