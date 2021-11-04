package net.pcpinfo.jbcodetest.service;

import lombok.extern.slf4j.Slf4j;
import net.pcpinfo.jbcodetest.client.JBCodetestApiClient;
import net.pcpinfo.jbcodetest.client.JurosBaixosAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class TreasureFinderService {

    @Autowired
    private JBCodetestApiClient client;

    public void reset() {
        log.info("reset");
        try {
            client.reset();
        } catch (JurosBaixosAPIException | IOException e) {
            e.printStackTrace();
        }
        log.info("reset done");
    }
}
