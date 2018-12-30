package cn.itcast.test;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import cn.itcast.entity.Student;
import cn.itcast.entity.Student.GenderType;

public class OptionalTest {
	// 保存一个对象，并获取一个对象
	@Test
	public void test1() {
		Student stu1 = new Student();
		stu1.setName("老王");
		stu1.setGender(GenderType.M);
		stu1.setBirthday(new Date());
		Optional<Student> o1 = Optional.of(stu1);
		System.out.println(o1.get() == stu1);
	}
	
	// 保存一个 null 值，并试图获取这个对象
	@Test
	public void test2() {
		Student stu = null;
		// 当我们使用 Optional.of()   这个方法进行保存时，
		// 如果保存的对象是一个null的话，那么会直接报空指针异常
		// 这样做的好处在于： 可以把异常提前到封装 Optional 对象的时候发现，
		// 一般Optional 都是作为返回值的包装类使用的，如果能在包装返回值的时候就发现null值的话
		// 那么我们就不需要在后面的程序中进行判断了
		Optional<Student> o = Optional.of(stu);
		System.out.println(o.get() == stu);
	}
	
	// 如果我们真想保存这个 null 值，也是可以的，但是要使用另一个方法保存
	@Test
	public void test3() {
		Student stu = null;
		// 如果我们想要保存那个 null值的话，那么使用 Optional.ofNullable() 可以进行保存
		// 这个方法既可以保存一个正常的对象，也可以保存一个 Null 值 
		Optional<Student> o = Optional.ofNullable(stu);
		// 保存以后，Optional 提供了一个 isPresent() 方法让我们查看保存的值是否是null
		// 如果值存在，那么返回true;   如果值不存在，也就是null的话，这里会返回一个false
		System.out.println(o.isPresent());
		// 如果我们使用的是  Optional.ofNullable() 这个方法来保存的，那么使用 get() 方法就可能
		// 取不到值，如果之前我们保存的的 null 的话，再执行此方法，则返回一个   NoSuchElementException
		System.out.println(o.get());
	}
	
	// 我们可以在获取值的时候进行灵活的处理
	@Test
	public void test4() {
		Student stu = null;
		Optional<Student> o = Optional.ofNullable(stu);
		
		// 我们设置一个默认的值
		Student defaultStu = new Student();
		defaultStu.setName("周星星");
		defaultStu.setBirthday(new Date());
		defaultStu.setGender(GenderType.M);
		
		// 我们使用 orElse()  方法进行取值时，如果 optional 对象里面的值为 null ，
		// 那么就会返回我们指定的默认值；  反之，返回optional 对象里面保存的那个值
		Student student = o.orElse(defaultStu);
		System.out.println(student == defaultStu);
	}
	
}
