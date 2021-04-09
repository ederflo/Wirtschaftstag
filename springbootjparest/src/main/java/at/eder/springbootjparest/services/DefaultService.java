package at.eder.springbootjparest.services;

import at.eder.springbootjparest.models.Department;

import java.util.List;

public interface DefaultService<T> {
    public List<T> getAll();
    public T getOne(Long id);
    public T createOrUpdateOne(T entity);
    public boolean delete(Long id);
}
