package cn.itcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.StudentRepository;
import cn.itcast.entity.Student;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional
	public void save(Student stu) {
		studentRepository.save(stu);
	}
}
