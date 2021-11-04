package net.pcpinfo.jbcodetest.unittest.service;

import lombok.SneakyThrows;
import net.pcpinfo.jbcodetest.service.TreasureFinderService;
import net.pcpinfo.jbcodetest.unittest.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        when(jbCodetestApiClient.getTreasure(any())).thenReturn(Optional.of("aTreasure"));

        treasureFinderService.startPuzzle();

        verify(jbCodetestApiClient, times(1))
                .post("d6a2d6a090ad568679f8c708dc3aa4c27b2a6ae8f0e1f64ffd7a8a38fa69ff14",
                        List.of("1", "fizz", "buzz", "fizzbuzz"));
    }

    @Test
    @SneakyThrows
    public void should_call_in_order() {
        when(jbCodetestApiClient.getNumbers()).thenReturn(new int[] {4, 6, 8, 18});
        when(jbCodetestApiClient.getTreasure(any())).thenReturn(Optional.of("aTreasure"));

        treasureFinderService.startPuzzle();

        var inOrder = inOrder(jbCodetestApiClient);
        inOrder.verify(jbCodetestApiClient, times(1))
                .post("5136271ba382f3946609e5b20de6ae0f29b361fa850fc69b3d73e29b31ffe98f",
                        List.of("4", "fizz", "8", "fizz"));
        inOrder.verify(jbCodetestApiClient, times(1))
                .getTreasure("5136271ba382f3946609e5b20de6ae0f29b361fa850fc69b3d73e29b31ffe98f");
        inOrder.verify(jbCodetestApiClient, times(1))
                .delete("5136271ba382f3946609e5b20de6ae0f29b361fa850fc69b3d73e29b31ffe98f");
    }


    @ParameterizedTest
    @ValueSource(ints = {1,3,10,1000})
    @SneakyThrows
    public void should_call_while_treasure_not_found(int times) {
        var random = new Random(0);
        when(jbCodetestApiClient.getNumbers())
                .thenAnswer(invocationOnMock -> random.ints(50).toArray());
        when(jbCodetestApiClient.getTreasure(any())).thenAnswer(new Answer<>() {
            int count = 0;
            @Override
            public Optional<String> answer(InvocationOnMock invocation) throws Throwable {
                count++;
                if (count == times) {
                    return Optional.of("aTreasure");
                }
                return Optional.empty();
            }
        });

        treasureFinderService.startPuzzle();

        verify(jbCodetestApiClient, times(times)).getNumbers();
        verify(jbCodetestApiClient, times(times)).post(any(), any());
        verify(jbCodetestApiClient, times(times)).getTreasure(any());
        verify(jbCodetestApiClient, times(times)).delete(any());
    }

}
