package ru.yandex.diskclient.activity.login;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import ru.yandex.diskclient.BuildConfig;
import ru.yandex.diskclient.R;
import ru.yandex.diskclient.activity.directorylist.DirectoryList;
import ru.yandex.diskclient.base.BaseFragment;
import ru.yandex.diskclient.helper.ActivityHelper;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class LoginFragment extends BaseFragment {
	@BindView(R.id.browser)
	WebView browser;
	@BindView(R.id.progress)
	ProgressBar progress;

	public static LoginFragment newInstance() {
		Bundle args = new Bundle();

		LoginFragment fragment = new LoginFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_login;
	}

	@Override
	protected void inject() {
		getComponent().inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
	}

	@Override
	protected void initControls(View v) {
		super.initControls(v);
	}

	@Override
	protected void loadData() {
		browser.getSettings().setBuiltInZoomControls(true);
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setDomStorageEnabled(true);
		browser.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				progress.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				progress.setVisibility(View.GONE);

				if (url.startsWith(BuildConfig.API_CALLBACK_URL)) {
					String tokenInfo = "";
					int ind = url.indexOf('#');
					if (ind > 0 && ind < url.length() - 1) {
						tokenInfo = url.substring(ind + 1);
					}

					Timber.d("TokenInfo = " + tokenInfo);
					String token = "";
					String[] tokenParams = tokenInfo.split("&");
					if (tokenParams != null && tokenParams.length > 0) {
						for (int i = 0; i < tokenParams.length; i++) {
							String tokenParam = tokenParams[i];
							String[] tokenKeyValue = tokenParam.split("=");
							if (tokenKeyValue != null && tokenKeyValue.length == 2) {
								String paramKey = tokenKeyValue[0];
								String paramVal = tokenKeyValue[1];

								if ("access_token".equalsIgnoreCase(paramKey)) {
									token = paramVal;
									// Для простоты реализации, остальные параметры пока не учитываем
									break;
								}
							}
						}
					}

					if (!token.isEmpty()) {
						// Сохраняем токен в SharedPreferences
						prefs.edit().putString(BuildConfig.SP_TOKEN, token).apply();

						// Редиректим на экран со списком файлов
						ActivityHelper.startActivity(activity, DirectoryList.class, true);
					}
				}
			}
		});

//		browser.setWebChromeClient(new WebChromeClient() {
//
//		});

		String tokenUrl = BuildConfig.API_AUTHORIZE_URL + "?response_type=token&client_id=" + BuildConfig.API_APPLICATION_ID;

		browser.loadUrl(tokenUrl);
	}
}
