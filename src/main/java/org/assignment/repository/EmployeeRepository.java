package org.assignment.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.assignment.entity.Employee;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
}
