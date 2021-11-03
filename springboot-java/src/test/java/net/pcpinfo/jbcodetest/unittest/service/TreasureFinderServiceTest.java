package net.pcpinfo.jbcodetest.unittest.service;

import net.pcpinfo.jbcodetest.BaseUnitTest;
import net.pcpinfo.jbcodetest.service.TreasureFinderService;
import net.pcpinfo.jbcodetest.unittest.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TreasureFinderServiceTest extends BaseUnitTest {

    @Autowired
    private TreasureFinderService treasureFinderService;

    @Test
    public void should_call_reset_when_reset_method_was_called() {
        treasureFinderService.reset();

        verify(jbCodetestApiClient, times(1)).reset();
    }

}
