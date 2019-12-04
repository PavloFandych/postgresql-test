package org.total.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.total.postgre.entity.Role;

/**
 * @author Pavlo.Fandych
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
