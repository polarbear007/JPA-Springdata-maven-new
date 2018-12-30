package cn.itcast.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.EmployeesRespository;
import cn.itcast.dao.OrderRepository;
import cn.itcast.dao.StudentRepository;
import cn.itcast.entity.Customer;
import cn.itcast.entity.Employees;
import cn.itcast.entity.Order;
import cn.itcast.entity.Student;
import cn.itcast.entity.Student.GenderType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringdataTest2 {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private EmployeesRespository employeesRespository;
	@Autowired
	private OrderRepository orderRepository;
	
	// 演示一下最基本的用法 
	@Test
	public void test() {
		Optional<Student> optional = studentRepository.findOne(new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.where(
						 	builder.equal(root.get("name"), "周星星"), 
							builder.equal( root.get("gender"), GenderType.M)
						   );
				
				// 最后返回我们的全部查询条件即可
				return query.getRestriction();
			}
		});
		Student stu = optional.get();
		System.out.println(stu);
	}
	
	// 如果仅仅是等值条件，那么还不如使用qbe 查询呢
	// 我们演示一下这种动态查询如何添加 qbe 查询无法添加的查询条件吧
	// 
	@Test
	public void test2() {
		Pageable pageable = PageRequest.of(0, 30, Direction.DESC, "lastName", "firstName");
		employeesRespository.findAll(new Specification<Employees>() {
			// 动态拼接查询条件
			@Override
			public Predicate toPredicate(Root<Employees> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse("1953-12-12");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				query.where(
								// 模糊查询
								builder.like(root.get("lastName"), "%as%"),
								//  模糊查询
								builder.like(root.get("firstName"), "a%"),
								// 日期比较
								builder.greaterThan(root.get("birthDate"), date)
							);
				
				// 最后返回我们的全部查询条件即可
				return query.getRestriction();
			}
		}, pageable);
	}
	
	// 演示一下关联查询
	@Test
	public void  test3() {
		List<Order> list = orderRepository.findAll(new Specification<Order>() {
	
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Join<Order, Customer> customer = root.join("customer");
				query.where(builder.equal(customer.get("cid"), 1));
				
				return query.getRestriction();
			}
		});
		
		if(list != null && list.size() > 0) {
			for (Order order : list) {
				System.out.println(order);
			}
		}
	}
	
}
