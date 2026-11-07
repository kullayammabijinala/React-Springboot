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
import com.Service.LearningService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/cource")
public class LearningController {
	
	@Autowired
	LearningService learningservice ;
	
	@PostMapping("/postcource")
	LearningModel createCource(@RequestBody LearningModel lm  ) {
		return  learningservice.createcource(lm) ;
		
	}
	@GetMapping("/getcources")
	List<LearningModel> getCources() {
		return  learningservice.getCources() ;
		
	}
	@GetMapping("/getcourceId/{courceId}")
	LearningModel getCourceById(@PathVariable Integer courceId ) throws Exception {
		return  learningservice.getCourceById(courceId) ;
		
	}
	@PutMapping("/updatecource/{courceId}")
	LearningModel updateCourceById(@RequestBody LearningModel lm,@PathVariable Integer courceId )  {
		return  learningservice.updateCourceById(lm,courceId) ;
		
	}
	@PatchMapping("/patchcourceId/{courceId}")
	LearningModel partialById(@RequestBody LearningModel lm,@PathVariable Integer courceId )  {
		return  learningservice.patchCourceById(lm,courceId) ;
		
	}
	@DeleteMapping("/deletecource/{courceId}")
	String    
deleteCourceById(@PathVariable Integer courceId){
		  learningservice.deleteCourceById(courceId) ;
		return "delete"+courceId+"succeesfully";
		 
	}


}
