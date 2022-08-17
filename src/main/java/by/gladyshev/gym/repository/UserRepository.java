package by.gladyshev.gym.repository;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String email);
}
