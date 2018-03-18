package com.fs.connection.mysql.jdbc;

import com.fs.connection.mysql.jdbc.bean.Post;
import com.fs.connection.mysql.jdbc.service.JdbcTemplateService;
import com.fs.connection.mysql.jdbc.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.fs.connection.mysql.jdbc")
public class MysqlJdbcApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplateService mJdbcTemplateService;

	@Autowired
	private MybatisService mybatisService;
	public static void main(String[] args) {
		SpringApplication.run(MysqlJdbcApplication.class, args);
	}

	@Override
	public void run(final String... strings) throws Exception {
		//testJdbc();
		mJdbcTemplateService.queryById(1);
		mybatisService.queryByUserId(1);
	}

	public void testJdbc() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/test?"
				+ "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
		conn = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();
		String sql = "select *from post where user_id=1";
		ResultSet resultSet = stmt.executeQuery(sql);
		List<Post> posts = new ArrayList<>();
		while (resultSet.next()) {
			Post post = new Post();
			post.setId(resultSet.getLong("id"));
			post.setTitle(resultSet.getString("title"));
			post.setContents(resultSet.getString("contents"));
			posts.add(post);
		}
		stmt.close();
		conn.close();
	}
}