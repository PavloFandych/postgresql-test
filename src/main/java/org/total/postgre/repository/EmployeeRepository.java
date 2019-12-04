package org.total.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.total.postgre.entity.Employee;

/**
 * @author Pavlo.Fandych
 */

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}