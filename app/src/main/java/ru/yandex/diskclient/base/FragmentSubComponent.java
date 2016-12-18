package ru.yandex.diskclient.base;

import dagger.Subcomponent;
import ru.yandex.diskclient.activity.directorylist.DirectoryListFragment;
import ru.yandex.diskclient.activity.login.LoginFragment;
import ru.yandex.diskclient.activity.profile.ProfileFragment;
import ru.yandex.diskclient.annotation.PerFragment;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentSubComponent {
	void inject(LoginFragment fragment);
	void inject(DirectoryListFragment fragment);
	void inject(ProfileFragment fragment);
}
