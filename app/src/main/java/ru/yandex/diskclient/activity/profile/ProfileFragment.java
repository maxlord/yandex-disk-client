package ru.yandex.diskclient.activity.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.diskclient.BuildConfig;
import ru.yandex.diskclient.R;
import ru.yandex.diskclient.annotation.IOScheduler;
import ru.yandex.diskclient.annotation.UIScheduler;
import ru.yandex.diskclient.app.DiskApplication;
import ru.yandex.diskclient.base.BaseFragment;
import ru.yandex.diskclient.helper.FileHelper;
import ru.yandex.diskclient.rest.Api;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class ProfileFragment extends BaseFragment {

	@BindView(R.id.version)
	TextView version;
	@BindView(R.id.space)
	TextView space;
	@BindView(R.id.list)
	RecyclerView list;
	@BindView(R.id.progress)
	ProgressBar progress;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Inject
	DiskApplication application;

	@Inject
	@UIScheduler
	Scheduler ui;

	@Inject
	@IOScheduler
	Scheduler io;

	@Inject
	Api api;

	private Subscription subscription;
	private DiskFileAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
	}

	public static ProfileFragment newInstance() {
		Bundle args = new Bundle();

		ProfileFragment fragment = new ProfileFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_profile;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	@Override
	protected void initControls(View v) {
		super.initControls(v);

		toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
		toolbar.setNavigationOnClickListener(view -> activity.onBackPressed());
	}

	@Override
	protected void loadData() {
		toolbar.setTitle(activity.getTitle());

		version.setText(getString(R.string.activity_profile_api_version, BuildConfig.API_VERSION));
		progress.setVisibility(View.VISIBLE);

		adapter = new DiskFileAdapter(getActivity(), null);

		list.setHasFixedSize(true);
		list.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
		list.setAdapter(adapter);

		subscription = Observable.zip(api.getDiskInfo(getTokenString()), api.getLastUploadedFileList(getTokenString(), 10),
				(disk, lastUploadedResourcesList) -> {
					ProfileData pd = new ProfileData();
					pd.disk = disk;
					pd.lastUploadedFiles = lastUploadedResourcesList;

					return pd;
				})
				.subscribeOn(io)
				.observeOn(ui)
				.subscribe(new Subscriber<ProfileData>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(ProfileData profileData) {
						progress.setVisibility(View.GONE);

						if (profileData != null) {
							space.setText(Html.fromHtml(getString(R.string.activity_profile_space,
									FileHelper.readableFileSize(profileData.disk.usedSpace),
									FileHelper.readableFileSize(profileData.disk.totalSpace))));

							adapter.addAllItems(profileData.lastUploadedFiles.items);
							adapter.notifyDataSetChanged();
						}
					}
				});
	}

	@Override
	public void onDestroy() {
		if (subscription != null && !subscription.isUnsubscribed()) {
			subscription.unsubscribe();
		}

		super.onDestroy();
	}
}
