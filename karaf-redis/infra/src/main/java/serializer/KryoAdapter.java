package serializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.ServiceScope;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import db.protocols.IStreamSerial;

@Component(service = IStreamSerial.class, scope = ServiceScope.SINGLETON)
public class KryoAdapter implements IStreamSerial {

	private Kryo kryo;

	@Activate
	void onInit() {
		// Ciclo de vida não utilizado
	}

	@Deactivate
	void onDestroy() {
		// Ciclo de vida não utilizado
	}

	@Modified
	void onChange() {
		// Ciclo de vida não utilizado
	}

	public Kryo getKryoInstance() {
		if (this.kryo == null)
			this.kryo = new Kryo();

		return this.kryo;
	}

	@Override
	public void write(String filename, Object obj) throws FileNotFoundException {
		Output output = new Output(new FileOutputStream(filename));
		this.getKryoInstance().writeObject(output, obj);
		output.close();
	}

	@Override
	public <T> void register(Class<T> classing) {
		this.getKryoInstance().register(classing);
	}

	@Override
	public <T> T read(String filename, Class<T> classing) throws FileNotFoundException {
		Input input = new Input(new FileInputStream(filename));
		T inputReader = this.getKryoInstance().readObject(input, classing);
		input.close();
		return inputReader;
	}

}
