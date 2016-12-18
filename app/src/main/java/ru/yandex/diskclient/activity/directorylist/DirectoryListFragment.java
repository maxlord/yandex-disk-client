package ru.yandex.diskclient.activity.directorylist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.diskclient.R;
import ru.yandex.diskclient.activity.profile.Profile;
import ru.yandex.diskclient.annotation.IOScheduler;
import ru.yandex.diskclient.annotation.UIScheduler;
import ru.yandex.diskclient.app.DiskApplication;
import ru.yandex.diskclient.base.BaseFragment;
import ru.yandex.diskclient.helper.ActivityHelper;
import ru.yandex.diskclient.rest.Api;
import ru.yandex.diskclient.rest.model.Directory;
import ru.yandex.diskclient.rest.model.Link;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class DirectoryListFragment extends BaseFragment implements Observer<Directory> {

	@BindView(R.id.list)
	RecyclerView list;
	@BindView(R.id.empty)
	TextView empty;
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

	@Inject
	Bus bus;

	private DiskFileAdapter adapter;
	private Observable<Directory> observable;
	private Subscription subscription;
	private String currentPath;

	public static DirectoryListFragment newInstance() {
		Bundle args = new Bundle();

		DirectoryListFragment fragment = new DirectoryListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_directory_list;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	@Override
	public void onPause() {
		bus.unregister(this);

		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

		bus.register(this);
	}

	@Override
	protected void initControls(View v) {
		super.initControls(v);

		toolbar.inflateMenu(R.menu.menu_directory_list);
		toolbar.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.action_profile) {
				ActivityHelper.startActivity(activity, Profile.class, false);

				return true;
			}

			return false;
		});
	}

	@Override
	protected void loadData() {
		toolbar.setTitle(activity.getTitle());

        adapter = new DiskFileAdapter(getActivity(), item -> {
	        Timber.d("item = " + item);

	        if (!DiskFileAdapter.RESOURCE_TYPE_FILE.equalsIgnoreCase(item.type)) {
		        reloadData(item.path);
	        } else {
		        // Получаем ссылку на скачивание файла
		        api.getLink(getTokenString(), item.path)
				        .observeOn(ui)
				        .subscribeOn(io)
				        .subscribe(new Subscriber<Link>() {
					        @Override
					        public void onCompleted() {

					        }

					        @Override
					        public void onError(Throwable e) {
						        Timber.e(e, "Ошибка при получении ссылки на файл");

						        new AlertDialog.Builder(activity)
								        .setTitle(R.string.common_error)
								        .setMessage("Во время загрузки файла произошла ошибка.\nПопробуйте позднее.")
								        .setPositiveButton("OK", null)
								        .create()
								        .show();
					        }

					        @Override
					        public void onNext(Link link) {
						        if (link != null) {
							        try {
								        Uri uri = Uri.parse(link.href);
								        Intent intent = new Intent(Intent.ACTION_VIEW);
								        intent.setData(uri);
								        startActivity(intent);
							        } catch (Exception e) {
								        Timber.e(e, "Ошибка при открытии файла");

								        new AlertDialog.Builder(activity)
										        .setTitle(R.string.common_error)
										        .setMessage(e.getMessage())
										        .setPositiveButton("OK", null)
										        .create()
										        .show();
							        }
						        }
					        }
				        });
	        }
        });

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

		reloadData("/");
	}

	@Override
	public void onDestroy() {
		if (subscription != null && !subscription.isUnsubscribed()) {
			subscription.unsubscribe();
		}

		super.onDestroy();
	}

	private void reloadData(String relativePath) {
		currentPath = relativePath;

		progress.setVisibility(View.VISIBLE);
		list.setVisibility(View.GONE);
		empty.setVisibility(View.GONE);

		observable = api.getResourceList(getTokenString(), relativePath);

		// Отписываемся, если это повторный вызов
		if (subscription != null && !subscription.isUnsubscribed()) {
			subscription.unsubscribe();
		}

		subscription = observable
				.subscribeOn(io)
				.observeOn(ui)
				.subscribe(this);
	}

	@Override
	public void onCompleted() {

	}

	@Override
	public void onError(Throwable e) {
		Timber.e(e, "Ошибка при получении данных через АПИ");

		new AlertDialog.Builder(activity)
				.setTitle(R.string.common_error)
				.setMessage(R.string.common_api_error_text)
				.setPositiveButton("OK", null)
				.create()
				.show();
	}

	@Override
	public void onNext(Directory directory) {
		progress.setVisibility(View.GONE);

		if (directory != null && directory.embedded.items != null) {
			Timber.d("items: %s", directory.embedded.items.toString());

			adapter.clear();
			adapter.addAllItems(directory.embedded.items);
			adapter.notifyDataSetChanged();

			if (adapter.getItemCount() > 0) {
				list.setVisibility(View.VISIBLE);
			} else {
				empty.setVisibility(View.VISIBLE);
			}

			// настраиваем тулбар
			int firstSlash = currentPath.indexOf("/");
			if (firstSlash >= 0) {
				String relativePath = currentPath.substring(firstSlash + 1);

				if (!relativePath.isEmpty()) {
					// Значит полагаем, что мы не в корне
					toolbar.setTitle(directory.name);
					toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
					toolbar.setNavigationOnClickListener(view -> {
						performUpDirectory(relativePath);
					});
				} else {
					toolbar.setTitle(activity.getTitle());
					toolbar.setNavigationIcon(null);
					toolbar.setNavigationOnClickListener(null);
				}
			}
		}
	}

	private void performUpDirectory(String relativePath) {
		int lastInd = relativePath.lastIndexOf('/');
		if (lastInd >= 0) {
			reloadData("/" + relativePath.substring(0, lastInd));
		} else {
			reloadData("/");
		}
	}

	@Subscribe
	public void onBackAction(BackActionEvent event) {
		int firstSlash = currentPath.indexOf("/");
		if (firstSlash >= 0) {
			String relativePath = currentPath.substring(firstSlash + 1);

			if (!relativePath.isEmpty()) {
				performUpDirectory(relativePath);
			} else {
				activity.finish();
			}
		} else {
			activity.finish();
		}
	}
}
