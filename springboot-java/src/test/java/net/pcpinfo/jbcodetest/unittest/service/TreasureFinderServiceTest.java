package net.pcpinfo.jbcodetest.unittest.service;

import lombok.SneakyThrows;
import net.pcpinfo.jbcodetest.service.TreasureFinderService;
import net.pcpinfo.jbcodetest.unittest.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;

public class TreasureFinderServiceTest extends BaseUnitTest {

    @Autowired
    private TreasureFinderService treasureFinderService;

    @Test
    @SneakyThrows
    public void should_call_reset_when_reset_method_was_called() {
        treasureFinderService.reset();

        verify(jbCodetestApiClient, times(1)).reset();
    }

    @Test
    @SneakyThrows
    public void should_post_sha256_and_puzzle_solution() {
        when(jbCodetestApiClient.getNumbers()).thenReturn(new int[] {1, 3, 5, 15});

        treasureFinderService.startPuzzle();

        verify(jbCodetestApiClient, times(1))
                .post("d6a2d6a090ad568679f8c708dc3aa4c27b2a6ae8f0e1f64ffd7a8a38fa69ff14",
                        List.of("1", "fizz", "buzz", "fizzbuzz"));
    }

    @SneakyThrows
    public void should_delete_after_post() {
        when(jbCodetestApiClient.getNumbers()).thenReturn(new int[] {1, 3, 5, 15});

        treasureFinderService.startPuzzle();

        verify(jbCodetestApiClient, times(1))
                .post("d6a2d6a090ad568679f8c708dc3aa4c27b2a6ae8f0e1f64ffd7a8a38fa69ff14",
                        List.of("1", "fizz", "buzz", "fizzbuzz"));
    }

}
