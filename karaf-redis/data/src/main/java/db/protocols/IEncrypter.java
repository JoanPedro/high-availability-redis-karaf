package db.protocols;

public interface IEncrypter {
	public String encrypt(String strToEncrypt);
	public String decrypt(String strToDecrypt);
}
