package at.eder.springbootjparest.services;

import at.eder.springbootjparest.models.Department;
import at.eder.springbootjparest.repositories.DepartmentRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository repo;

    @Override
    public List<Department> getAll() {
        return repo.findAll();
    }

    @Override
    public Department getOne(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Department createOrUpdateOne(Department department) {
        return repo.save(department);
    }

    @Override
    public boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }
}
