package volunteer.upay.com.upay.rest;

import android.os.Environment;
import android.text.TextUtils;


import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import volunteer.upay.com.upay.BuildConfig;

/**
 * @author amanbansal
 * @version 1.0
 * @since 2/1/17
 */
public class RetrofitAdapter {

    final static String BASE_URL = "http://www.upay.org.in/api/v1.0/";
    static int DEFAULT_READ_TIMEOUT = 90; //90 Seconds


    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;


    public static Retrofit getRetrofit(String baseUrl) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                Timber.e("request: %s", chain.request().toString());
//                Timber.e("protocol params: %s", chain.connection().protocol().toString());
//                Timber.e("other params: %s", chain.connection().socket().toString());
                return chain.proceed(chain.request());
            }
        };
        builder.addNetworkInterceptor(interceptor);
        builder.addNetworkInterceptor(new StethoInterceptor());
        builder.connectionPool(new okhttp3.ConnectionPool(CORE_POOL_SIZE, BuildConfig.KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS));
        builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(BuildConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.cache(new Cache(Environment.getDownloadCacheDirectory(), 1024 * 2 * 1024)); // 2MB Cache size
        builder.dispatcher(new Dispatcher(Executors.newFixedThreadPool(BuildConfig.MAX_IDLE_CONNECTIONS)));
        builder.cache(new Cache(Environment.getDataDirectory(), 1024 * 5));

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                //.callbackExecutor(Executors.newFixedThreadPool(CORE_POOL_SIZE))
                .baseUrl(!TextUtils.isEmpty(baseUrl) ? baseUrl : BASE_URL)
                //.baseUrl(baseURLKey==0? BASE_URL : BASE_COMMON_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }
}
