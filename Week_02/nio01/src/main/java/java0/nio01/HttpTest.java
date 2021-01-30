package java0.nio01;

import com.sun.org.apache.regexp.internal.RE;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HttpTest {
    private static final List<String> urls = Arrays.asList("http://localhost:8801/",
            "http://localhost:8802/","http://localhost:8803/");
    public String serverHandler() throws IOException {
        int i = new Random().nextInt(urls.size());
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .get()
                .url(urls.get(i))
                .build();
        Response response = okHttpClient.newCall(build).execute();
        if (response.isSuccessful()) {
            return "successful: "+ response.body().string();
        } else {
            return "failed: "+ response.message();
        }
    }
}
