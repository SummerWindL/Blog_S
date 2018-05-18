package com.sunyard.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunyard.exception.BlogRuntimeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@SuppressWarnings("deprecation")
public class AESUtils {

	private static Logger log = LoggerFactory.getLogger(AESUtils.class);
	private final static String algorithm = "AES";

	/**
	 * BASE64����
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64����
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * ����
	 * 
	 * @param data
	 * @param rawKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String rawKey) {
		byte[] key = rawKey.getBytes();
		// Instantiate the cipher
		try {
//			 KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//             kgen.init(256, new SecureRandom(key));  
//             SecretKey secretKey = kgen.generateKey();  
//             byte[] enCodeFormat = secretKey.getEncoded();  
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			//IvParameterSpec iv = new IvParameterSpec(key);

			
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(data.getBytes());
			return encryptBASE64(encrypted);
		} catch (Exception e) {
			// App.log.info("AES", "��ȡ���ܴ�����," + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * ����
	 * 
	 * @param encrypted
	 * @param rawKey
	 * @return
	 * http://localhost:8080//apps-client/action/user/portalcheck?Para=OoaKzb6S6TJ7UGoTGqQ6VPhEsbvz%2b1f1pIp2gXcILFfm%2bjpYfFem7%2fweT9TFl0HHcQB3qkdthnbMMvEstDsvgkjo17Gqt6sB8NggtPn5SBV9NmSmPsV%2bnd2ryhPfLGQBK0GYh9m8tz8E4FtDBEkwIA%3d%3d
	 */
	public static String decrypt(String encrypted, String rawKey) {
		try {
			encrypted = encrypted.replace("%2b", "+");
			encrypted = encrypted.replace("%2f", "/");
			encrypted = encrypted.replace("%3d", "=");
			System.out.println(encrypted);
			byte[] tmp = decryptBASE64(encrypted);
			byte[] key = rawKey.getBytes();

			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(2, skeySpec);

			byte[] decrypted = cipher.doFinal(tmp);
			return new String(decrypted);
		} catch (Exception e) {
			// App.log.info("AES", "��ȡ���ܴ�����," + e.getMessage());
			return "";
		}

	}
	
	/**
	 * �õ��������ϵͳ������
	 * @param para ����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getAesInfoToMap(String para){
		
		try {
			HashMap map = new HashMap();
			String key = "BCFD3E51207C0CF5";//��Կ
		    String decrypted = decrypt(para, key);// ���ܴ�
		    if(null!=decrypted && !"".equals(decrypted)){
			    String[] dec = decrypted.split("&&");
			    String[] dec0 = dec[0].split("&");
			    String[] dec1 = dec[1].split("&");	
			    
			    if(null ==dec0 && dec0.length==0 && null ==dec1 && dec1.length==0){
			    	log.error("���ܳ���"+"���ܴ���"+para);
			    	return map;
			    }
			    
			    for (int i = 0; i < dec0.length; ++i){
				      String[] s = dec0[i].split("=");
				      map.put(s[0], s[1]);
				}
				    
			    for (int i = 0; i < dec1.length; ++i){
			      String[] s = dec1[i].split("=");
			      map.put(s[0], s[1]);
			    }
		    } else {
		    	
		    	log.error("���ܳ���"+"���ܴ���"+para);
		    }
		    
		    return map;			
		} catch (Exception e) {
			throw new BlogRuntimeException("���ܳ���"+"���ܴ���"+para);
		}

	  }
	
	/**
	 * ʱ�䳬��15�󣬲��ܵ���
	 * @param time
	 * @return
	 */
	  public static boolean checkTime(String time){
	    boolean flag = false;
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try{
	      Date d1 = df.parse(time);
	      Date d2 = df.parse(DateUtil.getCurrentDate());
	      long diff = d2.getTime() - d1.getTime();
	      long times = diff / 1000L;
	      if (times > 15L){
	        flag = true;
	      }
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return flag;
	 }

	public static void main(String[] args) throws Exception {
		String data = "Employeeid=1111&Expired_Date=2012-07-19&Form_System=GreenOffice&Target_Type=Login";
		String key = "BCFD3E51207C0CF5";
		System.out.println("��ԿΪ��" + key);
		long lStart = System.currentTimeMillis();
		// ����
		String encrypted = encrypt(data, key);
		System.out.println("ԭ�ģ�" + data);
		System.out.println("���ܺ�" + encrypted);
		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("���ܺ�ʱ��" + lUseTime + "����");

		System.out.println();
		// ����
		lStart = System.currentTimeMillis();
		String decrypted = decrypt("OoaKzb6S6TJ7UGoTGqQ6VIdAhxroa77vUBzQo6KjXct3loxM3dBm8WC6KnGRhTGInpVX/Lu6OsX0Ls1X2D3VAkjo17Gqt6sB8NggtPn5SBV9NmSmPsV+nd2ryhPfLGQBK0GYh9m8tz8E4FtDBEkwIA==", key);// ���ܴ�
		System.out.println("���ܺ� " + decrypted);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("���ܺ�ʱ��" + lUseTime + "����");

	}
}
