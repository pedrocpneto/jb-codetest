package net.pcpinfo.jbcodetest.util;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <TReturn> TReturn fromJson(@Nullable String json, Class<TReturn> type) {
        return new Gson().fromJson(json, type);
    }

}
