package net.pcpinfo.jbcodetest.controller;

import io.swagger.annotations.ApiOperation;
import net.pcpinfo.jbcodetest.service.TreasureFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/treasurefinder")
public class TreasureFinderController {

    @Autowired
    private TreasureFinderService service;

    @ApiOperation("reset treasure finder over again")
    @PostMapping("/reset")
    public void reset() {
        service.reset();
    }

    // TODO async processing
    @ApiOperation("start to find the treasure")
    @PostMapping("/treasure-find")
    public void treasureFind() {
        service.treasureFind();
    }

}
