package com.mobit.mobit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mobit.mobit.entity.Contact;

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>, JpaSpecificationExecutor<Contact>{

}
