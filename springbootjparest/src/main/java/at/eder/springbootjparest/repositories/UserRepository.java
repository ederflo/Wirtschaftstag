package at.eder.springbootjparest.repositories;

import at.eder.springbootjparest.models.Admin;
import at.eder.springbootjparest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
