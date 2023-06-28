package com.piersoft.vm.persistence.repositories;

import com.piersoft.vm.persistence.entities.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorKYCRepository extends CrudRepository<Vendor, Long> {
}
