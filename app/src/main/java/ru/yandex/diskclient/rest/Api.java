package ru.yandex.diskclient.rest;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import ru.yandex.diskclient.rest.model.Directory;
import ru.yandex.diskclient.rest.model.Disk;
import ru.yandex.diskclient.rest.model.LastUploadedResourcesList;
import ru.yandex.diskclient.rest.model.Link;
import rx.Observable;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 19.09.16
 */
public interface Api {
    @GET("v1/disk/resources")
    Observable<Directory> getResourceList(@Header("Authorization") String token, @Query("path") String relativePath);

    @GET("v1/disk/resources/last-uploaded")
    Observable<LastUploadedResourcesList> getLastUploadedFileList(@Header("Authorization") String token, @Query("limit") int limit);

    @GET("v1/disk")
    Observable<Disk> getDiskInfo(@Header("Authorization") String token);

	@GET("v1/disk/resources/download")
	Observable<Link> getLink(@Header("Authorization") String token, @Query("path") String path);
}
