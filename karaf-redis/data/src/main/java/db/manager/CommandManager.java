package db.manager;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import db.protocols.ICommands;
import db.protocols.IEncrypter;
import usecase.IGetValue;
import usecase.IHashGet;
import usecase.IHashSet;
import usecase.IMapSet;
import usecase.ISetValue;

@Component(service = { ISetValue.class, IHashSet.class, IMapSet.class, IHashGet.class, IGetValue.class })
public class CommandManager implements ISetValue, IHashSet, IMapSet, IHashGet, IGetValue {
	
	@Reference
	ICommands commands;
	
	@Reference
	IEncrypter encrypter;
	
	@Activate
	public void onInit() {
		// Ciclo de vida não utilizado
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
	public String get(String key) {
		String encrypted = this.commands.get(key);
		return this.encrypter.decrypt(encrypted);
	}

	@Override
	public void set(String key, String value) {
		String encryted = this.encrypter.encrypt(value);
		this.commands.set(key, encryted);
	}

	@Override
	public String hashGet(String key, String field) {
		return this.commands.hashGet(key, field);
	}

	@Override
	public void mapSet(String key, Map<String, String> map) {
		this.commands.mapSet(key, map);
	}

	@Override
	public void hashSet(String key, String field, String value) {
		this.commands.hashSet(key, field, value);
	}

}
