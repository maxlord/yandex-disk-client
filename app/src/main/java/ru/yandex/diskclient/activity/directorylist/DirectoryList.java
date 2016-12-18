package ru.yandex.diskclient.activity.directorylist;

import android.app.Fragment;

import ru.yandex.diskclient.activity.base.BaseActivity;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class  DirectoryList extends BaseActivity {
	@Override
	protected Fragment loadFragment() {
		return DirectoryListFragment.newInstance();
	}

	@Override
	protected void onResume() {
		super.onResume();

		bus.register(this);
	}

	@Override
	protected void onPause() {
		bus.unregister(this);

		super.onPause();
	}

	@Override
	public void onBackPressed() {
		bus.post(new BackActionEvent());
	}
}
