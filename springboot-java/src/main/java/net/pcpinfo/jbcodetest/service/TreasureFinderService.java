package net.pcpinfo.jbcodetest.service;

import net.pcpinfo.jbcodetest.client.JBCodetestApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreasureFinderService {

    @Autowired
    private JBCodetestApiClient client;

    public void reset() {
        client.reset();
    }
}
