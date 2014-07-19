package MODEL;

import java.util.ArrayList;

public class AbstractDoc {

	int docNum;
	int neighborNum;
	boolean deleted;
	ArrayList<Integer> neighborList;
	
	public void setDocNum(int num){
		docNum = num;
	}
	public void setNeighborNum(int num){
		neighborNum = num;
	}
	public void setDeletedStatus(boolean status){
		deleted = status;
	}
	public void setNeighborList(ArrayList<Integer> list){
		neighborList = list;
	}
	
	public int getDocNum(){
		return docNum;
	}
	public int getNeighborNum(){
		return neighborNum;
	}
	public boolean getStatus(){
		return deleted;
	}
	public ArrayList<Integer> getNeighborList(){
		return neighborList;
	}
}