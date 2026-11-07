package com.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cource")
@Setter
@Getter
public class LearningModel {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 Integer courseId;
    String courseName;
    String description;
    Integer durationInWeeks;
   Double  price;
    String instructorName;	
}
