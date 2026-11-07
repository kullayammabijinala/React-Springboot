package com.Service;

import java.util.List;

import com.Model.LearningModel;

public interface LearningService {
	public LearningModel createcource(LearningModel lm);
	
	List<LearningModel> getCources();
	LearningModel getCourceById(Integer courceId) throws Exception;
	LearningModel updateCourceById(LearningModel lm,Integer courceId);
	LearningModel patchCourceById( LearningModel lm,Integer courceId);
	void deleteCourceById(Integer courceId);

}
