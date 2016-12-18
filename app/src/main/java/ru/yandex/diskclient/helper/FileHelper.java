package ru.yandex.diskclient.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class FileHelper {
	public static String readableFileSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "б", "кб", "мб", "гб", "тб" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public static boolean saveFileContent(File f, byte[] fileContent) {
		if (f != null) {
			if (f.exists()) {
				f.delete();
			}

			try {
				FileOutputStream fos = new FileOutputStream(f.getPath());
				fos.write(fileContent);
				fos.close();

				return true;
			} catch (IOException e) {
				Timber.e(e, "Ошибка записи в файл");
			}
		}

		return false;
	}
}
