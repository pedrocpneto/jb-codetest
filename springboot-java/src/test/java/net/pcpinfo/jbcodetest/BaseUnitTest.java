package net.pcpinfo.jbcodetest;

import net.pcpinfo.jbcodetest.client.JBCodetestApiClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BaseUnitTest.JBCodetestTestConfiguration.class)
public abstract class BaseUnitTest {

    @TestConfiguration
    @ComponentScan(basePackages = "net.pcpinfo.jbcodetest.service")
    static class JBCodetestTestConfiguration {

    }

    @MockBean
    protected JBCodetestApiClient jbCodetestApiClient;

}
