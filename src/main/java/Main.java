import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static final String SERVICE_URI = "https://api.nasa.gov/planetary/apod?api_key=jON2rLoL1K8gpr7F0pmUN5qi7pNdCFFntC6vPkdt";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(SERVICE_URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);

        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        Post post = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
        });

        request = new HttpGet(post.getUrl());
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        response = httpClient.execute(request);

        byte[] bytes = response.getEntity().getContent().readAllBytes();
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\User\\Pictures\\pictureForNetology.jpg");
        fileOutputStream.write(bytes);
    }
}