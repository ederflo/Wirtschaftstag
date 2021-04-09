package at.eder.springbootjparest.services;

import at.eder.springbootjparest.models.User;
import at.eder.springbootjparest.repositories.MailRepository;
import at.eder.springbootjparest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public User getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public User createOrUpdateOne(User entity) {
        return repo.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        try {
            repo.deleteById(id);
        } catch(Exception ex) {

        }
        return true;
    }
}
