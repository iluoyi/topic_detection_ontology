package JDBC;

import java.sql.*;   
public class DBConnection {           
	private Connection con;     
	
	public DBConnection(){         
		String  CLASSFORNAME ="com.mysql.jdbc.Driver";        
	    String SERVANDDB ="jdbc:mysql://localhost:3306/news?user=root&password=&useUnicode=true&characterEncoding=utf-8";  
		
		try     
		{       
			Class.forName(CLASSFORNAME);  
			con = DriverManager.getConnection(SERVANDDB);    
			}         catch(Exception e)    
			{           
				e.printStackTrace();   
				}  
			}   
	
	//返回值为int，即状态值，用于添加、删除、插入等操作
	public int getRes(String sql)   
	{        
		try     
	{           
			Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);         
			int result=stmt.executeUpdate(sql);      
			return result;       
			}       
		catch(Exception e){       
			e.printStackTrace();       
			}       
		return 0;     
		}    
	//返回值为ResultSet，用于读取等操作
	public ResultSet getRes2(String sql){  
		try     
		{          
			Statement stmt2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);       
			ResultSet result=stmt2.executeQuery(sql);    
			return result;       
			}       
		catch(Exception e){     
			e.printStackTrace();      
			}         
		return null;  
		} 
	}