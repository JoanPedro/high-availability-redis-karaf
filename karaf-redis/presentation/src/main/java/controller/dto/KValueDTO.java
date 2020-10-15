package controller.dto;

public class KValueDTO {
	private String key;
	private String value;

	public KValueDTO() {
		// Noth
	}

	public String getKey() {
		return key;
	}

	public KValueDTO setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public KValueDTO setValue(String value) {
		this.value = value;
		return this;
	}
}
