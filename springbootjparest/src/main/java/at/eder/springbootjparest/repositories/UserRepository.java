package at.eder.springbootjparest.repositories;

import at.eder.springbootjparest.models.Admin;
import at.eder.springbootjparest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE user_type = ?1", nativeQuery = true)
    List<User> findByUserType(String userType);
}
