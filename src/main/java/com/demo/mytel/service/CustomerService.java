package com.demo.mytel.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.mytel.dto.CallDetailsDTO;
import com.demo.mytel.dto.CustomerDTO;
import com.demo.mytel.dto.FriendFamilyDTO;
import com.demo.mytel.dto.LoginDTO;
import com.demo.mytel.entity.CallDetails;
import com.demo.mytel.entity.Customer;
import com.demo.mytel.entity.FriendFamily;
import com.demo.mytel.entity.Plan;
import com.demo.mytel.repository.CallDetailsRepository;
import com.demo.mytel.repository.CustomerRepository;
@Service
public class CustomerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerRepository custRepo;

	@Autowired
	CallDetailsRepository callDetailsRepo;

	// Create a new customer
	public void createCustomer(CustomerDTO custDTO) {
		logger.info("Creation request for customer {}", custDTO);
		Customer cust = custDTO.createEntity();
		List<Long> friendAndFaamily=custDTO.getFriendAndFamily();
		List<FriendFamily> friends=new ArrayList<>();
		for(Long long1:friendAndFaamily) {
			friends.add(new FriendFamily(custDTO.getPhoneNo(), long1));
		}
		Plan p = new Plan();
		p.setPlanId(custDTO.getCurrentPlan().getPlanId());
		cust.setPlan(p);
		cust.setFriends(friends);
		custRepo.save(cust);
	}

	// Login
	public boolean login(LoginDTO loginDTO) {
		logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
		Customer cust = custRepo.findById(loginDTO.getPhoneNo()).get();
		if (cust != null && cust.getPassword().equals(loginDTO.getPassword())) {
			return true;
		}
		return false;
	}

	// Fetches full profile of a specific customer
	public CustomerDTO getCustomerProfile(Long phoneNo) {

		logger.info("Profile request for customer {}", phoneNo);
		Customer cust = custRepo.findById(phoneNo).get();
		CustomerDTO custDTO = CustomerDTO.valueOf(cust);

		logger.info("Profile for customer : {}", custDTO);
		return custDTO;
	}

	// Fetches call details of a specific customer
	public List<CallDetailsDTO> getCustomerCallDetails(@PathVariable long phoneNo) {

		logger.info("Calldetails request for customer {}", phoneNo);

		List<CallDetails> callDetails = callDetailsRepo.findByCalledBy(phoneNo);
		List<CallDetailsDTO> callsDTO = new ArrayList<CallDetailsDTO>();

		for (CallDetails call : callDetails) {
			callsDTO.add(CallDetailsDTO.valueOf(call));
		}
		logger.info("Calldetails for customer : {}", callDetails);

		return callsDTO;
	}

	// Save the friend details of a specific customer
	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) {
		logger.info("Creation request for customer {} with data {}", phoneNo, friendDTO);
		friendDTO.setPhoneNo(phoneNo);
		FriendFamily friend = friendDTO.createFriend();
		List<FriendFamily> friendList= new ArrayList<>();
		friendList.add(friend);
		
		Customer c=custRepo.getOne(phoneNo);
		c.getFriends().add(friend);
		c.setFriends(c.getFriends());
		custRepo.save(c);
	}

}
