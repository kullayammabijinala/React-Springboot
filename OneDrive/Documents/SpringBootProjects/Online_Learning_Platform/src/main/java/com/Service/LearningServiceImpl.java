package com.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Model.LearningModel;
import com.Repo.LearningRepo;

@Service
public class LearningServiceImpl implements LearningService {
	
	@Autowired
	LearningRepo learningrepo;

	@Override
	public LearningModel createcource(LearningModel lm) {
		
		return learningrepo.save(lm);
	}

	@Override
	public List<LearningModel> getCources() {
		
		return learningrepo.findAll();
	}

	@Override
	public LearningModel getCourceById(Integer courceId) {
		return learningrepo.findById(courceId).get();
	}

	@Override
	public LearningModel updateCourceById(LearningModel lm, Integer courceId) {
		LearningModel exitdatafromDb=getCourceById(courceId);
		exitdatafromDb.setCourseId(lm.getCourseId());
		exitdatafromDb.setCourseName(lm.getCourseName());
		exitdatafromDb.setDescription(lm.getDescription());
		exitdatafromDb.setPrice(lm.getPrice());
		exitdatafromDb.setDurationInWeeks(lm.getDurationInWeeks());
		exitdatafromDb.setInstructorName(lm.getInstructorName());
		
		return learningrepo.save(exitdatafromDb) ;
	}

	@Override
	public LearningModel patchCourceById(LearningModel lm, Integer courceId) {
		
		 LearningModel  existdata =
				 getCourceById(courceId);
		                    

		  
		   if (lm.getCourseId() != null) {
		    existdata.setCourseId(lm.getCourseId());
		   }
		   if (lm.getCourseId() != null) {
			existdata.setCourseName(lm.getCourseName());
		   }
		   if (lm.getCourseId() != null) {
			existdata.setDescription(lm.getDescription());
		   }
		   if (lm.getCourseId() != null) {
			existdata.setPrice(lm.getPrice());
		   }
		   if (lm.getCourseId() != null) {
			existdata.setDurationInWeeks(lm.getDurationInWeeks());
		   }
		   if (lm.getCourseId() != null) {
			existdata.setInstructorName(lm.getInstructorName());
		   }
		return learningrepo.save(existdata);
	}

	@Override
	public void deleteCourceById(Integer courceId) {
		if(courceId!=null)
		{
			learningrepo.deleteById(courceId);
			System.out.println("deleted"+courceId+"sucussfully");
		}
		
	}

}
