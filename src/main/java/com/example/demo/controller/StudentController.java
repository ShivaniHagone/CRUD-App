package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;



@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	//Insert data of new student 
	@PostMapping("/student")
	Student newStudent(@RequestBody Student newStudent) {
		return studentRepository.save(newStudent);
	}
	
	// Get all records 
	@GetMapping("/students")
	List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
	//Get student data by Id
	@GetMapping("/student/{id}")
	Student getStudentById(@PathVariable Integer id){
		return studentRepository.findById(id)
		 .orElseThrow(()->new UserNotFoundException(id));
	}
	
	//Update records using Id
	@PutMapping("/student/{id}")
	Student updateStudent(@RequestBody Student newStudent, @PathVariable Integer id) {
		return studentRepository.findById(id)
				.map(student -> {
					student.setName(newStudent.getName());
					student.setAge(newStudent.getAge());
					student.setStd(newStudent.getStd());
					student.setGrade(newStudent.getGrade());
					
					return studentRepository.save(student);
				}).orElseThrow(()->new UserNotFoundException(id));
	}
	
	//Delete record using Id 
	@DeleteMapping("/student/{id}")
	String deleteStudent(@PathVariable Integer id) {
		if(!studentRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		studentRepository.deleteById(id);
		return "Student with id "+id+" has deleted.";
		}
}
