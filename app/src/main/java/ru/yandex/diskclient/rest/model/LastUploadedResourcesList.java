package ru.yandex.diskclient.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class LastUploadedResourcesList {

	/**
	 * items : [{"name":"photo2.png","preview":"https://downloader.disk.yandex.ru/preview/...","created":"2014-04-22T14:57:13+04:00","modified":"2014-04-22T14:57:14+04:00","path":"disk:/foo/photo2.png","md5":"53f4dc6379c8f95ddf11b9508cfea271","type":"file","mime_type":"image/png","size":54321},{"name":"photo1.png","preview":"https://downloader.disk.yandex.ru/preview/...","created":"2014-04-21T14:57:13+04:00","modified":"2014-04-21T14:57:14+04:00","path":"disk:/foo/photo1.png","md5":"4334dc6379c8f95ddf11b9508cfea271","type":"file","mime_type":"image/png","size":34567}]
	 * limit : 20
	 */

	@SerializedName("limit")
	public int limit;
	@SerializedName("items")
	public List<ResourceItem> items;

	public static class ResourceItem {
		/**
		 * name : photo2.png
		 * preview : https://downloader.disk.yandex.ru/preview/...
		 * created : 2014-04-22T14:57:13+04:00
		 * modified : 2014-04-22T14:57:14+04:00
		 * path : disk:/foo/photo2.png
		 * md5 : 53f4dc6379c8f95ddf11b9508cfea271
		 * type : file
		 * mime_type : image/png
		 * size : 54321
		 */

		@SerializedName("name")
		public String name;
		@SerializedName("preview")
		public String preview;
		@SerializedName("created")
		public String created;
		@SerializedName("modified")
		public String modified;
		@SerializedName("path")
		public String path;
		@SerializedName("md5")
		public String md5;
		@SerializedName("type")
		public String type;
		@SerializedName("mime_type")
		public String mimeType;
		@SerializedName("size")
		public int size;

		@Override
		public String toString() {
			return "ResourceItem{" +
					"name='" + name + '\'' +
					", preview='" + preview + '\'' +
					", created='" + created + '\'' +
					", modified='" + modified + '\'' +
					", path='" + path + '\'' +
					", md5='" + md5 + '\'' +
					", type='" + type + '\'' +
					", mimeType='" + mimeType + '\'' +
					", size=" + size +
					'}';
		}
	}

	@Override
	public String toString() {
		return "ResourcesList{" +
				"limit=" + limit +
				", items=" + items +
				'}';
	}
}
