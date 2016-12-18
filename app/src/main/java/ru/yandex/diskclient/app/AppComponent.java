package ru.yandex.diskclient.app;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.diskclient.activity.base.ActivityModule;
import ru.yandex.diskclient.activity.base.ActivitySubComponent;
import ru.yandex.diskclient.rest.Api;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
	void inject(DiskApplication application);

	Bus provideBus();
	Api provideApi();

	ActivitySubComponent plus(ActivityModule module);
//	ServiceSubComponent plus(ServiceModule module);
}
