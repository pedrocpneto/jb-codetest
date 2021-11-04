package net.pcpinfo.jbcodetest.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.pcpinfo.jbcodetest.client.JBCodetestApiClient;
import net.pcpinfo.jbcodetest.client.JurosBaixosAPIException;
import net.pcpinfo.jbcodetest.util.FizzBuzzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static net.pcpinfo.jbcodetest.util.JsonUtils.toJson;
import static net.pcpinfo.jbcodetest.util.SHA256Utils.hashUTF8Hex;

@Service
@Slf4j
public class TreasureFinderService {

    @Autowired
    private JBCodetestApiClient client;

    public void reset() {
        log.info("init");
        try {
            client.reset();
        } catch (JurosBaixosAPIException | IOException e) {
            e.printStackTrace();
        }
        log.info("done");
    }

    @SneakyThrows
    public void treasureFind() {
        log.info("init");
        var iteration = 0;
        var treasure = Optional.<String>empty();
        while(treasure.isEmpty()) {
            log.info("iteration {}", iteration++);
            var puzzle = stream(client.getNumbers())
                    .mapToObj(FizzBuzzUtils::fizzBuzzNumber)
                    .collect(toList());
            var hash = hashUTF8Hex(toJson(puzzle));
            client.post(hash, puzzle);
            treasure = client.getTreasure(hash);
            client.delete(hash);
            log.info("has treasure: {}", treasure.isPresent());
        }
        log.info("treasure: {}", treasure.get());
        log.info("done");
    }
}
