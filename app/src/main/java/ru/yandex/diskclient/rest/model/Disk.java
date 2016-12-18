package ru.yandex.diskclient.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class Disk {

	/**
	 * trash_size : 4631577437
	 * total_space : 319975063552
	 * used_space : 26157681270
	 * system_folders : {"applications":"disk:/Приложения","downloads":"disk:/Загрузки/"}
	 */

	@SerializedName("trash_size")
	public long trashSize;
	@SerializedName("total_space")
	public long totalSpace;
	@SerializedName("used_space")
	public long usedSpace;
	@SerializedName("system_folders")
	public SystemFolders systemFolders;

	public static class SystemFolders {
		/**
		 * applications : disk:/Приложения
		 * downloads : disk:/Загрузки/
		 */

		@SerializedName("applications")
		public String applications;
		@SerializedName("downloads")
		public String downloads;

		@Override
		public String toString() {
			return "SystemFolders{" +
					"applications='" + applications + '\'' +
					", downloads='" + downloads + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "Disk{" +
				"trashSize=" + trashSize +
				", totalSpace=" + totalSpace +
				", usedSpace=" + usedSpace +
				", systemFolders=" + systemFolders +
				'}';
	}
}
