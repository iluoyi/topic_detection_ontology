package preprocessing;

import java.io.File;
import java.sql.ResultSet;

import JDBC.DBConnection;

public class DataTransfer {

	//将新闻从MySQL数据库中变化为files
	public void databaseToFiles(){
		DBConnection dbcon=new DBConnection(); 
    	try {   
    		String sq="select * from sohu";   
    		ResultSet rs = dbcon.getRes2(sq); 
    		
    		WriteOut writer = new WriteOut();
    		
    		while (rs.next()){
    		String title = rs.getNString("title");
    		title = "./TestCorpus/"+title+".txt";
    		String content = rs.getNString("contents");
    		
    		writer.output(title, content);
    		System.out.println("Database to files ready...");
    		}
    		
    		rs.close(); 
		} 
	catch (Exception e) {  
		e.printStackTrace(); 
		}
	}
	
	//将files写入MySQL数据库中
	public void filesToDatabase(){
		File dirPath=new File("TestCorpus-28");
		File[] files = dirPath.listFiles();
		DBConnection dbcon=new DBConnection(); 
		ReadIn reader = new ReadIn();
		
    	try {  
    		String cmd = "DELETE FROM test";
    		dbcon.getRes(cmd);//首先清空test表
    		
    		for (File f : files){
    		String title = f.getName();  		
    		title = "./TestCorpus-28/"+title;
    	
    		String contents = reader.input(title);  		
    		title = f.getName().substring(0, f.getName().length()-4);
    		//System.out.println(title);
    		String sq="INSERT INTO test VALUES ('"+title+"','"+contents+"')";   
    		//System.out.println(sq);
    		dbcon.getRes(sq); 
    		}
    		System.out.println("Files to database ready...");
    	} 
    		catch (Exception e) {  
    			e.printStackTrace(); 
    			}
			
	}
	
	public static void main(String[] args){
		DataTransfer transfer = new DataTransfer();
		transfer.filesToDatabase();
	}
}
