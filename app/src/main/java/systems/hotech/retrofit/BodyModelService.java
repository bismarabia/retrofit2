package systems.hotech.retrofit;

import java.util.*;

import io.reactivex.Observable;
import systems.hotech.retrofit.retrofit2.http.*;

public interface BodyModelService {

    @GET("rafolder/view/list/get/id")
    Observable<BodyModel> getBodyResult(@Body List body, @HeaderMap Map<String, String> headerMap);
}