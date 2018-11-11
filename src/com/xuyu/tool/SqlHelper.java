package com.xuyu.tool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.xuyu.message.Teacher;
import java.util.ArrayList;
import java.util.List;


/**
 * 该类主要进行数据库的操作
 */
public class SqlHelper {

	/**
	 * 获得文案
	 *
	 * @return String文案
	 */
	public String getBanner() {
		String text = null;
		try {
			MysqlConnect ps = new MysqlConnect();
			Connection con;
			Statement sql;
			ResultSet res;
			con = ps.getConnect();
			sql = con.createStatement();
			res = sql.executeQuery("select * from copywriting_table where id = (select max(id) from copywriting_table)");
			while (res.next()) {
				text = "-------------" + res.getString("banner") + "-------------\n" + res.getString("text") + "\n";

			}

			con.close();
			sql.close();
			ps.closeConnect();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return text;
	}

	/**
	 * 获取第二天所有有课老师的User_id,即客户下单 IDEL库存为0的商品
	 * @param week
	 * @return
	 */
	public static List<Teacher> getUserId(String week) {
		List<Teacher> list = new ArrayList<Teacher>();
		try {
			MysqlConnect ps = new MysqlConnect();
			Connection con;
			Statement sql;
			ResultSet res;
			con = ps.getConnect();
			sql = con.createStatement();
			res = sql.executeQuery("select * from teacher_time where " + week + " is not null");
			while (res.next()) {
				Teacher teacher = new Teacher(res.getString("user"), res.getString("name"),res.getString("mobile"));
				teacher.setItemNo(res.getString(week));
				list.add(teacher);
			}
			sql.close();
			con.close();
			ps.closeConnect();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return list;
	}

}
