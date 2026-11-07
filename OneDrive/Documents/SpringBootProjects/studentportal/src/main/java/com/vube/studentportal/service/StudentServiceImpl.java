package com.vube.studentportal.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vube.studentportal.dbconnect.FirstConnectJdbcToMysql;
import com.vube.studentportal.repo.StudentRepo;

@Service
public  class StudentServiceImpl  implements StudentService{
	
	@Autowired
    StudentRepo studentrepo; 
	
	@Override
	public FirstConnectJdbcToMysql createStudent(FirstConnectJdbcToMysql fcs) {
		
		return studentrepo.save(fcs);
	}

	@Override
	public List<FirstConnectJdbcToMysql> getAllStudent() {
		return studentrepo.findAll();
	}

	@Override
	public FirstConnectJdbcToMysql getStudentById(Integer id) {
		return studentrepo.findById(id).orElseThrow(null);
	}

	@Override
	public FirstConnectJdbcToMysql updateStudent(FirstConnectJdbcToMysql fcs,Integer id) {
		
		FirstConnectJdbcToMysql dataFromDb=getStudentById(id) ;
		dataFromDb.setAge(fcs.getAge());
		dataFromDb.setFname(fcs.getFname());
		dataFromDb.setLname(fcs.getLname());
		dataFromDb.setTotal_marks(fcs.getTotal_marks());
		return studentrepo.save(dataFromDb);
	}

	@Override
	public void deleteStudentById(Integer id) {
	    FirstConnectJdbcToMysql student = studentrepo.findById(id)
	            .orElseThrow(() -> new RuntimeException(
	                    "Student not found with id: " + id));

	    studentrepo.delete(student);
	}
	@Override
	public FirstConnectJdbcToMysql partialUpdateStudent(
	        FirstConnectJdbcToMysql student, Integer id) {

	    // Step 1: Find existing student
	    FirstConnectJdbcToMysql existingStudent =
	            studentrepo.findById(id)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Student not found with ID: " + id));

	    // Step 2: Update only the received fields
	    if (student.getFname() != null) {
	        existingStudent.setFname(student.getFname());
	    }

	    if (student.getLname() != null) {
	        existingStudent.setLname(student.getLname());
	    }

	    if (student.getAge() != null) {
	        existingStudent.setAge(student.getAge());
	    }

	    if (student.getTotal_marks() != null) {
	        existingStudent.setTotal_marks(student.getTotal_marks());
	    }

	    // Step 3: Save updated student into MySQL
	    return studentrepo.save(existingStudent);
	}
	
}
