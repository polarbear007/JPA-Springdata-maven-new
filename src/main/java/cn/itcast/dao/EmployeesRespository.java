package cn.itcast.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.entity.Employees;

public interface EmployeesRespository extends JpaRepository<Employees, Integer>, JpaSpecificationExecutor<Employees> {
	
}
