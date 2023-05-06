package pojos;

public class Payload {

	public String operation;
	public String algorithm;
	public String method;
	public String key;
	public String inputType;
	public String input;
	public String ext;

	public Payload(String operation, String algorithm, String method, String key, String inputType, String input,
			String ext) {
		this.operation = operation;
		this.algorithm = algorithm;
		this.method = method;
		this.key = key;
		this.inputType = inputType;
		this.input = input;
		this.ext = ext ;
	}

	@Override
	public String toString() {
		return "{" + "operation='" + operation + '\'' + ", algorithm='" + algorithm + '\'' + ", method='" + method
				+ '\'' + ", key='" + key + '\'' + ", inputType='" + inputType + '\'' + ", input='" + input + '\''
				+ ", ext='" + ext + '\'' + '}';
	}

	public String getOperation() {
		return operation;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getMethod() {
		return method;
	}

	public String getKey() {
		return key;
	}

	public String getInputType() {
		return inputType;
	}

	public String getInput() {
		return input;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}