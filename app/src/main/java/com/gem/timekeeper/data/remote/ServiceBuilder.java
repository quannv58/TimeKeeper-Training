package com.gem.timekeeper.data.remote;

import com.gem.timekeeper.App;
import com.gem.timekeeper.data.dto.UserDTO;
import com.gem.timekeeper.data.remote.services.SurveyService;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gemvietnam.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.gem.timekeeper.BuildConfig;
import com.gem.timekeeper.data.remote.services.LocationService;
import com.gem.timekeeper.data.remote.services.CommonService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Web services builder
 * Created by neo on 2/15/2016.
 */
public class ServiceBuilder {

  private static Retrofit getRetrofit(String endPoint) {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG) {
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    OkHttpClient client = new OkHttpClient.Builder()
        .readTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(90, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(new Interceptor() {
          // User agent default
          @Override
          public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            // Build request with headers
            Request.Builder builder = original.newBuilder()
                .header("User-Agent", System.getProperty("http.agent"))
//                .header("USER-TOKEN", userToken)
                .header("Content-Type", "application/json")
                .method(original.method(), original.body());

            UserDTO userDTO = PrefWrapper.getUser(App.getInstance());
            if (userDTO != null && !StringUtils.isEmpty(userDTO.getAccessToken())) {
              builder.header("Authorization", userDTO.getTokenType() + " " + userDTO.getAccessToken());
            }

            return chain.proceed(builder.build());
          }
        })
        .build();

//    if (sInstance == null) {
    GsonBuilder builder = new GsonBuilder()
        .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
          public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
          }
        });

    Gson gSon = builder.setLenient().create();

    return new Retrofit.Builder()
        .baseUrl(endPoint)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gSon))
        .build();
//    }
  }

  private static String getBaseUrl() {
    return BuildConfig.BASE_URL;
  }

  private static String buildUrl(String serviceName) {
    return getBaseUrl() + BuildConfig.API_VERSION  + "/" + (StringUtils.isEmpty(serviceName) ? "" : serviceName + "/");
  }

  /**
   * private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
   * KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
   * <p>
   * // Loading CAs from an InputStream
   * CertificateFactory cf = null;
   * cf = CertificateFactory.getInstance("X.509");
   * <p>
   * Certificate ca;
   * // I'm using Java7. If you used Java6 ic_close it manually with finally.
   * try (InputStream cert = context.getResources().openRawResource(R.raw.kpmg_data)) {
   * ca = cf.generateCertificate(cert);
   * }
   * <p>
   * // Creating a KeyStore containing our trusted CAs
   * String keyStoreType = KeyStore.getDefaultType();
   * KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
   * keyStore.load(null, null);
   * keyStore.setCertificateEntry("ca", ca);
   * <p>
   * // Creating a TrustManager that trusts the CAs in our KeyStore.
   * String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
   * TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
   * tmf.init(keyStore);
   * <p>
   * // Creating an SSLSocketFactory that uses our TrustManager
   * SSLContext sslContext = SSLContext.getInstance("TLS");
   * sslContext.init(null, tmf.getTrustManagers(), null);
   * <p>
   * return sslContext;
   * }
   */

  public static LocationService getLocationService() {
    return getRetrofit(buildUrl(LocationService.SERVICE_NAME)).create(LocationService.class);
  }

  public static SurveyService getSurveyService() {
    return getRetrofit(buildUrl(SurveyService.SERVICE_NAME)).create(SurveyService.class);
  }

  public static CommonService getCommonService() {
    return getRetrofit(buildUrl("")).create(CommonService.class);
  }
}
