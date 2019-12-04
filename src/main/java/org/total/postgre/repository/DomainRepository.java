package org.total.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.total.postgre.entity.Domain;

/**
 * @author Pavlo.Fandych
 */

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

    Domain findByDomainName(final String domainName);
}