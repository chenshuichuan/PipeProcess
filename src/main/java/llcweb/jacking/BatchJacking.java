package llcweb.jacking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class BatchJacking {
	private static final int  BATCH_LENGTH = 5;
	private ArrayList<Integer> pipeLength;//原料管件信息
	private ArrayList<PipeCutingItem> pipeCutingList;//管件的下料表信息
	public BatchJacking(ArrayList<Integer> pipeLength,ArrayList<PipeCutingItem> pipeCutingList) throws SQLException {
		this.pipeLength = pipeLength;
		this.pipeCutingList = pipeCutingList;
	}
	
	public BatchJacking() {};
	
	/**
	 * 分情况调用套料算法
	 * @return batchJackingResult
	 * @throws CloneNotSupportedException 
	 */
	public BatchJackingResult batchJacking() throws CloneNotSupportedException{
		BatchJackingResult batchJackingResult;
		System.out.println("下料表数量"+pipeCutingList.size());
		System.out.println("接受到的测量长度"+pipeLength);
		
		int inputLengthNum = 0;
		for (int i = 0; i < pipeLength.size(); i++) {
			inputLengthNum += pipeLength.get(i);
		}
		int targetLengthNum = 0;
		for (int i = 0; i < pipeCutingList.size(); i++) {
			targetLengthNum += pipeCutingList.get(i).getCuttingLength();
		}
		//原材料总长小于需套料总长，采用方法二
		if(inputLengthNum < targetLengthNum) {
			batchJackingResult = method2();
			return batchJackingResult;
		}
		//套料管件数量小于10根采用方法一
		if(pipeCutingList.size()<10){
			 batchJackingResult = method1();
		}
		//其他情况采用方法三
		else{
			batchJackingResult = method3();
		}
		return batchJackingResult;
	}
	
	/**
	 * 套料算法1穷举所欲的方案获取最好的那个
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	private BatchJackingResult method1() throws CloneNotSupportedException{
		//初始化结果
		ArrayList<PipeJackingResult> middleResult = new ArrayList<>();
		for(int length : pipeLength){
			PipeJackingResult pipeJackingResult = new PipeJackingResult();
			pipeJackingResult.setPipeLength(length);
			pipeJackingResult.setCloutLength(length);
			middleResult.add(pipeJackingResult);
		}
		JackingMethod1 jackingMethod1 = new JackingMethod1(middleResult,pipeCutingList);
		return jackingMethod1.getBatchJackingResult();			
	}
	
	/**
	 * 套料算法2 按工人的方法来进行套料，从最小的那根管开始，每次用和这根管长度最接近的那个来进行套料
	 * 套料长度不够时调用
	 */
	private BatchJackingResult method2(){
		Collections.sort(pipeLength);
		Collections.sort(pipeCutingList);
		if(pipeLength.get(pipeLength.size()-1) < pipeCutingList.get(0).getCuttingLength()){
			System.out.println("没有能套料的管件");
			throw new RuntimeException("没有套料的软件！");
		}
		HashMap<String, PipeJackingResult> result = new HashMap<>();
		for (int length : pipeLength ) {
			//如果存在相同length，就在名字后面加上编号
			int num = 2;
			String len =Integer.toString(length);
			String tempName = len;
			while(result.containsKey(tempName)){
				len = tempName+"("+(num++)+")";
			}
			result.put(len,jackingMinLengPipe(length));	
		}
		System.out.println(result);
		ArrayList<PipeJackingResult> cloutPipeList = new ArrayList<PipeJackingResult>();
		BatchJackingResult batchJackingResult = new BatchJackingResult();
		batchJackingResult.setBatchJackingResultMap(result);
		/*if(!pipeCutingList.isEmpty()){//判断是否将所有的下料表都套料完成
			batchJackingResult.setCloutNum(-1);
			batchJackingResult.setBatchJackingResultMap(null);
		}	*/
		
		for (String key : result.keySet()) {  
			   System.out.println("key= "+ key + " and value= " + result.get(key)); 
			   cloutPipeList.add(result.get(key));
		 }
		batchJackingResult.setCloutPipeList(cloutPipeList);
		
		return batchJackingResult;
		
	}
	
	private PipeJackingResult jackingMinLengPipe(int length) {
		PipeJackingResult pipejackingResult = new PipeJackingResult();
		pipejackingResult.setPipeLength(length);
		ArrayList<PipeCutingItem> resultList = new ArrayList<>();
		while(true){
			if(pipeCutingList.isEmpty() || length < pipeCutingList.get(0).getCuttingLength()) {
				break;
			}
			boolean isJackingHappened = false;
			for (int i = 0; i < pipeCutingList.size()-1; i++) {
				PipeCutingItem pipeCutingItemLeft = pipeCutingList.get(i);
				PipeCutingItem pipeCutingItemRight = pipeCutingList.get(i+1);
				if(length>=pipeCutingItemLeft.getCuttingLength()&&length<=pipeCutingItemRight.getCuttingLength()){
					length -= pipeCutingItemLeft.getCuttingLength();
					resultList.add(pipeCutingItemLeft);
					pipeCutingList.remove(i);
					isJackingHappened = true;
					break;
				}
			}
			if(!isJackingHappened){
				PipeCutingItem pipeCutingItemLast = pipeCutingList.get(pipeCutingList.size()-1);
				length -= pipeCutingItemLast.getCuttingLength();
				resultList.add(pipeCutingItemLast);
				pipeCutingList.remove(pipeCutingList.size()-1);
			}
		}
		pipejackingResult.setCloutLength(length);
		pipejackingResult.setPipeJackingList(resultList);
		return pipejackingResult;
	}

	/**
	 * 套料算法3》-将一大批数据拆成小批小批的然后用方法1区套料
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private BatchJackingResult method3() throws CloneNotSupportedException {
		//按长度降序排序
		Collections.sort(pipeCutingList,new Comparator<PipeCutingItem>() {

			@Override
			public int compare(PipeCutingItem p1, PipeCutingItem p2) {				
				return p2.getCuttingLength() - p1.getCuttingLength();
			}
		});
		
		ArrayList<PipeJackingResult> middleResult = new ArrayList<>();
		for(int length : pipeLength){
			PipeJackingResult pipejackingResult = new PipeJackingResult();
			pipejackingResult.setPipeLength(length);
			pipejackingResult.setCloutLength(length);
			middleResult.add(pipejackingResult);
		}
		
		BatchJackingResult batchJackingResult = new BatchJackingResult();
		
		for(int i=0;i<pipeCutingList.size();){
			
			ArrayList<PipeCutingItem> batchCutingList = new ArrayList<>();
			int count =0;
			while(count<BATCH_LENGTH){
			if(i >= pipeCutingList.size())
				break;
			PipeCutingItem pipeCutingItem = pipeCutingList.get(i);
			batchCutingList.add(pipeCutingItem);
			i++;
			count++;			
			}
			JackingMethod1 jackingMethod1 = new JackingMethod1(middleResult,batchCutingList);
			batchJackingResult =jackingMethod1.getBatchJackingResult();
			middleResult = new ArrayList<>();
			middleResult.addAll(batchJackingResult.getBatchJackingResultMap().values());
			int sum = 0;
			for(PipeJackingResult pipeJackingResult : middleResult){
				sum += pipeJackingResult.getPipeJackingList().size();
			}
			System.out.println("当前结果当中的下料段数为："+sum);			
		}
		
		return  batchJackingResult;
	}
	
	
}
