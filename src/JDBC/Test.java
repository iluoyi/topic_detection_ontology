package JDBC;

import java.sql.*;

import ICTCLAS.I3S.AC.ICTCLAS50;

public class Test {
    public static void main(String[] srg) {
    	DBConnection dbcon=new DBConnection(); 
    	try {   
    		String sq="select * from sohu";   
    		ResultSet rs = dbcon.getRes2(sq);  
    		rs.next();  
    		System.out.println(rs.getString("contents")); 
    	//---------------------------------------------------------	
    		try
    		{
    			ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
    			//分词所需库的路径
    			String argu = ".";
    			//初始化
    		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
    			{
    				System.out.println("Init Fail!");
    				return;
    			}
    			else { System.out.println("Init Succeed!"); }

    byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(rs.getString("contents").getBytes("GB2312"), 0, 1);
    			//System.out.println(nativeBytes.length);
    			String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
    			
        		System.out.println(nativeStr); 
    				
    			testICTCLAS50.ICTCLAS_Exit();
    		}
    		catch (Exception ex)
    		{
    		}
       //-------------------------------------------------------------
    		rs.close(); 
    		} 
    	catch (Exception e) {  
    		e.printStackTrace(); 
    		}
    }
}
