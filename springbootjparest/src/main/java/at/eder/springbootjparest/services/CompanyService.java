package at.eder.springbootjparest.services;

import at.eder.springbootjparest.models.Company;
import at.eder.springbootjparest.repositories.CompanyRepository;
import at.eder.springbootjparest.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService extends DefaultService<Company> {

}
