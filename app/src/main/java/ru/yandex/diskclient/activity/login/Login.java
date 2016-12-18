package ru.yandex.diskclient.activity.login;

import android.app.Fragment;

import ru.yandex.diskclient.activity.base.BaseActivity;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class Login extends BaseActivity {
	@Override
	protected Fragment loadFragment() {
		return LoginFragment.newInstance();
	}
}
