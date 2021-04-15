package at.eder.springbootjparest.repositories;

import at.eder.springbootjparest.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Part;
import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query(value = "SELECT * FROM participation WHERE event_id = ?1", nativeQuery = true)
    List<Participation> findByEventId(long eventId);
}
