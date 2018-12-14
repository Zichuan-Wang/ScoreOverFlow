package batch;

public class CSVParseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int line;
	
	public CSVParseException(int line) {
		this.line = line;
	}
	
	public int getLine() {
		return line;
	}
}
