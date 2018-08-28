package llcweb.jacking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

public class JackingMethod1 {
	private ArrayList<PipeJackingResult> pipeLength;//原料管件信息
	private ArrayList<PipeCutingItem> pipeCutingList;//管件的下料表信息
	
	public JackingMethod1(ArrayList<PipeJackingResult> pipeLength, ArrayList<PipeCutingItem> pipeCutingList) {
		this.pipeLength = pipeLength;
		this.pipeCutingList = pipeCutingList;
	}

	public ArrayList<PipeJackingResult> getPipeLength() {
		return pipeLength;
	}

	public void setPipeLength(ArrayList<PipeJackingResult> pipeLength) {
		this.pipeLength = pipeLength;
	}
	
	public BatchJackingResult getBatchJackingResult() throws CloneNotSupportedException {
		// 获取套料结果
		BatchJackingResult batchJackingResult = getBatchJackingResult(pipeLength,0);
		
		for(Entry<String, PipeJackingResult> result : batchJackingResult.getBatchJackingResultMap().entrySet()){
			System.out.println(result.getKey() + ": " + result.getValue());
			
			//if(result.getValue().getCloutLength()>150)
				batchJackingResult.getCloutPipeList().add(result.getValue());
		}
		return batchJackingResult;
	}
	
	/**递归套料程序
	 * @param middleResult 中间结果值
	 * @param pipeCutingItemNum 当前递归需要套料的管件单元的编号
	 * @return BatchJackingResult 递归套料的结果
	 * @throws CloneNotSupportedException
	 */
	private BatchJackingResult getBatchJackingResult(ArrayList<PipeJackingResult> middleResult, int pipeCutingItemNum) 
			throws CloneNotSupportedException {
		BatchJackingResult batchJackingResult = new BatchJackingResult();
		//按余料长度降序排序
		Collections.sort(middleResult);
		
		
		//所有的管件都套料完成返回结果
		if(pipeCutingItemNum >= pipeCutingList.size()){
			System.out.println("middleResult::::"+middleResult);
			for(PipeJackingResult result : middleResult){
				int cloutLength = result.getCloutLength();
				if(cloutLength <= 150)
					batchJackingResult.setCloutNum(batchJackingResult.getCloutNum()+1);
				if(cloutLength > batchJackingResult.getMaxCloutLength())
					batchJackingResult.setMaxCloutLength(cloutLength);
				HashMap<String, PipeJackingResult> resultMap = batchJackingResult.getBatchJackingResultMap();
				String length = new Integer(result.getPipeLength()).toString();
				int num =2;
				String tempName = length;
				while(resultMap.containsKey(length)){
					length = tempName+"("+(num++)+")";
				}
				resultMap.put(length,result);
			}
			return batchJackingResult;
		}
		
		//初始化套料结果
		batchJackingResult.setCloutNum(-1);
		PipeCutingItem pipeItem  = pipeCutingList.get(pipeCutingItemNum);
		int pipeCutingItemLength = pipeItem.getCuttingLength();
		//将管件套到不同的原料管上，获取结果
		for (int i = 0; i < middleResult.size(); i++) {
			PipeJackingResult pipeJackingResult = middleResult.get(i);
			int pipeCloutLength = pipeJackingResult.getCloutLength();
			//当前原料管的长度不足以容纳当前管件，套到下一根管件上
			if(pipeCloutLength < pipeCutingItemLength) break;
			
			//创建中间结果值2克隆中间结果值1当中数据避免对中间结果值1的数据造成影响
			ArrayList<PipeJackingResult> middleResult2 = new ArrayList<>();
			middleResult2 = clone(middleResult);
			
			//获取新的扫描结果
			PipeJackingResult newPipeJackingReulst = middleResult2.get(i);
			newPipeJackingReulst.setCloutLength(pipeCloutLength-pipeCutingItemLength);
			newPipeJackingReulst.getPipeJackingList().add(pipeItem);
			
			//根据当前管件的套料结果获取按后续安排获取的套料结果
			BatchJackingResult tempBatchJackingResult = getBatchJackingResult(middleResult2,pipeCutingItemNum+1);
			
			//比较各个套料结果的优劣
			if(compare(tempBatchJackingResult,batchJackingResult) > 0)
				batchJackingResult = tempBatchJackingResult;
		}
		return batchJackingResult;
	}
	/**比较两种套料的结果那种更优，如果第一种更优返回正数，第二种更优返回负数，相同返回0
	 * @param result1
	 * @param result2
	 * @return
	 */
	private int compare(BatchJackingResult result1,BatchJackingResult result2) {
		//产生废料的段数，段数越多越优
		if(result1.getCloutNum() == -1 && result2.getCloutNum() == -1)  
			return -1;
		if(result1.getCloutNum()!= result2.getCloutNum())  
			return result1.getCloutNum()-result2.getCloutNum();
		//段数相同，比较可利用余料的长度，最长余料长度最长的最优，最长余料的长度相同则比较次长的余料
		ArrayList<Entry<String, PipeJackingResult>> list1 = new ArrayList<>(result1.getBatchJackingResultMap().entrySet());
		ArrayList<Entry<String, PipeJackingResult>> list2 = new ArrayList<>(result2.getBatchJackingResultMap().entrySet());
		
		//比较器，排序用，按递减排序
		Comparator<Entry<String, PipeJackingResult>> comparator = new Comparator<Entry<String,PipeJackingResult>>(){
			@Override
			public int compare(Entry<String, PipeJackingResult> o1, Entry<String, PipeJackingResult> o2) {
				return o2.getValue().getCloutLength() - o1.getValue().getCloutLength();
			}
		};
		//按递减排序
		Collections.sort(list1,comparator);
		Collections.sort(list2,comparator);
		
		//按余料长度比较两个结果的优劣，最大余料长度大的那个结果好，最大结果相同则比较次大的。
		for (int i = 0; i < list1.size(); i++) {
			int cloutLength1 = list1.get(i).getValue().getCloutLength();
			int cloutLength2 = list2.get(i).getValue().getCloutLength();
			if(cloutLength1 != cloutLength2)
				return cloutLength1 - cloutLength2;
		}
		return 0;
	}

	/**ArrayList深度复制
	 * @param original
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private ArrayList<PipeJackingResult> clone(ArrayList<PipeJackingResult> original) throws CloneNotSupportedException {
		ArrayList<PipeJackingResult> copy = new ArrayList<>();
		for(PipeJackingResult result : original){
			copy.add((PipeJackingResult) result.clone());
		}
		return copy;
	}
	
	
	
}
