package at.eder.springbootjparest;

import at.eder.springbootjparest.models.*;
import at.eder.springbootjparest.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@SpringBootApplication
public class SpringBootJpArestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpArestApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner dbInserts (CompanyRepository companyRepo, DepartmentRepository departmentRepo,
                                        EventRepository eventRepo, MailRepository mailRepo,
                                        ParticipationRepository participationRepo, TimeSlotRepository timeSlotRepo,
                                        UserRepository userRepo) {
        return args -> {
            Admin admin1 = Admin.builder()
                    .name("admin1")
                    .email("admin@admin.com")
                    .pwdToken("token").build();
            Admin admin2 = Admin.builder()
                    .name("admin2")
                    .email("admin2@admin.com")
                    .pwdToken("token").build();
            userRepo.save(admin1);
            userRepo.save(admin2);

            Responsible responsible1 = Responsible.builder()
                    .name("responsible1")
                    .email("responsible1@responsible.com")
                    .pwdToken("token").build();
            Responsible responsible2 = Responsible.builder()
                    .name("responsible2")
                    .email("responsible2@responsible.com")
                    .pwdToken("token").build();
            Responsible responsible3 = Responsible.builder()
                    .name("responsible3")
                    .email("responsible3@responsible.com")
                    .pwdToken("token").build();
            Responsible responsible4 = Responsible.builder()
                    .name("responsible4")
                    .email("responsible4@responsible.com")
                    .pwdToken("token").build();
            userRepo.save(responsible1);
            userRepo.save(responsible2);
            userRepo.save(responsible3);
            userRepo.save(responsible4);

            Pupil pupil1 = Pupil.builder()
                    .name("pupil1")
                    .email("pupil1@pupil.com")
                    .pwdToken("token").build();
            Pupil pupil2 = Pupil.builder()
                    .name("pupil2")
                    .email("pupil2@pupil.com")
                    .pwdToken("token").build();
            Pupil pupil3 = Pupil.builder()
                    .name("pupil3")
                    .email("pupil3@pupil.com")
                    .pwdToken("token").build();
            Pupil pupil4 = Pupil.builder()
                    .name("pupil4")
                    .email("pupil4@pupil.com")
                    .pwdToken("token").build();
            Pupil pupil5 = Pupil.builder()
                    .name("pupil5")
                    .email("pupil5@pupil.com")
                    .pwdToken("token").build();
            Pupil pupil6 = Pupil.builder()
                    .name("pupil6")
                    .email("pupil6@pupil.com")
                    .pwdToken("token").build();
            userRepo.save(pupil1);
            userRepo.save(pupil2);
            userRepo.save(pupil3);
            userRepo.save(pupil4);
            userRepo.save(pupil5);
            userRepo.save(pupil6);

            Company company1 = Company.builder()
                    .name("company1")
                    .zipTown("Afritz am See, 9542")
                    .street("Schoberweg 4")
                    .phone("123456")
                    .eMail("comp1@comp.com")
                    .replyTo("comp1@comp.com")
                    .comments("Not a company").build();
            Company company2 = Company.builder()
                    .name("company2")
                    .zipTown("Afritz am See, 9542")
                    .street("Schoberweg 4")
                    .phone("123456")
                    .eMail("comp2@comp.com")
                    .replyTo("comp2@comp.com")
                    .comments("Not a company").build();
            Company company3 = Company.builder()
                    .name("company3")
                    .zipTown("Afritz am See, 9542")
                    .street("Schoberweg 4")
                    .phone("123456")
                    .eMail("comp3@comp.com")
                    .replyTo("comp3@comp.com")
                    .comments("Not a company").build();
            Company company4 = Company.builder()
                    .name("company4")
                    .zipTown("Afritz am See, 9542")
                    .street("Schoberweg 4")
                    .phone("123456")
                    .eMail("comp4@comp.com")
                    .replyTo("comp4@comp.com")
                    .comments("Not a company").build();
            companyRepo.save(company1);
            companyRepo.save(company2);
            companyRepo.save(company3);
            companyRepo.save(company4);

            Department department1 = Department.builder()
                    .label("Bautechnik").build();
            Department department2 = Department.builder()
                    .label("Informatik").build();
            departmentRepo.save(department1);
            departmentRepo.save(department2);

            TimeSlot timeSlot1 = TimeSlot.builder()
                    .starts(LocalTime.of(8, 0))
                    .ends(LocalTime.of(8, 50))
                    .maxParticipants(3).build();
            TimeSlot timeSlot2 = TimeSlot.builder()
                    .starts(LocalTime.of(8, 50))
                    .ends(LocalTime.of(9, 40))
                    .maxParticipants(5).build();
            TimeSlot timeSlot3 = TimeSlot.builder()
                    .starts(LocalTime.of(9, 40))
                    .ends(LocalTime.of(10, 30))
                    .maxParticipants(4).build();
            TimeSlot timeSlot4 = TimeSlot.builder()
                    .starts(LocalTime.of(10, 40))
                    .ends(LocalTime.of(11, 30))
                    .maxParticipants(7).build();
            timeSlotRepo.save(timeSlot1);
            timeSlotRepo.save(timeSlot2);
            timeSlotRepo.save(timeSlot3);
            timeSlotRepo.save(timeSlot4);

            Event event1 = Event.builder()
                    .label("Wirtschaftstag1")
                    .date(LocalDate.of(2019, 5, 10))
                    .defaultPrice(100)
                    .organiser(admin1).build();
            Event event2 = Event.builder()
                    .label("Wirtschaftstag2")
                    .date(LocalDate.of(2019, 6, 10))
                    .defaultPrice(1000)
                    .organiser(admin2).build();
            eventRepo.save(event1);
            eventRepo.save(event2);

            Mail mail1 = Mail.builder()
                    .date(LocalDate.of(2020, 12, 20))
                    .time(LocalTime.of(3, 2))
                    .subject("Anfrage").content("Termin ist am 10.05.2019")
                    .sender(admin1)
                    .receivers(Set.of(responsible1)).build();
            Mail mail2 = Mail.builder()
                    .date(LocalDate.of(2020, 12, 20))
                    .time(LocalTime.of(3, 2))
                    .subject("Anfrage").content("Termin ist am 10.06.2019")
                    .sender(admin2)
                    .receivers(Set.of(responsible3)).build();
            mailRepo.save(mail1);
            mailRepo.save(mail2);

            Participation participation1 = Participation.builder()
                    .price(120)
                    .paidAlready(40)
                    .comments("Netter dude")
                    .event(event1)
                    .company(company1)
                    .responsible(responsible1)
                    .interestedDepartments(Set.of(department1, department2))
                    .timeSlotOffers(Set.of(timeSlot1, timeSlot3, timeSlot4)).build();
            Participation participation2 = Participation.builder()
                    .price(120)
                    .paidAlready(40)
                    .comments("Netter dude")
                    .event(event1)
                    .company(company2)
                    .responsible(responsible2)
                    .interestedDepartments(Set.of(department1))
                    .timeSlotOffers(Set.of(timeSlot2, timeSlot3)).build();
            Participation participation3 = Participation.builder()
                    .price(120)
                    .paidAlready(40)
                    .comments("Netter dude")
                    .event(event2)
                    .company(company3)
                    .responsible(responsible3)
                    .interestedDepartments(Set.of(department1))
                    .timeSlotOffers(Set.of(timeSlot1, timeSlot4)).build();
            Participation participation4 = Participation.builder()
                    .price(120)
                    .paidAlready(40)
                    .comments("Netter dude")
                    .event(event2)
                    .company(company4)
                    .responsible(responsible4)
                    .interestedDepartments(Set.of(department1, department2))
                    .timeSlotOffers(Set.of(timeSlot1, timeSlot2, timeSlot3)).build();
            participationRepo.save(participation1);
            participationRepo.save(participation2);
            participationRepo.save(participation3);
            participationRepo.save(participation4);
        };
    }
}
