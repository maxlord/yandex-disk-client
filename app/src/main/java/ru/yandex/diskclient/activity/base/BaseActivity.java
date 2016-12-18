package ru.yandex.diskclient.activity.base;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.squareup.otto.Bus;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ru.yandex.diskclient.R;
import ru.yandex.diskclient.annotation.ConfigPrefs;
import ru.yandex.diskclient.annotation.IOScheduler;
import ru.yandex.diskclient.annotation.UIScheduler;
import ru.yandex.diskclient.app.DiskApplication;
import ru.yandex.diskclient.rest.Api;
import rx.Scheduler;

public abstract class BaseActivity extends RxAppCompatActivity {
	public static final String FRAGMENT_TAG = "fragment_main";

	@Inject
	DiskApplication application;

	@Inject
	@ConfigPrefs
	SharedPreferences prefs;

	@Inject
	Api api;

	@Inject
	@UIScheduler
	Scheduler ui;

	@Inject
	@IOScheduler
	Scheduler io;

	@Inject
	public Bus bus;

	protected Fragment fragment;

	private ActivitySubComponent component;

	public ActivitySubComponent getComponent() {
		return component;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DiskApplication app = (DiskApplication) getApplication();
		this.component = app.component().plus(new ActivityModule(this));
		this.component.inject(this);

		setContentView(R.layout.activity_master);

		initControls();

		Bundle args = getIntent().getExtras();
		if (args != null) {
			readArguments(args);
		}

		if (savedInstanceState == null) {
			fragment = loadFragment();

			if (fragment != null) {
				getFragmentManager().beginTransaction().replace(R.id.fragment, fragment, FRAGMENT_TAG).commit();
			}
		} else {
			fragment = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
		}
	}

	protected void initControls() {
		ButterKnife.bind(this);
	}

	/**
	 * Инициализирует и возвращает фрагмент для отображения в активити.
	 * Классы наследники должны переопределять этот метод и загружать фрагмент.
	 *
	 * @return
	 */
	protected abstract Fragment loadFragment();

	/**
	 * Используя этот метод нужно инициализироваь аргументы переданнные в активити, через
	 * getIntent().getExtras();
	 */
	protected void readArguments(@NonNull Bundle args) {

	}
}
