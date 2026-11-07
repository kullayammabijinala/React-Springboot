package com.Service;

import java.util.List;

import com.Model.LearningStudentModel;

public interface LearningStudentService {
	public LearningStudentModel createLession(LearningStudentModel lsm);
	
	List<LearningStudentModel> getAllLessions();
	LearningStudentModel getLessionsById(Integer lessonId) throws Exception;
	LearningStudentModel updateLessionsById( LearningStudentModel lsm,Integer lessonId);
	LearningStudentModel patchLessionsById(LearningStudentModel lsm,Integer lessonId);
	void deleteLessionsById(Integer lessonId);

//	LearningStudentModel getBycource_lessonId(Integer lessonId);
	

}
