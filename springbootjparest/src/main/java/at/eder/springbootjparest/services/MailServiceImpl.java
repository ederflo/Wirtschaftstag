package at.eder.springbootjparest.services;

import at.eder.springbootjparest.models.Event;
import at.eder.springbootjparest.models.Mail;
import at.eder.springbootjparest.repositories.CompanyRepository;
import at.eder.springbootjparest.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailRepository repo;

    @Override
    public List<Mail> getAll() {
        return repo.findAll();
    }

    @Override
    public Mail getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Mail createOrUpdateOne(Mail entity) {
        return repo.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }
}
