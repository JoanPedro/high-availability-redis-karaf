package controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.protocols.IProtocol;
import usecase.IGetValue;
import usecase.IHashGet;
import usecase.IHashSet;
import usecase.IMapSet;
import usecase.ISetValue;
import utils.IComparerValidator;
import utils.IEmailValidator;

@Component(service = IProtocol.class, scope = ServiceScope.SINGLETON, immediate = true)
public class SignUpController implements IProtocol {

	private static final String VALID_PASSWORD = "anyvalid_password";
	private static final String OTHER_VALID_PASSWORD = "other_anyvalid_password";
	private static final String INVALID_PASSWORD = "invalid_password";
	private static final String NO_MATCH_PASSWORD = "wrong_password";
	private static final String VALID_EMAIL = "valid_email@mail.com";
	private static final String OTHER_VALID_EMAIL = "other_valid_email@mail.com";
	private static final String INVALID_EMAIL = "invalid_email";

	private final Logger logger = LoggerFactory.getLogger(SignUpController.class);
	private List<User> userList = new ArrayList<>();
	private Map<String, String> mapper = new HashMap<>();

	@Reference
	IEmailValidator emailValidator;

	@Reference
	IComparerValidator comparerValidator;

	@Reference
	ISetValue setValue;

	@Reference
	IHashSet hashSetValue;
	
	@Reference
	IMapSet mapSetValue;

	@Reference
	IGetValue getValue;
	
	@Reference
	IHashGet hashGetValue;

	@Activate
	public void onInit(Map<String, Object> properties) {
		this.logger.info("SignUpController iniciado!");
		handle();
	}

	@Deactivate
	public void onDestroy() {
		this.logger.info("SignUpController destruído!");
	}
	
	@Modified
	public void onChange(Map<String, Object> properties) {
		// Ciclo de vida não utilizado
	}

	@Override
	public void handle() {
		System.out.println("//--- *** --- Exemplo 1: Criptografia --- *** --- \\");

		User validUser = new User("ValidUser", VALID_EMAIL, VALID_PASSWORD, VALID_PASSWORD);
		User invalidEmailUser = new User("InvalidUser", INVALID_EMAIL, VALID_PASSWORD, VALID_PASSWORD);
		User invalidPasswordUser = new User("OtherInvalidUser", VALID_EMAIL, INVALID_PASSWORD, NO_MATCH_PASSWORD);
		User otherValidUser = new User("OtherValidUser", OTHER_VALID_EMAIL, OTHER_VALID_PASSWORD, OTHER_VALID_PASSWORD);

		this.userList.add(validUser);
		this.userList.add(invalidEmailUser);
		this.userList.add(invalidPasswordUser);
		this.userList.add(otherValidUser);

		this.userList.stream().forEach(user -> {
			boolean isEmailValid = this.emailValidator.isValid(user.getEmail());
			boolean isEqual = this.comparerValidator.isEqual(user.getPassword(), user.getPasswordConfirmation());

			if (isEmailValid && isEqual)
				user.setIsValid(true);
		});

		this.userList.stream().filter(user -> user.getIsValid())
				.forEach(user -> this.setValue.set(user.getName(), user.getPassword()));
		
		this.userList.stream().filter(user -> user.getIsValid())
				.forEach(user -> System.out.println("Senhas descryptografadas -> " + this.getValue.get(user.getName())));
		
		// --- *** ---
		System.out.println("\n//--- *** --- Exemplo 2: Command service (1 - 1) --- *** --- \\");

		this.hashSetValue.hashSet("joan", "nome1", "Pedro");
		this.hashSetValue.hashSet("joan", "nome2", "Oliveira");
		this.hashSetValue.hashSet("joan", "nome3", "de Souza");
		
		
		for (Integer i = 1; i <= 3; i++) {
			System.out.println("HashGet (Método hashSetValue) -> " + this.hashGetValue.hashGet("joan", "nome".concat(i.toString())));
		}
		
		// --- *** ---
		System.out.println("\n//--- *** --- Exemplo 3: StreamAPI + Lambdas --- *** --- \\");

		this.mapper.put("nome1", "Pedro2");
		this.mapper.put("nome2", "Oliveira2");
		this.mapper.put("nome3", "de Souza2");
		
		this.mapper.entrySet().stream().forEach(map -> System.out.println("Stream de um Map<> -> " + map.getValue()));
		
		this.mapSetValue.mapSet("joan2", this.mapper);
		
		System.out.println("\n//--- *** --- Exemplo 4: Command service (1 - n) --- *** --- \\\\");
		for (Integer i = 1; i <= 3; i++) {
			System.out.println("HashGet (Método mapSetValue) -> " + this.hashGetValue.hashGet("joan2", "nome".concat(i.toString())));
		}
		
		//
		
		System.out.println("FINAL");
	}

}
