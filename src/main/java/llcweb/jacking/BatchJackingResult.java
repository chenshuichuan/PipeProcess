package llcweb.jacking;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.poifs.crypt.HashAlgorithm;

/**
 * 所有管件的套料结果
 * @author guan
 */
public class BatchJackingResult {
	
	private int cloutNum = 0;//套料产生的大于150mm的余料的数量
	private int maxCloutLength = 0;//套料产生的最长的余料的长度
	private ArrayList<PipeJackingResult> cloutPipeList = new ArrayList<>();
	//所有管上的套料方案
	private HashMap<String, PipeJackingResult> batchJackingResultMap = new HashMap<>();
	public int getCloutNum() {
		return cloutNum;
	}
	public void setCloutNum(int cloutNum) {
		this.cloutNum = cloutNum;
	}
	public int getMaxCloutLength() {
		return maxCloutLength;
	}
	public void setMaxCloutLength(int maxCloutLength) {
		this.maxCloutLength = maxCloutLength;
	}
	public ArrayList<PipeJackingResult> getCloutPipeList() {
		return cloutPipeList;
	}
	public void setCloutPipeList(ArrayList<PipeJackingResult> cloutPipeList) {
		this.cloutPipeList = cloutPipeList;
	}
	public HashMap<String, PipeJackingResult> getBatchJackingResultMap() {
		return batchJackingResultMap;
	}
	public void setBatchJackingResultMap(HashMap<String, PipeJackingResult> batchJackingResultMap) {
		this.batchJackingResultMap = batchJackingResultMap;
	}
	@Override
	public String toString() {
		return "BatchJackingResult [cloutNum=" + cloutNum + ", maxCloutLength=" + maxCloutLength + ", cloutPipeList="
				+ cloutPipeList + ", batchJackingResultMap=" + batchJackingResultMap + "]";
	}
	
	
	
	
	
}
