package net.pcpinfo.jbcodetest.client;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static net.pcpinfo.jbcodetest.util.JsonUtils.fromJson;
import static net.pcpinfo.jbcodetest.util.JsonUtils.toJson;

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
        var request = buildRequest("/fizzbuzz/reset", RequestMethod.GET);
        log.info("request: {}", request);
        var response = execute(request);
        log.info("response: {}", response);
        log.debug("response body: {}", responseBody(response));
    }

    @Retryable(value = RuntimeException.class)
    public int[] getNumbers() throws IOException {
        var request = buildRequest("/fizzbuzz", RequestMethod.GET);
        log.info("request: {}", request);
        var response= execute(request);
        log.info("response: {}", response);
        String body = responseBody(response);
        log.debug("response body: {}", body);
        if (body == null) {
            throw new IllegalStateException("invalid response body");
        }
        return fromJson(body, int[].class);
    }

    public void post(String hash, List<String> puzzle) throws IOException {
        var request = buildRequest("/fizzbuzz/" + hash, RequestMethod.POST, puzzle);
        log.info("request: {}", request);
        var response= execute(request);
        log.info("response: {}", response);
        String body = responseBody(response);
        log.debug("response body: {}", body);
    }

    public void delete(String hash) throws IOException {
        var request = buildRequest("/fizzbuzz/" + hash, RequestMethod.DELETE);
        log.info("request: {}", request);
        var response= execute(request);
        log.info("response: {}", response);
        String body = responseBody(response);
        log.debug("response body: {}", body);
    }

    @Retryable(value = {RuntimeException.class})
    public Optional<String> getTreasure(String hash) throws IOException {
        var request = buildRequest("/fizzbuzz/" + hash + "/canihastreasure", RequestMethod.GET);
        log.info("request: {}", request);
        var response= execute(request);
        log.info("response: {}", response);
        String body = responseBody(response);
        log.debug("response body: {}", body);
        return Optional.ofNullable(
                Objects.toString(fromJson(body, Map.class).get("treasure"), null));
    }

    @NotNull
    private Request buildRequest(String path, RequestMethod method, Object bodyContent) {
        RequestBody requestBody;
        if (bodyContent != null) {
            MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
            requestBody = RequestBody.create(toJson(bodyContent), jsonMediaType);
        } else {
            requestBody = null;
        }
        return new Request.Builder().url(url + path)
                .method(method.name(), requestBody)
                .build();
    }

    @NotNull
    private Request buildRequest(String path, RequestMethod method) {
        return buildRequest(path, method, null);
    }

    @NotNull
    private Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    private String responseBody(Response response) {
        try(ResponseBody body = response.body()) {
            if (body != null) {
                return body.string();
            }
        } catch (IOException e) {
            log.error("error on parse response", e);
        }
        return null;
    }
}
