package controller.dto;

public class UserDTO {

	private String name;
	private String email;
	private String password;
	private String passwordConfirmation;
	private boolean isValid = false;

	public UserDTO(Builder builder) {
		this.name = builder.name;
		this.email = builder.email;
		this.password = builder.password;
		this.passwordConfirmation = builder.passwordConfirmation;
		this.isValid = builder.isValid;
	}

	public static class Builder {

		private final String name;
		private final String email;
		private String password;
		private String passwordConfirmation;
		private boolean isValid = false;

		public Builder(String name, String email) {
			this.name = name;
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public String getPasswordConfirmation() {
			return passwordConfirmation;
		}

		public Builder setPasswordConfirmation(String passwordConfirmation) {
			this.passwordConfirmation = passwordConfirmation;
			return this;
		}

		public boolean isValid() {
			return isValid;
		}

		public Builder setValid(boolean isValid) {
			this.isValid = isValid;
			return this;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public UserDTO build() {
			return new UserDTO(this);
		}

	}

	@Override
	public String toString() {
		return this.name + " - " + this.email + " - " + this.password + " - " + this.passwordConfirmation + " - "
				+ this.isValid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
