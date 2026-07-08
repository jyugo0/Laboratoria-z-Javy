package pwr.karmil.lab07catering.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndHaslo(String username, String haslo);
    Optional<User> findByUsername(String username);
}
