package com.demo.mytel.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.mytel.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	


}
