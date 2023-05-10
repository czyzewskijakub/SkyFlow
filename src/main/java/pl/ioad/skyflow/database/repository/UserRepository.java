package pl.ioad.skyflow.database.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ioad.skyflow.database.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("update User u set u.firstName = ?2, u.lastName = ?3, u.email = ?4, u.passwordHash = ?5, u.profilePictureUrl = ?6, u.isAdmin = ?7 where u.userId = ?1")
    void updateUser(Long userId, String firstName, String lastName, String email, String passwordHash, String profilePictureUrl, Boolean isAdmin);

    Optional<User> findByEmail(String email);

}
