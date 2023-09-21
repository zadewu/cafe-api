package vn.cmax.cafe.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    Page<UserEntity> findAllByOrderByUpdatedDateDesc(Pageable pageable);

    Page<UserEntity> findAllByRolesOrderByUpdatedDateDesc(Pageable pageable, UserRole role);
}
