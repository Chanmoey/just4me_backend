package com.gxu.just4me.repository;

import com.gxu.just4me.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Chanmoey
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOpenid(String open);
}
