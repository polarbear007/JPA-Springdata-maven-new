package cn.itcast.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class InitTest {
	@Autowired
	private DataSource dataSource;
	
	// 配置好 dataSource 看一下能不能正常获取数据库连接
	// 配置好一个实体类以后，我们再来执行一下，看能不能自动建一张表
	@Test
	public void test() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
	
	
}
