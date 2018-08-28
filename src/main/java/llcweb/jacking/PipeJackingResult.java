package llcweb.jacking;

import java.util.ArrayList;

public class PipeJackingResult implements Comparable<PipeJackingResult>,Cloneable{
	
	private int pipeLength;//管件原材料的长度
	private int cloutLength;//该管套料结束之后的余料的长度
	private boolean isBarCodePrinted;//是否已经打印了条形码
	//该管件上分配的管件的套料方案
	private ArrayList<PipeCutingItem> pipeJackingList = new ArrayList<>();
	
	
	public int getPipeLength() {
		return pipeLength;
	}
	public void setPipeLength(int pipeLength) {
		this.pipeLength = pipeLength;
	}



	public int getCloutLength() {
		return cloutLength;
	}
	public void setCloutLength(int cloutLength) {
		this.cloutLength = cloutLength;
	}



	public boolean isBarCodePrinted() {
		return isBarCodePrinted;
	}
	public void setBarCodePrinted(boolean isBarCodePrinted) {
		this.isBarCodePrinted = isBarCodePrinted;
	}



	public ArrayList<PipeCutingItem> getPipeJackingList() {
		return pipeJackingList;
	}
	public void setPipeJackingList(ArrayList<PipeCutingItem> pipeJackingList) {
		this.pipeJackingList = pipeJackingList;
	}

	@Override
	public int compareTo(PipeJackingResult o) {
		return o.cloutLength-this.getCloutLength();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException{
		PipeJackingResult o = new PipeJackingResult();
		o = (PipeJackingResult) super.clone();
		ArrayList<PipeCutingItem> pipeJackingListCopy = new ArrayList<>();
		for(PipeCutingItem item :pipeJackingList){
			pipeJackingListCopy.add((PipeCutingItem) item.clone());
		}
		o.setPipeJackingList(pipeJackingListCopy);
		return o;
	}



	@Override
	public String toString() {
		return "PipejackingResult [cloutLength=" + cloutLength + ", pipeJackingList="
				+ pipeJackingList + "]";
	}
		
	
}
