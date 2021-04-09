package at.eder.springbootjparest.repositories;

import at.eder.springbootjparest.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
