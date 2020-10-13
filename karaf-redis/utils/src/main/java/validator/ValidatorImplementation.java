package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import utils.IComparerValidator;
import utils.IEmailValidator;

@Component(service = { IEmailValidator.class, IComparerValidator.class })
public class ValidatorImplementation implements IEmailValidator, IComparerValidator{

	private static final String REG_EX = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,})+)$";
	private Pattern pattern;

	@Activate
	public void onInit() {
		this.pattern = Pattern.compile(REG_EX);
	}

	@Override
	public Boolean isValid(String email) {
		Matcher itMatched = this.pattern.matcher(email);
		return itMatched.matches();
	}

	@Override
	public Boolean isEqual(String valueOne, String valueTwo) {
		return valueOne.equals(valueTwo);
	}

}
