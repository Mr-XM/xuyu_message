package com.xuyu.tool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

}
