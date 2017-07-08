package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DisscussionData {
	public static void main(String[] args) {
		ComboPooledDataSource source = new ComboPooledDataSource();
		try(Connection conn = source.getConnection()){
//			Statement statement = conn.createStatement();
//			statement.execute("drop table if exists usertable");
//			statement.execute("create table usertable(id int primary key,"
//					+ "name varchar(20),sex varchar(20),age int(4),birthday date,pic varchar(50))");
			PreparedStatement prepareStatement = conn.prepareStatement("insert into discussion(discussion_id,user_id,topic,name,body,create_time,last_update_time) values(?,?,?,?,?,?,?)");
			for(int i=1; i<5; i++){
				prepareStatement.setString(1, UUID.randomUUID().toString());
				prepareStatement.setString(2, "0001");
				prepareStatement.setString(3, Math.random()>0.5?"体育":"时政");
				prepareStatement.setString(4, "最新消息："+i);
				prepareStatement.setString(5, "此处省略100字"+i);
				prepareStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
				Thread.sleep(1000);
				prepareStatement.setTimestamp(7, new Timestamp(new Date().getTime()));
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
