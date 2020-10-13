package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

import utils.IComparerValidator;
import utils.IEmailValidator;

@Component(service = { IEmailValidator.class, IComparerValidator.class })
public class ValidatorImplementation implements IEmailValidator, IComparerValidator {

	private static final String REG_EX = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,})+)$";
	private Pattern pattern;

	@Activate
	public void onInit() {
		this.pattern = Pattern.compile(REG_EX);
	}
	
	@Deactivate
	public void onDestroy() {
		// Ciclo de vida não utilizado
	}
	
	@Modified
	public void onChange() {
		// Ciclo de vida não utilizado
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
