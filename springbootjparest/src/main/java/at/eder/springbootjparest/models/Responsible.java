package at.eder.springbootjparest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("responsible")
@ToString(callSuper = true)
public class Responsible extends User {
}
