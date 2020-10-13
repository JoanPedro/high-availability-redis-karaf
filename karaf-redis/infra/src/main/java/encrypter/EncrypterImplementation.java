package encrypter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;

import db.protocols.IEncrypter;

@Component(service = IEncrypter.class, scope = ServiceScope.SINGLETON)
public class EncrypterImplementation implements IEncrypter {

	private static final String SECRET_KEY = "MyStrong@P4ssw0rd!";

	@Activate
	public void onInit() {
		// Ciclo de vida não utilizado
	}

	@Deactivate
	public void onDestroy() {
		// Ciclo de vida não utilizado
	}

	@Override
	public String encrypt(String strToEncrypt) {
		return CryptoHelper.encrypt(strToEncrypt, SECRET_KEY);
	}

	@Override
	public String decrypt(String strToDecrypt) {
		return CryptoHelper.decrypt(strToDecrypt, SECRET_KEY);
	}

}
