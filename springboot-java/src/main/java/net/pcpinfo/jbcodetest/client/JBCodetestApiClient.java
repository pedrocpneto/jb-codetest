package net.pcpinfo.jbcodetest.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
                if (!response.isSuccessful()) {
                    throw new JurosBaixosAPIException();
                }
                return response;
            })
            .build();

    public void reset() throws IOException {
        var request = new Request.Builder().url(url + "/fizzbuzz/reset")
                .get()
                .build();
        var call = client.newCall(request);
        var response = call.execute();
    }
}
