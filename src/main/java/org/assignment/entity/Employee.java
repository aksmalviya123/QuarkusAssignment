package org.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@FilterDef(name = "Employees.byName",
        defaultCondition = "name = :name",
        parameters = @ParamDef(name = "name", type = String.class))
@Filter(name = "Employees.byName")
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;
    String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
