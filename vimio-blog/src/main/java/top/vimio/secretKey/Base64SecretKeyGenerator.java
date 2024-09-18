package top.vimio.secretKey;

/**
 * @Description: JWT secretKey生成器
 * @Author: Vimio
 * @Date: 2024/9/8 11:55
 */
import java.security.SecureRandom;
import java.util.Base64;

public class Base64SecretKeyGenerator {
    public static String generateBase64SecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            String secretKey = generateBase64SecretKey();
            System.out.println("生成的 base64 编码的 secretKey：" + secretKey);
        }
    }
}