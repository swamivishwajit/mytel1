package com.demo.mytel.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.mytel.dto.PlanDTO;
import com.demo.mytel.entity.Plan;
import com.demo.mytel.repository.PlanRepository;
@Service
public class PlanService {
	@Autowired
	PlanRepository planRepo;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	// Fetches all plan details
	public List<PlanDTO> getAllPlans() {
		List<Plan> plans = planRepo.findAll();
		List<PlanDTO> planDTOs = new ArrayList<PlanDTO>();

		for (Plan plan : plans) {
			PlanDTO planDTO = PlanDTO.valueOf(plan);
			planDTOs.add(planDTO);
		}

		logger.info("Plan details : {}", planDTOs);
		return planDTOs;
	}

}
