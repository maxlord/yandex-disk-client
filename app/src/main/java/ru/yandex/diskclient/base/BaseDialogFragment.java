package ru.yandex.diskclient.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.RxDialogFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.diskclient.activity.base.BaseActivity;
import ru.yandex.diskclient.annotation.ConfigPrefs;

/**
 * Базовый фрагмент
 *
 * @author Lord (Kuleshov M.V.)
 * @since 01.04.2015
 */
public abstract class BaseDialogFragment extends RxDialogFragment {
	@Inject
	@ConfigPrefs
	protected SharedPreferences prefs;

	@Inject
	protected BaseActivity activity;

	protected Unbinder unbinder;

	private FragmentSubComponent component;

	public FragmentSubComponent getComponent() {
		return component;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getLayoutRes(), container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getActivity() instanceof BaseActivity) {
			BaseActivity activity = (BaseActivity) getActivity();
			this.component = activity.getComponent().plus(new FragmentModule(this));

			inject();
		}

		loadData();
	}

	/**
	 * @return
	 */
	protected abstract
	@LayoutRes
	int getLayoutRes();

	protected abstract void inject();

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		unbinder = ButterKnife.bind(this, v);
		initControls(v);
		onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		if (unbinder != null) {
			unbinder.unbind();
		}

		super.onDestroyView();
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {

	}

	protected void initControls(View v) {

	}

	protected void loadData() {

	}

	/**
	 * Получение настроек пользователя
	 *
	 * @return
	 */
	protected SharedPreferences getPrefs() {
		return prefs;
	}
}
