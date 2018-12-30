package cn.itcast.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.StudentRepository;
import cn.itcast.entity.Student;
import cn.itcast.entity.Student.GenderType;
import cn.itcast.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringdataTest1 {
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;
	
	// 添加一些数据，用于后面的测试
	@Test
	public void test() {
		Student stu1 = new Student();
		stu1.setName("周星星");
		stu1.setBirthday(new Date());
		stu1.setGender(GenderType.M);
		studentService.save(stu1);
		
		Student stu2 = new Student();
		stu2.setName("刘德华");
		stu2.setBirthday(new Date());
		stu2.setGender(GenderType.M);
		studentService.save(stu2);
		
		Student stu3 = new Student();
		stu3.setName("林青霞");
		stu3.setBirthday(new Date());
		stu3.setGender(GenderType.F);
		studentService.save(stu3);
		
		Student stu4 = new Student();
		stu4.setName("王祖贤");
		stu4.setBirthday(new Date());
		stu4.setGender(GenderType.F);
		studentService.save(stu4);
	}
	
	// 新版本的 JpaRepository  接口不仅继承了 PagingAndSortingRepository接口，
	// 还额外继承了 QueryByExampleExecutor 这个接口，所以我们以后可以直接使用QBE查询了
	// 传一个 example 对象，查询出一条记录
	//  如果查询的结果是多条记录，那么会报错，所以一般查询的条件中应该有一个是唯一索引列或者是主键列才用这个方法
	@Test
	public void test2() {
		// 创建一个实体类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setGender(GenderType.M);
		// 通过实体类对象创建一个example 对象
		Example<Student> example = Example.of(stu);
		// 新版本的   CRUD 接口已经没有了 findOne 方法了，以前这个方法是通过传入一个id值去查询，返回一个实体对象 
		// 现在 QueryByExampleExecutor 重新定义了 findOne() 方法，这时需要传入的是一个 example 对象
		// 然后返回一个实体对象
		Optional<Student> findOne = studentRepository.findOne(example);
		Student student = findOne.get();
		System.out.println(student);
	}
	
	// 传入一个 example 对象，查询多条记录，直接返回一个 list 集合
	@Test
	public void test3() {
		// 创建一个实体类对象
		Student stu = new Student();
		stu.setGender(GenderType.F);
		// 通过实体类对象创建一个example 对象
		Example<Student> example = Example.of(stu);
		
		List<Student> list = studentRepository.findAll(example);
		if(list != null && list.size() >0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 如果我们只传入一个example 对象的话，那么默认都是
	// 属性1 = 值1 and 属性2 = 值2 and .....   这种形式
	// 如果我们想要更加精细的匹配的话，那么可以给这个 example 再添加一个匹配器
	// 告诉springdata 怎么去用这个 example 对象里面不同的属性值的
	@Test
	public void test4() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		
		// 然后我们先不忙着创建 example 对象，先创建一个匹配器
		// 这个匹配器对象可以由静态方法创建
		ExampleMatcher matcher = ExampleMatcher.matching();
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试 matchingAll()  和  matchingAny() 的区别
	@Test
	public void test5() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试  withIgnoreCase()  方法的作用
	@Test
	public void test6() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		// 【注意】 因为我们这里只有一个String 类型的字段，所以可能看不出具体差别
		ExampleMatcher matcher = ExampleMatcher.matching()
				                               .withIgnoreCase();
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	
	// 测试 withIgnoreCase(String... propertyPath)
	@Test
	public void test7() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		// 【注意】 这个方法里面可以指定多个成员变量名，这里接收的是一个可变参数
		ExampleMatcher matcher = ExampleMatcher.matching()
				                               .withIgnoreCase("name");
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试  withIncludeNullValues() 这个方法
	@Test
	public void test8() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		// 【注意】默认情况下，我们是不会把是否为 null 作为查询条件的，但是使用这个方法
		//       可以把是否为 null 值也作为查询条件去查询
		ExampleMatcher matcher = ExampleMatcher.matching()
				                               .withIncludeNullValues();
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	// 测试 withIgnorePaths(String... ignoredPaths)
	@Test
	public void test9() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		// 【注意】默认情况下，我们会把所有非空的属性值作为查询条件，但是如果某个或者某些属性值我们不想作为查询条件
		//        那就可以使用下面的方法告诉springdata 
		//        这个方法接收的是一个可变参，可以接收多个属性名
		ExampleMatcher matcher = ExampleMatcher.matching()
				                               .withIgnorePaths("name", "birthday");
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
	//  测试一下withMatcher(String propertyPath, GenericPropertyMatcher genericPropertyMatcher) 这个方法
	@Test
	public void test10() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		//GenericPropertyMatcher  是ExampleMatcher 的一个内部类，这个内部类提供了一些匹配的方法，
		//主要是模糊匹配和正则匹配。如果我们想要使用这些方法的话，那么就可以自己new 一个这个对象，然后再调用这个对象的方法。
		ExampleMatcher matcher = ExampleMatcher.matching()
				                    .withMatcher("name", new GenericPropertyMatcher().contains());
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
	
//  测试一下withMatcher(String propertyPath, GenericPropertyMatcher genericPropertyMatcher) 这个方法
	@Test
	public void test11() throws ParseException {
		// 首先，创建一个PO类对象
		Student stu = new Student();
		stu.setName("周星星");
		stu.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-29"));
		stu.setGender(GenderType.M);
		//使用 GenericPropertyMatcher的静态方法来指定匹配的模式
		// 这里要使用到ExampleMatcher 这个接口的   StringMatcher 枚举类型
		ExampleMatcher matcher = ExampleMatcher.matching()
				                    .withMatcher("name", GenericPropertyMatcher.of(StringMatcher.CONTAINING));
		
		// 根据实体对象和匹配器生成一个example 对象
		Example<Student> example = Example.of(stu, matcher);
		
		// 根据 example 对象去查询数据
		List<Student> list = studentRepository.findAll(example); 
		
		if(list != null && list.size()>0) {
			for (Student student : list) {
				System.out.println(student);
			}
		}
	}
}
