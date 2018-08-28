package llcweb.jacking;


//原始管件长度类
public class PipeLength {
	private int id;
	private int length;
	private String material;
	public PipeLength() {

	}
	public PipeLength(int id, int length, String material) {
		this.id = id;
		this.length = length;
		this.material = material;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	@Override
	public String toString() {
		return "PipeLength [id=" + id + ", length=" + length + ", material=" + material + "]";
	}
	
}
