package ru.yandex.diskclient.app;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.diskclient.BuildConfig;
import ru.yandex.diskclient.annotation.IOScheduler;
import ru.yandex.diskclient.annotation.UIScheduler;
import ru.yandex.diskclient.rest.Api;
import ru.yandex.diskclient.util.AsyncBus;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Модуль приложения
 *
 * @author Lord (Kuleshov M.V.)
 * @since 01.04.2015
 */
@Module
public class AppModule {
	protected final DiskApplication application;

	public AppModule(DiskApplication application) {
		this.application = application;

		Timber.plant(new Timber.DebugTree());
	}

	@Provides
	@Singleton
	DiskApplication provideApplication() {
		return application;
	}

	@Provides
	@Singleton
	public Bus provideBus() {
		return new AsyncBus(ThreadEnforcer.ANY);
	}

	@Provides
	@UIScheduler
	@Singleton
	public Scheduler provideUiScheduler() {
		return AndroidSchedulers.mainThread();
	}

	@Provides
	@IOScheduler
	@Singleton
	public Scheduler provideIoScheduler() {
		return Schedulers.io();
	}

	@Provides
	@Singleton
	public Gson providerGson() {
		return new GsonBuilder()
//				.registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.create();
	}

	@Provides
	@Singleton
	public Api provideApi(Gson gson) {
		Retrofit.Builder b = new Retrofit.Builder();

		OkHttpClient.Builder client = new OkHttpClient.Builder();
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		if (BuildConfig.DEBUG) {
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		} else {
			interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
		}
		client.interceptors().add(interceptor);

		b.client(client.build());
		b.addConverterFactory(GsonConverterFactory.create(gson));
		b.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

		Retrofit retrofit = b.baseUrl(BuildConfig.API_URL).build();

		return retrofit.create(Api.class);
	}
}
