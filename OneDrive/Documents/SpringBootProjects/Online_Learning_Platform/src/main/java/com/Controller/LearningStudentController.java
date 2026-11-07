package com.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Model.LearningModel;
import com.Model.LearningStudentModel;
import com.Service.LearningService;
import com.Service.LearningStudentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/lessons")
public class LearningStudentController {
	
	@Autowired
	LearningStudentService learningstudser;
	
	@PostMapping("/postdata")
 LearningStudentModel createLession(@RequestBody LearningStudentModel lsm) {
	return learningstudser.createLession(lsm);
}
	@GetMapping("/getlessions")
	List<LearningStudentModel> getAllLessions(){
		return learningstudser.getAllLessions();
		
	}
	@GetMapping("/getlessonId/{lessonId}")
	LearningStudentModel getLessionsById(@PathVariable Integer lessonId) throws Exception{
		return learningstudser.getLessionsById(lessonId);
		
	}
	@PutMapping("/update/{lessonId}")
	LearningStudentModel updateLessionsById(@RequestBody LearningStudentModel lsm,@PathVariable Integer lessonId) {
		return learningstudser.updateLessionsById(lsm,lessonId) ;
		
	}
	@PatchMapping("/patch/{lessonId}")
	LearningStudentModel patchLessionsById(@RequestBody LearningStudentModel lsm, @PathVariable Integer lessonId) {
		return learningstudser.patchLessionsById(lsm,lessonId) ;
		
	}
	@DeleteMapping("/delete/{lessonId}")
	void deleteLessionsById(Integer lessonId) {
		if(lessonId!=null) {
			learningstudser.deleteLessionsById(lessonId);
		}
		
		
			
	}

}
