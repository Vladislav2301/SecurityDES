import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// Program to implement the Data Encryption Standard(DES) algorithm

public class DES {

    private Cipher cipher = null;
    private DESKeySpec keySpec = null;
    private SecretKeyFactory keyFactory = null;

    public String encrypt (String inputString, String commonKey) throws Exception{

        String encryptedValue = "";
        SecretKey key = getSecretKey(commonKey);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = inputString.getBytes();
        byte[] outputBytes = cipher.doFinal(inputBytes);
        encryptedValue = new HexBinaryAdapter().marshal(outputBytes);
        return encryptedValue;
    }

    public String decrypt(String encryptedString, String commonKey) throws Exception{

        String decryptedValue = "";
        encryptedString = encryptedString.replace(' ', '+');
        SecretKey key = getSecretKey(commonKey);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] recoveredBytes = null;
        try {
            recoveredBytes = cipher.doFinal(new HexBinaryAdapter().unmarshal(encryptedString));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        decryptedValue = new String(recoveredBytes);
        return  decryptedValue;
    }


    private SecretKey getSecretKey(String secretPassword){

        SecretKey key = null;
        try {
            cipher = Cipher.getInstance("DES");
            keySpec = new DESKeySpec(secretPassword.getBytes("UTF8"));
            keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(keySpec);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in generating the secret Key");
        }
        return key;
    }

    public static void main(String[] args) {

        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(System.in));
        DES des = new DES();

        try {
            System.out.println("ENCRYPTION  ------------------------------");
            System.out.print("Enter Plain Message: ");
            String input = reader.readLine();
            System.out.print("Enter Key: ");
            String key = reader.readLine();
            System.out.println();

            System.out.print("Encrypted Message: ");
            String encrypted = des.encrypt(input, key);
            System.out.println(encrypted);
            System.out.println();
            System.out.println();

            System.out.println("DECRYPTION ----------------------------------");
            System.out.print("Enter Encrypted Message ");
            encrypted = reader.readLine();

            System.out.print("Enter key: ");
            key = reader.readLine();
            System.out.println();
            System.out.print("Decrypted Message: ");

            String decrypted = des.decrypt(encrypted, key);
            System.out.println(decrypted);
            System.out.println();

        }   catch (Exception e){
            e.printStackTrace();
        }

    }
}
