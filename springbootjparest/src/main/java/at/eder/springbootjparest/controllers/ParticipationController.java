package at.eder.springbootjparest.controllers;

import at.eder.springbootjparest.models.Event;
import at.eder.springbootjparest.models.Participation;
import at.eder.springbootjparest.services.EventServiceImpl;
import at.eder.springbootjparest.services.ParticipationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    @Autowired
    private ParticipationServiceImpl svc;

    @GetMapping()
    public List<Participation> getAllParticipations() { return svc.getAll(); }

    @GetMapping("/{id}")
    public Participation getParticipationById(@PathVariable Long id) { return svc.getOne(id); }

    @PostMapping("/")
    public Participation createParticipation(@RequestBody Participation participation) { return svc.createOrUpdateOne(participation); }

    @PutMapping("/")
    public Participation updateParticipation(@RequestBody Participation participation) { return svc.createOrUpdateOne(participation); }

    @DeleteMapping("/{id}")
    public boolean deleteParticipation(@PathVariable Long id) { return svc.delete(id); }
}