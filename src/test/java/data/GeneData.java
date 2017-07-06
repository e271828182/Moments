package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class GeneData {
	public static void main(String[] args) {
		ComboPooledDataSource source = new ComboPooledDataSource();
		try(Connection conn = source.getConnection()){
			Statement statement = conn.createStatement();
			statement.execute("drop table if exists usertable");
			statement.execute("create table usertable(id int primary key,"
					+ "name varchar(20),sex varchar(20),age int(4),birthday date,pic varchar(50))");
			PreparedStatement prepareStatement = conn.prepareStatement("insert into usertable values(?,?,?,?,?,?)");
			for(int i=1; i<5; i++){
				prepareStatement.setInt(1, i);
				prepareStatement.setString(2, "好汉"+i);
				prepareStatement.setString(3, Math.random()>0.5?"男":"女");
				prepareStatement.setInt(4, (int)(100*Math.random()));
				prepareStatement.setDate(5, new java.sql.Date(new Date().getTime()));
				prepareStatement.setString(6, "aa.jpg");
				prepareStatement.addBatch();
				
			}
			int[] executeBatch = prepareStatement.executeBatch();
			for(int i:executeBatch){
				System.out.println(i);
			}
		}catch(Exception e){
			e.printStackTrace();
			source.close();
		}
	}
}
