package at.eder.springbootjparest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nr;
    private LocalDate date;
    private LocalTime time;
    private String subject;
    private String content;
    private File attachment1;
    private File attachment2;
    private File attachment3;

    @ManyToOne
    private Admin sender;


    @ManyToMany
    private Set<Responsible> receivers;
}