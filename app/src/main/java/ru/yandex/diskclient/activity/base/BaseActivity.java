package ru.yandex.diskclient.activity.base;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

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

//	@Nullable
//	@BindView(R.id.toolbar)
//	Toolbar toolbar;

//	@Inject
//	DatabaseHelper databaseHelper;

	protected Fragment fragment;

//	protected Drawer drawer;

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

//		if (toolbar != null) {
//			setSupportActionBar(toolbar);
//		}
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

	public void initDrawer(Toolbar toolbar) {
//		ProfileDrawerItem pdi = null;
//		AccountHeader headerResult = null;
//		pdi = new ProfileDrawerItem()
//				.withIcon(R.drawable.ic_profile_icon)
//				.withName("Гусев Владимир Викторович");
//
//		headerResult = new AccountHeaderBuilder()
//				.withActivity(this)
//				.withSelectionListEnabled(false)
//				.withSelectionListEnabledForSingleProfile(false)
//				.withHeaderBackground(R.drawable.bg_header)
////				.withProfileImagesVisible(false)
////				.withCompactStyle(true)
//				.withAlternativeProfileHeaderSwitching(false)
//				.addProfiles(pdi)
//				.build();
//
//		DrawerBuilder builder = new DrawerBuilder()
//				.withActivity(this)
//				.withToolbar(toolbar)
//				.withActionBarDrawerToggle(true)
//				.withActionBarDrawerToggleAnimated(true);
//		if (headerResult != null) {
//			builder.withAccountHeader(headerResult);
//		}
//
//		BadgeStyle badgeStyle = new BadgeStyle()
//				.withTextColor(Color.WHITE)
//				.withColorRes(R.color.primary)
//				.withCornersDp(16);
//		badgeStyle.withPaddingLeftRightDp(12);
//
//		builder.addDrawerItems(
////			new PrimaryDrawerItem()
////					.withName(R.string.drawer_item_my_requests)
////					.withIcon(GoogleMaterial.Icon.gmd_local_post_office)
////					.withIdentifier(DRAWER_ITEM_APPEAL_LIST)
////					.withBadgeStyle(badgeStyle)
////					.withBadge("2"),
////			new DividerDrawerItem(),
////			new PrimaryDrawerItem()
////					.withName(R.string.drawer_item_reservation_list)
////					.withIcon(GoogleMaterial.Icon.gmd_time)
////					.withIdentifier(DRAWER_ITEM_RESERVATION)
////					.withBadgeStyle(badgeStyle)
////					.withSelectable(false),
////			new DividerDrawerItem(),
//			new PrimaryDrawerItem()
//					.withName(R.string.drawer_item_department_list)
//					.withIcon(GoogleMaterial.Icon.gmd_pin)
//					.withIdentifier(DRAWER_ITEM_DEPARTMENT_LIST)
//					.withBadgeStyle(badgeStyle)
////			new DividerDrawerItem(),
////			new PrimaryDrawerItem()
////					.withName(R.string.drawer_item_profile)
////					.withIcon(GoogleMaterial.Icon.gmd_account)
////					.withIdentifier(DRAWER_ITEM_PROFILE)
//		);
//
//		builder.addStickyDrawerItems(
////				new PrimaryDrawerItem()
////						.withName(R.string.drawer_item_help)
////						.withIcon(GoogleMaterial.Icon.gmd_help)
////						.withIdentifier(DRAWER_ITEM_HELP),
////				new PrimaryDrawerItem()
////						.withName(R.string.drawer_item_settings)
////						.withIcon(GoogleMaterial.Icon.gmd_settings)
////						.withIdentifier(DRAWER_ITEM_SETTINGS),
//				new PrimaryDrawerItem()
//						.withName(R.string.drawer_item_logout)
//						.withIcon(GoogleMaterial.Icon.gmd_power_off)
//						.withIdentifier(DRAWER_ITEM_LOGOUT)
//						.withBadgeStyle(badgeStyle)
//						.withSelectable(false)
//		);
//
//		builder.withOnDrawerItemClickListener((view, position, drawerItem) -> processItemClick(drawerItem.getIdentifier()));
//		builder.withSelectedItem(-1);
//
//		drawer = builder.build();
////		drawer.keyboardSupportEnabled(this, false);
	}

	private boolean processItemClick(Long identifier) {
		switch (identifier.intValue()) {
//			case DRAWER_ITEM_APPEAL_LIST:
//				ActivityHelper.startActivity(this, AppealList.class, true);
//				break;
//
//			case DRAWER_ITEM_RESERVATION:
//				Bundle b = new Bundle();
//				//b.putInt(GroupList.EXT_IN_DEPARTMENT_ID, departmentId);
//				// TODO временно до реализации реального сервиса
//				b.putInt(GroupList.EXT_IN_DEPARTMENT_ID, BuildConfig.DEBUG_DEPARTMENT_ID); // Много нас, оно одно
//
//				ActivityHelper.startActivity(this, GroupList.class, false, b);
//				break;
//
//			case DRAWER_ITEM_DEPARTMENT_LIST:
//				ActivityHelper.startActivity(this, DepartmentList.class, true);
//				break;
//
//			case DRAWER_ITEM_LOGOUT:
//				new MaterialDialog.Builder(this)
//						.content(R.string.activity_base_dialog_logout_content)
//						.positiveText(R.string.activity_base_dialog_logout_confirm_yes)
//						.negativeText(R.string.activity_base_dialog_logout_confirm_cancel)
//						.onPositive((materialDialog, dialogAction) -> {
//							finish();
//						})
//						.show();
//				break;
		}

		return false;
	}

//	public Drawer getDrawer() {
//		return drawer;
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// освобождаем ресурсы хелпера БД
//		OpenHelperManager.releaseHelper();
	}
}
