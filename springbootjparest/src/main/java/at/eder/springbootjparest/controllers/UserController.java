package at.eder.springbootjparest.controllers;

import at.eder.springbootjparest.models.*;
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
    public User createUser(@RequestBody User user, @RequestParam String userType) {
        return createOrUpdateUser(user, userType);
    }

    @PutMapping()
    public User updateUser(@RequestBody User user, @RequestParam String userType) {
        return createOrUpdateUser(user, userType);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) { return svc.delete(id); }

    private User createOrUpdateUser(@RequestBody User user, @RequestParam String userType) {
        if (userType != null && !userType.isEmpty()) {
            switch (userType) {
                case "pupil":
                    return svc.createOrUpdateOne(Pupil.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .pwdToken(user.getPwdToken()).build());
                case "responsible":
                    return svc.createOrUpdateOne(Responsible.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .pwdToken(user.getPwdToken()).build());
                case "admin":
                    return svc.createOrUpdateOne(Admin.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .pwdToken(user.getPwdToken()).build());
                default:
                    return svc.createOrUpdateOne(user);
            }
        }
        return svc.createOrUpdateOne(user);
    }
}