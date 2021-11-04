package net.pcpinfo.jbcodetest.service;

import lombok.extern.slf4j.Slf4j;
import net.pcpinfo.jbcodetest.client.JBCodetestApiClient;
import net.pcpinfo.jbcodetest.client.JurosBaixosAPIException;
import net.pcpinfo.jbcodetest.util.FizzBuzzUtils;
import net.pcpinfo.jbcodetest.util.SHA256Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    public void startPuzzle() {
        log.info("init");
        List<String> puzzle = null;
        try {
            puzzle = stream(client.getNumbers())
                    .mapToObj(FizzBuzzUtils::fizzBuzzNumber)
                    .collect(toList());
        } catch (IOException e) {

        }
        client.post(hashUTF8Hex(toJson(puzzle)), puzzle);
        log.info("done");
    }
}
