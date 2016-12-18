package ru.yandex.diskclient.base;

import android.app.Fragment;

import dagger.Module;

@Module
public class FragmentModule {
	private final Fragment fragment;

	public FragmentModule(Fragment fragment) {
		this.fragment = fragment;
	}

//	@Provides
//	@PerFragment
//	public Validator provideValidator() {
//		return new Validator(fragment);
//	}
}
