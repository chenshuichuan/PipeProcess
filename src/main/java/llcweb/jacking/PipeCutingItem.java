package llcweb.jacking;

public class PipeCutingItem implements Comparable<PipeCutingItem>,Cloneable{

	private int id;
	private String batchName;
	private int areaCode;
	private String pipeMaterial;
	private int noInstalled;
	private int cuttingLength;
	private String pipeId;
	private String shape;
	private String surfaceTraet;
	private int outfieid;
	private String unit;
	private int unitId;
	private boolean isCutted;

	public int getCuttingLength() {
		return cuttingLength;
	}
	public void setCuttingLength(int cuttingLength) {
		this.cuttingLength = cuttingLength;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public int getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}

	
	

	public String getPipeMaterial() {
		return pipeMaterial;
	}
	public void setPipeMaterial(String pipeMaterial) {
		this.pipeMaterial = pipeMaterial;
	}
	public int getNoInstalled() {
		return noInstalled;
	}
	public void setNoInstalled(int noInstalled) {
		this.noInstalled = noInstalled;
	}
	
	public String getPipeId() {
		return pipeId;
	}
	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}
	public String getSurfaceTraet() {
		return surfaceTraet;
	}
	public void setSurfaceTraet(String surfaceTraet) {
		this.surfaceTraet = surfaceTraet;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public int getOutfieid() {
		return outfieid;
	}
	public void setOutfieid(int outfieid) {
		this.outfieid = outfieid;
	}

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}


	public boolean isCutted() {
		return isCutted;
	}
	public void setCutted(boolean isCutted) {
		this.isCutted = isCutted;
	}
	
	
	
	public PipeCutingItem(int id, String batchName, int areaCode, String pipeMaterial, int noInstalled,
			int cuttingLength, String pipeId, String shape, String surfaceTraet, int outfieid, String unit, int unitId,
			boolean isCutted) {
		super();
		this.id = id;
		this.batchName = batchName;
		this.areaCode = areaCode;
		this.pipeMaterial = pipeMaterial;
		this.noInstalled = noInstalled;
		this.cuttingLength = cuttingLength;
		this.pipeId = pipeId;
		this.shape = shape;
		this.surfaceTraet = surfaceTraet;
		this.outfieid = outfieid;
		this.unit = unit;
		this.unitId = unitId;
		this.isCutted = isCutted;
	}
	public PipeCutingItem() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "id="+id;
	}
	@Override
	public int compareTo(PipeCutingItem xialiaobiao) {
		return this.cuttingLength-xialiaobiao.cuttingLength;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
	
}
