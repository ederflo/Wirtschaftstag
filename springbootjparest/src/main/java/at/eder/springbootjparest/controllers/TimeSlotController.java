package at.eder.springbootjparest.controllers;

import at.eder.springbootjparest.models.Event;
import at.eder.springbootjparest.models.TimeSlot;
import at.eder.springbootjparest.services.EventServiceImpl;
import at.eder.springbootjparest.services.TimeSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeSlots")
public class TimeSlotController {

    @Autowired
    private TimeSlotServiceImpl svc;

    @GetMapping()
    public List<TimeSlot> getAllTimeSlots() { return svc.getAll(); }

    @GetMapping("/{id}")
    public TimeSlot getTimeSlotById(@PathVariable Long id) { return svc.getOne(id); }

    @PostMapping("/")
    public TimeSlot createTimeSlot(@RequestBody TimeSlot timeSlot) { return svc.createOrUpdateOne(timeSlot); }

    @PutMapping("/")
    public TimeSlot updateTimeSlot(@RequestBody TimeSlot timeSlot) { return svc.createOrUpdateOne(timeSlot); }

    @DeleteMapping("/{id}")
    public boolean deleteTimeSlot(@PathVariable Long id) { return svc.delete(id); }
}