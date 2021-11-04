package net.pcpinfo.jbcodetest.unittest.util;

import net.pcpinfo.jbcodetest.util.SHA256Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SHA256UtilsTest {

    @ParameterizedTest
    @CsvSource({
            "a,ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb",
            "aString,adc8403bce327a15b3add798c724ef87d8399cd30a5eb8d0c1ea08e5fb029268",
            "another-string,d2594082258e5d92ee06cb7ee39dbb5fb06000acdfac84b8976190b158ab70fc"})
    public void should_give_correct_hex_hash(String data, String hashExpected) {
        var hashResult = SHA256Utils.hashUTF8Hex(data);

        assertThat(hashResult).isEqualTo(hashExpected);
    }

}
