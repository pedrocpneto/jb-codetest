package net.pcpinfo.jbcodetest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SHA256Utils {

    public static String hashUTF8Hex(String data) {
        return DigestUtils.sha256Hex(data);
    }

}
