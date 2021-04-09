package at.eder.springbootjparest.services;

import at.eder.springbootjparest.models.Company;
import at.eder.springbootjparest.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository repo;

    @Override
    public List<Company> getAll() {
        return repo.findAll();
    }

    @Override
    public Company getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Company createOrUpdateOne(Company entity) {
        return repo.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }
}
