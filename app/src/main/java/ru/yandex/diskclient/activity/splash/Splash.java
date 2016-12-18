package ru.yandex.diskclient.activity.splash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import ru.yandex.diskclient.activity.base.ActivityModule;
import ru.yandex.diskclient.activity.base.ActivitySubComponent;
import ru.yandex.diskclient.activity.login.Login;
import ru.yandex.diskclient.annotation.ConfigPrefs;
import ru.yandex.diskclient.annotation.IOScheduler;
import ru.yandex.diskclient.annotation.UIScheduler;
import ru.yandex.diskclient.app.DiskApplication;
import ru.yandex.diskclient.helper.ActivityHelper;
import ru.yandex.diskclient.rest.Api;
import rx.Scheduler;

public class Splash extends RxAppCompatActivity {
	@Inject
	DiskApplication app;

	@Inject
	@UIScheduler
	Scheduler ui;

	@Inject
	@IOScheduler
	Scheduler io;

	@Inject
	Api api;

	@Inject
	@ConfigPrefs
	SharedPreferences prefs;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DiskApplication application = (DiskApplication) getApplication();
		ActivitySubComponent component = application.component().plus(new ActivityModule(this));
		component.inject(this);

		new IntentLauncher().start();
	}

	@Override
	public void onBackPressed() {

	}

	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			SystemClock.sleep(1000);

			startLogin();
		}

		private void startLogin() {
			// Запускаем главный экран
			ActivityHelper.startActivity(Splash.this, Login.class, true);
		}
	}
}
