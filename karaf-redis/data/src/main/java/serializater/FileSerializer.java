package serializater;

import java.io.FileNotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import db.protocols.IStreamSerial;
import usecase.ISerializater;

@Component(service = ISerializater.class, scope = ServiceScope.SINGLETON)
public class FileSerializer implements ISerializater {
	
	@Reference
	IStreamSerial streamSerial;
	
	@Override
	public <T> T read(String filename, Class<T> classing) throws FileNotFoundException {
		return this.streamSerial.read(filename, classing);
	}

	@Override
	public <T> void write(String filename, Object obj, Class<T> classing) throws FileNotFoundException {
		this.streamSerial.register(classing);
		this.streamSerial.write(filename, obj);
	}
}
