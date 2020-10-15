package db.protocols;

import java.io.FileNotFoundException;

public interface IStreamSerial {
	<T> void register(Class<T> classing);
	<T> T read(String filename, Class<T> classing) throws FileNotFoundException;
	void write(String filename, Object obj) throws FileNotFoundException;
}
