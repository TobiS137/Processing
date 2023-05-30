package ccm;

public class UnfinishedMacro {
	public String prevText, key, code, type = null;
	public int start = 0;
	public int end = 0;
	public char div = '\0';
	public Integer carBack = null;
	
	public void setInfo(String prevText, String type, String key, String code, int start, int end) {
		this.prevText = prevText;
		this.type = type;
		this.key = key;
		this.code = code;
		this.start = start;
		this.end = end;
	}
	
	public void setInfo(String prevText, String type, String key, char div, String code, int start, int end) {
		this.prevText = prevText;
		this.type = type;
		this.key = key;
		this.div = div;
		this.code = code;
		this.start = start;
		this.end = end;
	}
	
	public void setCarBack(int carBack) {
		this.carBack = Integer.valueOf(carBack);
	}
	
	public void reset() {
		prevText = null;
		type = null;
		key = null;
		code = null;
		div = '\0';
		start = 0;
		end = 0;
		carBack = null;
	}
	
	public boolean isEmpty() {
		return prevText == null && key == null && code == null && start == 0 && end == 0 && carBack == null && type == null;
	}
	
	public void setPrevText(String newPrevText) {
		this.prevText = newPrevText;
	}
}