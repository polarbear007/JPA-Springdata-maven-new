package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
}
