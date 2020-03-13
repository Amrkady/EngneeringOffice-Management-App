package com.services;

import java.util.List;

import com.entities.Users;

public interface UserService {
	
   boolean addUser(Users user);
   
   boolean updateUser(Users user);
   
   boolean deleteUser(Users user);
   
    List<Users> getAllUser();
	

}
