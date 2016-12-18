package ru.yandex.diskclient.activity.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.diskclient.R;
import ru.yandex.diskclient.helper.FileHelper;
import ru.yandex.diskclient.rest.model.LastUploadedResourcesList;
import timber.log.Timber;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 18.12.16
 */
public class DiskFileAdapter extends RecyclerView.Adapter<DiskFileAdapter.ViewHolder> {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.getDefault());
	private static final SimpleDateFormat PARSE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

	public static class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.icon)
		ImageView icon;
		@BindView(R.id.title)
		TextView title;
		@BindView(R.id.size)
		TextView size;
		@BindView(R.id.date)
		TextView date;

		public ViewHolder(View view) {
			super(view);

			ButterKnife.bind(this, view);
		}
	}

	interface Callback {
		void onClick(LastUploadedResourcesList.ResourceItem item);
	}

	private final Context context;
	private final List<LastUploadedResourcesList.ResourceItem> items;
	private final Callback callback;

	public DiskFileAdapter(Context context, Callback callback) {
		super();

		this.context = context;
		this.callback = callback;
		this.items = new ArrayList<>();
	}

	public void clear() {
		this.items.clear();
	}

	public void addAllItems(List<LastUploadedResourcesList.ResourceItem> items) {
		this.items.addAll(items);
	}

	public LastUploadedResourcesList.ResourceItem getItem(int position) {
		if (items != null && position < items.size()) {
			return items.get(position);
		}

		return null;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(context).inflate(R.layout.list_item_file, parent, false);

		view.setOnClickListener(v -> {
			LastUploadedResourcesList.ResourceItem item = (LastUploadedResourcesList.ResourceItem) v.getTag();

			if (callback != null) {
				callback.onClick(item);
			}
		});

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		LastUploadedResourcesList.ResourceItem item = items.get(position);

		holder.itemView.setTag(item);

		Date createdDate = null;
		try {
			createdDate = PARSE_DATE_FORMAT.parse(item.created);
		} catch (ParseException e) {
			Timber.e(e, "Ошибка преобразования даты: %s", item.created);
		}
//		Picasso.with(context).load(item.preview).into(holder.icon);
		holder.title.setText(item.path);
		holder.date.setText("");
		if (createdDate != null) {
			holder.date.setText(DATE_FORMAT.format(createdDate));
		}
		holder.size.setText(FileHelper.readableFileSize(item.size));
		holder.icon.setImageResource(R.drawable.ic_file);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}
}
