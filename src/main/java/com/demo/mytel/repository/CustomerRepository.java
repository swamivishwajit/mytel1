package com.demo.mytel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.mytel.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
}
