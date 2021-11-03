package net.pcpinfo.jbcodetest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "jbcodetestapi", url = "http://codetest.jurosbaixos.com.br/")
public interface JBCodetestApiClient {
    @GetMapping("/fizzbuzz/reset")
    void reset();
}
