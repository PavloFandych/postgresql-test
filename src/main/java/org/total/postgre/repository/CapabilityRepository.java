package org.total.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.total.postgre.entity.Capability;
import org.total.postgre.entity.enums.CapabilityType;

/**
 * @author Pavlo.Fandych
 */

@Repository
public interface CapabilityRepository extends JpaRepository<Capability, Long> {

    Capability findByCapabilityType(final CapabilityType capabilityType);
}
