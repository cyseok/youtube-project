package com.project.youtube;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Fail.fail;

public class MysqlTest {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {

        try(Connection con =
                    DriverManager.getConnection(
                            "jdbc:mysql://youtube-database.cneecue2gjlu.ap-northeast-2.rds.amazonaws.com:3306/youtube?serverTimezone=Asia/Seoul",
                            "admin",
                            "!dbstjr201")){
			/*
			DriverManager.getConnection(
						"jdbc:mysql://3.39.12.166:3306/sys?serverTimezone=UTC",
						"yunseok",
						"dbstjr8879")){
			 */
            System.out.println(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
