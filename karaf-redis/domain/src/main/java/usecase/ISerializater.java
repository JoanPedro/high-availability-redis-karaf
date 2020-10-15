package usecase;

import java.io.FileNotFoundException;

public interface ISerializater {
	<T> T read(String filename, Class<T> classing) throws FileNotFoundException;
	<T> void write(String filename, Object obj, Class<T> classing) throws FileNotFoundException;
}
