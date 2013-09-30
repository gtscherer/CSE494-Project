package crawler;

public class NoDataException extends Throwable{
	private static final long serialVersionUID = 5585962640993184813L;
	private String outputError;
	public static final int EMPTY_EDGE_LIST = 0;
	public static final int MISSING_PARENT = 1;
	public static final int MISSING_CHILD = 2;
	public static final int MISSING_HTML = 3;
	public static final int NO_FRIEND = 4;
	public static final int PARSE_FAIL = 5;
	
	@Override
	public String getMessage() {
		return outputError + super.getMessage();
	}
	private void setOutputError(int code){
		switch(code){
		case 0: 
			this.outputError = "No data to aggregate. Edge list empty.\n";
			break;
		case 1:
			this.outputError = "Parent data missing/incomplete.\n";
			break;
		case 2:
			this.outputError = "Child data missing/incomplete.\n";
			break;
		case 3:
			this.outputError = "HTML data missing. \n";
			break;
		case 4:
			this.outputError = "Friend not found.\n";
			break;
		case 5: 
			this.outputError = "Unable to parse.\n";
			break;
		}
	}
	NoDataException(){
		this.outputError = "No data to aggregate. Edge list empty.\n";
	}
	NoDataException(int code){
		setOutputError(code);
	}
	NoDataException(int code, String extra){
		setOutputError(code);
		this.outputError += "Additional details: " + extra + "\n";
	}
	
}