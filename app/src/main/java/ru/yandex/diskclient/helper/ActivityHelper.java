package ru.yandex.diskclient.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

/**
 * Хелпер для работы с Активити. Содержит набор методов для работы с актитиви.
 * 
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.2013
 * 
 */
public class ActivityHelper {
	/**
	 * Открывает активити
	 * 
	 * @param parentActivity
	 *            класс родительской активити
	 * @param cls
	 *            класс открываемой активити
	 * @param closeCurrent
	 *            нужно ли закрывать текущую активити
	 * @param extras
	 *            дополнительные параметры, передаваемые в открываемую актвити
	 * @throws NullPointerException
	 * @throws ActivityNotFoundException
	 */
	public static void startActivity(Activity parentActivity, Class<?> cls, boolean closeCurrent, Bundle extras)
			throws NullPointerException, ActivityNotFoundException {

		Intent intent = new Intent(parentActivity, cls);
		if (extras != null) {
			intent.putExtras(extras);
		}
		parentActivity.startActivity(intent);
		if (closeCurrent) {
			parentActivity.finish();
		}
	}

	/**
	 * Открывает новую Activity и сохраняет класс текущей активити
	 * 
	 * @param parentActivity
	 *            - текущая активити
	 * @param cls
	 *            - класс новой активити
	 * @param closeCurrent
	 *            - если true, то закрывает текущую
	 * @throws NullPointerException
	 *             если parentActivity или cls равны null
	 * @throws ActivityNotFoundException
	 *             если активити не найдена
	 */
	public static void startActivity(Activity parentActivity, Class<?> cls, boolean closeCurrent)
			throws NullPointerException, ActivityNotFoundException {
		startActivity(parentActivity, cls, closeCurrent, null);
	}

	/**
	 * Вызывает диалог выбора приложения для открытия документа
	 *
	 * @param context конекст
	 * @param fileName название файла
	 */
	public static void openDocument(Context context, String fileName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		File file = new File(fileName);
		String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
		String mimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		if (extension.equalsIgnoreCase("") || mimeType == null) {
			// if there is no extension or there is no definite mimeType, still try to open the file
			intent.setDataAndType(Uri.fromFile(file), "text/*");
		} else {
			intent.setDataAndType(Uri.fromFile(file), mimeType);
		}

		// custom message for the intent
		context.startActivity(Intent.createChooser(intent, "Выберите приложение:"));
	}
}
