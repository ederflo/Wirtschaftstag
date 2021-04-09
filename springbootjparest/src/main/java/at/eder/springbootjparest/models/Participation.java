package at.eder.springbootjparest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private double paidAlready;
    private String comments;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Responsible responsible;


    @ManyToMany
    private Set<TimeSlot> timeSlotOffers;

    @ManyToMany
    private Set<Department> interestedDepartments;
}