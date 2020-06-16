package systems.hotech.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import systems.hotech.retrofit.gson.GsonConverterFactory;
import systems.hotech.retrofit.retrofit2.*;

import android.os.Bundle;
import android.util.Log;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8223/orest/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        BodyModelService bodyModelService = retrofit.create(BodyModelService.class);
        Observable<BodyModel> dds = bodyModelService.getBodyResult(Arrays.asList(1059700), new HashMap<String, String>() {{
            put("Authorization", "Bearer 9dd776a8-62b1-4222-ae46-6451223af7c8");
        }});
                dds.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BodyModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BodyModel aO) {
                        Log.i("TAG", "onNext: response --> " + aO.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}