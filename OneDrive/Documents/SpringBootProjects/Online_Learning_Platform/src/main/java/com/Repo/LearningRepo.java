package com.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Model.LearningModel;

@Repository
public interface LearningRepo extends JpaRepository<LearningModel,Integer> {

	
}
