package at.eder.springbootjparest.controllers;

import at.eder.springbootjparest.models.Company;
import at.eder.springbootjparest.models.Event;
import at.eder.springbootjparest.services.CompanyServiceImpl;
import at.eder.springbootjparest.services.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventServiceImpl svc;

    @GetMapping()
    public List<Event> getAllEvents() { return svc.getAll(); }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) { return svc.getOne(id); }

    @PostMapping("/")
    public Event createEvent(@RequestBody Event event) { return svc.createOrUpdateOne(event); }

    @PutMapping("/")
    public Event updateEvent(@RequestBody Event event) { return svc.createOrUpdateOne(event); }

    @DeleteMapping("/{id}")
    public boolean deleteEvent(@PathVariable Long id) { return svc.delete(id); }
}