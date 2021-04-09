package at.eder.springbootjparest.controllers;

import at.eder.springbootjparest.models.Company;
import at.eder.springbootjparest.models.Department;
import at.eder.springbootjparest.services.CompanyServiceImpl;
import at.eder.springbootjparest.services.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl svc;

    @GetMapping()
    public List<Company> getAllCompanies() { return svc.getAll(); }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) { return svc.getOne(id); }

    @PostMapping("/")
    public Company createCompany(@RequestBody Company company) { return svc.createOrUpdateOne(company); }

    @PutMapping("/")
    public Company updateCompany(@RequestBody Company company) { return svc.createOrUpdateOne(company); }

    @DeleteMapping("/{id}")
    public boolean deleteCompany(@PathVariable Long id) { return svc.delete(id); }
}