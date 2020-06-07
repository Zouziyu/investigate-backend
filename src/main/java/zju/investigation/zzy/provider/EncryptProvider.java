package zju.investigation.zzy.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EncryptProvider {
    @Value("${spring.authorize.salt}")
    private String salt;

    public String getMD5Code(String str) {
        String base = str + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

}
