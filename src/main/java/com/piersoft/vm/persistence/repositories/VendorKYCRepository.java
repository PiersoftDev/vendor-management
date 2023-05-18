package com.piersoft.vm.persistence.repositories;

import com.piersoft.vm.persistence.entities.VendorKYC;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorKYCRepository extends CrudRepository<VendorKYC, Long> {
}
