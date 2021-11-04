package net.pcpinfo.jbcodetest.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static net.pcpinfo.jbcodetest.util.JsonUtils.fromJson;

@Component
@Slf4j
public class JBCodetestApiClient {
    // It could be some vault service, KMS e.g.
    @Value("#{environment.JB_API_KEY}")
    private String apiKey;

    @Value("${jurosbaixos.api.url}")
    private String url;

    // Is it worth to inject this?
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                var newRequest = chain.request().newBuilder()
                        .addHeader("X-API-KEY", apiKey)
                        .build();
                var response = chain.proceed(newRequest);
                return response;
            })
            .build();

    public void reset() throws IOException {
        var request = buildGetRequest("/fizzbuzz/reset");
        log.info("request: {}", request);
        var response = execute(request);
        log.info("response: {}", response);
    }

    public int[] getNumbers() throws IOException {
        var request = buildGetRequest("/fizzbuzz");
        log.info("request: {}", request);
        var response= execute(request);
        log.info("response: {}", response);
        return fromJson(response.body().string(), int[].class);
    }

    public void post(String hash, List<String> puzzle) {
        // TODO
    }

    @NotNull
    private Request buildGetRequest(String path) {
        return new Request.Builder().url(url + path)
                .get()
                .build();
    }

    @NotNull
    private Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }


}
