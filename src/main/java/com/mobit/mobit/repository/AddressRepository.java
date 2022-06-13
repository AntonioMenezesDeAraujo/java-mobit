package com.mobit.mobit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mobit.mobit.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{

	List<Address> findAllByContactId(Long id);
}
