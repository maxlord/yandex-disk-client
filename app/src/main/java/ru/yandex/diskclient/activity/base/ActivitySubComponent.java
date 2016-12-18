package ru.yandex.diskclient.activity.base;

import dagger.Subcomponent;
import ru.yandex.diskclient.activity.splash.Splash;
import ru.yandex.diskclient.annotation.PerActivity;
import ru.yandex.diskclient.base.FragmentModule;
import ru.yandex.diskclient.base.FragmentSubComponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivitySubComponent {
	FragmentSubComponent plus(FragmentModule module);

	void inject(Splash activity);
	void inject(BaseActivity activity);
}
