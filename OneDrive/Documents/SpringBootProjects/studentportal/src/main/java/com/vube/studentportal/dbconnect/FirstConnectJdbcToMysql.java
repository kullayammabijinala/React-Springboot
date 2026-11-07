package com.vube.studentportal.dbconnect;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="studentportaltb")
@Setter
@Getter
public class FirstConnectJdbcToMysql {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	String fname;
	String lname;
	Integer age;
	Double total_marks;
	
	
	
	

}
