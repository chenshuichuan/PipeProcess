package llcweb.jacking;

public class PipeCutingInfo {
	public int id;//编号
	public String meterial;//材料
	public int totalLength;
	public boolean isCutting = false;//是否已经下料
	public PipeCutingInfo() {

	}
	public PipeCutingInfo(int id, String meterial, int totalLength, boolean isCutting) {
		this.id = id;
		this.meterial = meterial;
		this.totalLength = totalLength;
		this.isCutting = isCutting;
	}

	public int getId() {
		return id;
	}
	public boolean isCutting() {
		return isCutting;
	}
	public void setCutting(boolean isCutting) {
		this.isCutting = isCutting;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeterial() {
		return meterial;
	}
	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}
	public int getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}
	@Override
	public String toString() {
		return "PipeCutingInfo [id=" + id + ", meterial=" + meterial + ", totalLength=" + totalLength + ", isCutting="
				+ isCutting + "]";
	}
	
	
	
}
