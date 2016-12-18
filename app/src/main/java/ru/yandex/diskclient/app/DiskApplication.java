package ru.yandex.diskclient.app;

import android.app.Application;
import android.os.Build;

import ru.yandex.diskclient.BuildConfig;
import ru.yandex.diskclient.R;


/**
 * Главный класс приложения
 *
 * @author Lord (Kuleshov M.V.)
 * @since 26.08.15
 */
public class DiskApplication extends Application {
	private AppComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		applicationComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public AppComponent component() {
		return applicationComponent;
	}

	/**
	 * Получает название приложения для АПИ
	 *
	 * @return String
	 */
	public String getApplicationName() {
		return getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME + " on Android " + Build.VERSION.RELEASE;
	}
}
