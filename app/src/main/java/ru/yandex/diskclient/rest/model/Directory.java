package ru.yandex.diskclient.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class Directory {
	/**
	 * public_key : HQsmHLoeyBlJf8Eu1jlmzuU+ZaLkjPkgcvmokRUCIo8=
	 * _embedded : {"sort":"","path":"disk:/foo","items":[{"path":"disk:/foo/bar","type":"dir","name":"bar","modified":"2014-04-22T10:32:49+04:00","created":"2014-04-22T10:32:49+04:00"},{"name":"photo.png","preview":"https://downloader.disk.yandex.ru/preview/...","created":"2014-04-21T14:57:13+04:00","modified":"2014-04-21T14:57:14+04:00","path":"disk:/foo/photo.png","md5":"4334dc6379c8f95ddf11b9508cfea271","type":"file","mime_type":"image/png","size":34567}],"limit":20,"offset":0}
	 * name : foo
	 * created : 2014-04-21T14:54:42+04:00
	 * custom_properties : {"foo":"1","bar":"2"}
	 * public_url : https://yadi.sk/d/2AEJCiNTZGiYX
	 * modified : 2014-04-22T10:32:49+04:00
	 * path : disk:/foo
	 * type : dir
	 */

	@SerializedName("public_key")
	public String publicKey;
	@SerializedName("_embedded")
	public Embedded embedded;
	@SerializedName("name")
	public String name;
	@SerializedName("created")
	public String created;
	@SerializedName("custom_properties")
	public String customProperties;
	@SerializedName("public_url")
	public String publicUrl;
	@SerializedName("modified")
	public String modified;
	@SerializedName("path")
	public String path;
	@SerializedName("type")
	public String type;

	public static class Embedded {
		/**
		 * sort :
		 * path : disk:/foo
		 * items : [{"path":"disk:/foo/bar","type":"dir","name":"bar","modified":"2014-04-22T10:32:49+04:00","created":"2014-04-22T10:32:49+04:00"},{"name":"photo.png","preview":"https://downloader.disk.yandex.ru/preview/...","created":"2014-04-21T14:57:13+04:00","modified":"2014-04-21T14:57:14+04:00","path":"disk:/foo/photo.png","md5":"4334dc6379c8f95ddf11b9508cfea271","type":"file","mime_type":"image/png","size":34567}]
		 * limit : 20
		 * offset : 0
		 */

		@SerializedName("sort")
		public String sort;
		@SerializedName("path")
		public String path;
		@SerializedName("limit")
		public int limit;
		@SerializedName("offset")
		public int offset;
		@SerializedName("items")
		public List<DirectoryItem> items;

		public static class DirectoryItem {
			/**
			 * path : disk:/foo/bar
			 * type : dir
			 * name : bar
			 * modified : 2014-04-22T10:32:49+04:00
			 * created : 2014-04-22T10:32:49+04:00
			 * preview : https://downloader.disk.yandex.ru/preview/...
			 * md5 : 4334dc6379c8f95ddf11b9508cfea271
			 * mime_type : image/png
			 * size : 34567
			 */

			@SerializedName("path")
			public String path;
			@SerializedName("type")
			public String type;
			@SerializedName("name")
			public String name;
			@SerializedName("modified")
			public String modified;
			@SerializedName("created")
			public String created;
			@SerializedName("preview")
			public String preview;
			@SerializedName("md5")
			public String md5;
			@SerializedName("mime_type")
			public String mimeType;
			@SerializedName("size")
			public int size;

			@Override
			public String toString() {
				return "DirectoryItem{" +
						"path='" + path + '\'' +
						", type='" + type + '\'' +
						", name='" + name + '\'' +
						", modified='" + modified + '\'' +
						", created='" + created + '\'' +
						", preview='" + preview + '\'' +
						", md5='" + md5 + '\'' +
						", mimeType='" + mimeType + '\'' +
						", size=" + size +
						'}';
			}
		}

		@Override
		public String toString() {
			return "Embedded{" +
					"sort='" + sort + '\'' +
					", path='" + path + '\'' +
					", limit=" + limit +
					", offset=" + offset +
					", items=" + items +
					'}';
		}
	}

	@Override
	public String toString() {
		return "Directory{" +
				"publicKey='" + publicKey + '\'' +
				", embedded=" + embedded +
				", name='" + name + '\'' +
				", created='" + created + '\'' +
				", customProperties='" + customProperties + '\'' +
				", publicUrl='" + publicUrl + '\'' +
				", modified='" + modified + '\'' +
				", path='" + path + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
