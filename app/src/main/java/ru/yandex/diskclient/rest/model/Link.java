package ru.yandex.diskclient.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 19.12.16
 */
public class Link {

	/**
	 * href : https://downloader.dst.yandex.ru/disk/...
	 * method : GET
	 * templated : false
	 */

	@SerializedName("href")
	public String href;
	@SerializedName("method")
	public String method;
	@SerializedName("templated")
	public boolean templated;

	@Override
	public String toString() {
		return "Link{" +
				"href='" + href + '\'' +
				", method='" + method + '\'' +
				", templated=" + templated +
				'}';
	}
}
