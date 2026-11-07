package com.vube.studentportal.service;

import java.util.List;

import com.vube.studentportal.dbconnect.FirstConnectJdbcToMysql;
import com.vube.studentportal.repo.StudentRepo;

public interface StudentService {
	 public  FirstConnectJdbcToMysql createStudent(FirstConnectJdbcToMysql fcs);
	 
	List <FirstConnectJdbcToMysql> getAllStudent();
	 FirstConnectJdbcToMysql getStudentById(Integer id) throws Exception;
	 FirstConnectJdbcToMysql updateStudent(FirstConnectJdbcToMysql  fcs,Integer id);
	  void deleteStudentById(Integer id);
	  FirstConnectJdbcToMysql partialUpdateStudent(
		        FirstConnectJdbcToMysql student, Integer id);;
}
