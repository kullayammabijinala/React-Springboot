package com.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Model.LearningStudentModel;
import com.Repo.LearningStudentRepo;

@Service
public class LearningStudentServiceImpl  implements LearningStudentService{

	@Autowired
	LearningStudentRepo  learningStudRepo;
	
	@Override
	public LearningStudentModel createLession(LearningStudentModel lsm) {
		
		return  learningStudRepo.save(lsm);
	}

	@Override
	public List<LearningStudentModel> getAllLessions() {
		// TODO Auto-generated method stub
		return  learningStudRepo.findAll();
	}

	@Override
	public LearningStudentModel getLessionsById(Integer lessonId) {
		// TODO Auto-generated method stub
		return  learningStudRepo.findById(lessonId).get();
	}

	@Override
	public LearningStudentModel updateLessionsById(LearningStudentModel lsm, Integer lessonId) {
		LearningStudentModel existLessiondata=getLessionsById(lessonId);
		existLessiondata.setLessonId(lsm.getLessonId());
		existLessiondata.setLessonTitle(lsm.getLessonTitle());
		existLessiondata.setLessonNumber(lsm.getLessonNumber());
		existLessiondata.setLessonDescription(lsm.getLessonDescription());
		existLessiondata.setVideoUrl(lsm.getVideoUrl());
		existLessiondata.setCourse(lsm.getCourse());
		
		return  learningStudRepo.save(existLessiondata);
	}

	@Override
	public LearningStudentModel patchLessionsById(LearningStudentModel lsm, Integer lessonId) {
		LearningStudentModel existLessiondata=getLessionsById(lessonId);
		if(lsm.getLessonId()!=null) {
		existLessiondata.setLessonId(lsm.getLessonId());
		}
		if(lsm.getLessonTitle()!=null) {
		existLessiondata.setLessonTitle(lsm.getLessonTitle());
		}
		if(lsm.getLessonNumber()!=null) {
		existLessiondata.setLessonNumber(lsm.getLessonNumber());
		}
		if(lsm.getLessonDescription()!=null) {
		
		existLessiondata.setLessonDescription(lsm.getLessonDescription());
		}
		if(lsm.getVideoUrl()!=null) {
		existLessiondata.setVideoUrl(lsm.getVideoUrl());
		}
		if(lsm.getCourse()!=null) {
		existLessiondata.setCourse(lsm.getCourse());
		}
		
		return  learningStudRepo.save(existLessiondata);
	}

	@Override
	public void deleteLessionsById(Integer lessonId) {
		
		
	}
//	public  List<LearningStudentModel> getBycource_lessonId(Integer lessonId) {
//		return learningStudRepo.findById(lessonId);
//	}
	

}
