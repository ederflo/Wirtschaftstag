package at.eder.springbootjparest.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("pupil")
@ToString(callSuper = true)
public class Pupil extends User {

    @ManyToMany
    private Set<TimeSlot> joins;
}
