package at.eder.springbootjparest.controllers;

import at.eder.springbootjparest.models.Event;
import at.eder.springbootjparest.models.User;
import at.eder.springbootjparest.services.EventServiceImpl;
import at.eder.springbootjparest.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl svc;

    @GetMapping()
    public List<User> getAllUsers() { return svc.getAll(); }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) { return svc.getOne(id); }

    @PostMapping()
    public User createUser(@RequestBody User user) { return svc.createOrUpdateOne(user); }

    @PutMapping()
    public User updateUser(@RequestBody User user) { return svc.createOrUpdateOne(user); }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) { return svc.delete(id); }
}