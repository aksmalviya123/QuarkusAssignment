package org.assignment.resources;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import org.assignment.entity.Employee;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.assignment.repository.EmployeeRepository;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepository;

    @GET
    @Path("/list")
    public Response listEmployees(@DefaultValue("0") @QueryParam("pageNumber") Integer pageNumber,
                                  @DefaultValue("5") @QueryParam("pageSize") Integer pageSize,
                                  @DefaultValue("name") @QueryParam("sortBy") String sortByColumn,
                                  @DefaultValue("Ascending") @QueryParam("sortOrder") Sort.Direction direction,
                                  @RequestBody Map<String, String> searchFields) {


        //First Sort the data by given sort order and column name
        PanacheQuery<Employee> employeePanacheQuery = employeeRepository.findAll(Sort.by(sortByColumn, direction));

        //Apply filter on column Name : only on "name column we've applied filter"
        employeePanacheQuery.filter("Employees.byName", Parameters.with("name", searchFields.get("name")));

        //Applying pagination on the sorted result
        List<Employee> employeesList = employeePanacheQuery.page(Page.of(pageNumber, pageSize)).list();

        if(employeesList.isEmpty()) {
            return Response.noContent().status(404).build();
        }

        return Response.ok(employeesList).status(200).build();
    }

    @POST
    @RolesAllowed("admin")
    @Transactional
    public Response saveEmployee(Employee employee) {
        //user cannot paas id
        if(employee.getId()!=null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        //save employee name in database
        employeeRepository.persist(employee);
        return Response.ok(employee).status(201).build();
    }
}
