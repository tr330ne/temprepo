package org.rusherhack.client.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.DetectedVersion;
import org.rusherhack.client.api.RusherHackAPI;

public class WebUtils {
   public static final Pattern URL_PATTERN = Pattern.compile(
      "((?:[a-z0-9]{2,}:\\/\\/)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_]{1,}\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"§ \n]|$))", 2
   );
   public static final Gson GSON = new GsonBuilder().registerTypeAdapter(UUID.class, new WebUtils.UUIDSerializer()).create();
   public static final String USER_AGENT = "RusherHack-Client/" + RusherHackAPI.getVersion() + "/" + DetectedVersion.BUILT_IN.getName();
   public static final String LEGIT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36";
   public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).followRedirects(Redirect.NORMAL).build();

   public static <T> HttpResponse<T> sendCheckedGetRequest(URI uri, BodyHandler<T> handler) throws Exception {
      HttpResponse<T> response = sendGetRequest(uri, handler);
      if (response.statusCode() != 200) {
         throw new Exception("Unexpected response status code");
      } else {
         return response;
      }
   }

   public static <T> HttpResponse<T> sendGetRequest(URI uri, BodyHandler<T> handler) throws IOException, InterruptedException {
      HttpRequest request = newRequestBuilder(uri).build();
      return HTTP_CLIENT.send(request, handler);
   }

   public static <T> HttpResponse<T> sendPostRequest(URI uri, BodyPublisher publisher, BodyHandler<T> handler) throws IOException, InterruptedException {
      HttpRequest request = newRequestBuilder(uri).POST(publisher).build();
      return HTTP_CLIENT.send(request, handler);
   }

   public static <T> HttpResponse<T> sendRequest(HttpRequest request, BodyHandler<T> handler) throws IOException, InterruptedException {
      return HTTP_CLIENT.send(request, handler);
   }

   public static Builder newRequestBuilder(URI uri) {
      return HttpRequest.newBuilder(uri).header("User-Agent", USER_AGENT);
   }

   public static Builder newJsonPostRequest(URI uri, JsonElement json) {
      return newJsonPostRequest(uri, GSON.toJson(json));
   }

   public static Builder newJsonPostRequest(URI uri, String json) {
      return newRequestBuilder(uri).POST(BodyPublishers.ofString(json)).header("Content-Type", "application/json; charset=utf-8");
   }

   public static Builder newFormPostRequest(URI uri, WebUtils.FormBuilder form) {
      return newRequestBuilder(uri).POST(BodyPublishers.ofString(form.build())).header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
   }

   public static BodyHandler<JsonElement> ofJson() {
      return ofJson(JsonElement.class);
   }

   public static <T> BodyHandler<T> ofJson(Class<T> clazz) {
      return responseInfo -> BodySubscribers.mapping(BodySubscribers.ofString(StandardCharsets.UTF_8), str -> (T)GSON.fromJson(str, clazz));
   }

   public static class FormBuilder {
      private final LinkedHashMap<String, String> map = new LinkedHashMap<>();

      private FormBuilder() {
      }

      public static WebUtils.FormBuilder create() {
         return new WebUtils.FormBuilder();
      }

      public WebUtils.FormBuilder add(String key, String value) {
         this.map.put(key, value);
         return this;
      }

      public String build() {
         StringBuilder inputString = new StringBuilder();

         for (Entry<String, String> entry : this.map.entrySet()) {
            if (!inputString.isEmpty()) {
               inputString.append("&");
            }

            inputString.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            inputString.append("=");
            inputString.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
         }

         return inputString.toString();
      }

      public BodyPublisher buildPublisher() {
         return BodyPublishers.ofString(this.build());
      }
   }

   public static class UUIDSerializer extends TypeAdapter<UUID> {
      public void write(JsonWriter out, UUID value) throws IOException {
         out.value(fromUUID(value));
      }

      public UUID read(JsonReader in) throws IOException {
         return fromString(in.nextString());
      }

      public static UUID fromString(String value) {
         return value != null && !value.equals("") ? UUID.fromString(value.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5")) : null;
      }

      public static String fromUUID(UUID value) {
         return value == null ? "" : value.toString().replace("-", "");
      }
   }
}
