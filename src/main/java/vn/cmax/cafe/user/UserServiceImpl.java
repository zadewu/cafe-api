package vn.cmax.cafe.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.User;
import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.mapper.UserMapper;
import vn.cmax.cafe.utils.UserRequests;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserEntity updateUserPassword(String username, String newPassword) {
        UserEntity userEntity = getUser(username);
        userEntity.setPassword(newPassword);
        this.userRepository.save(userEntity);
        return getUser(username);
    }

    @Override
    public UserEntity getUser(String username) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);
        return userEntityOptional.stream().findFirst().orElseThrow();
    }

    @Override
    public UserEntity getUser(Long id) {
        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
        return userEntityOptional.stream().findFirst().orElseThrow();
    }

    @Transactional
    @Override
    public UserEntity signUp(UserRequest userRequest) {
        User user = UserRequests.toUser(userRequest);
        UserEntity userEntity = UserMapper.INSTANCE.fromUser(user);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return this.userRepository.save(userEntity);
    }
}
