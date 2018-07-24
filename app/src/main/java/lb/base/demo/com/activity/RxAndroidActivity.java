package lb.base.demo.com.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.base_tools.Lg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lb.base.demo.com.R;
import lb.base.demo.com.bean.TestBean;

/**
 * @author FLB
 * RxAndroid 学习记录
 */
public class RxAndroidActivity extends AppCompatActivity {

    @BindView(R.id.tv_test_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_text_01)
    void test1() {
        tvContent.setText(R.string.subscribe);
        final TestBean testBean = new TestBean("张三");
        Observable<TestBean> observable = Observable.create(new ObservableOnSubscribe<TestBean>() {
            @Override
            public void subscribe(ObservableEmitter<TestBean> emitter) {
                emitter.onNext(testBean);
            }
        });

        observable.subscribe(new Observer<TestBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                Lg.i("onSubscribe");
            }

            @Override
            public void onNext(TestBean testBean) {
                Lg.i("onNext:" + testBean);
            }

            @Override
            public void onError(Throwable e) {
                Lg.i("onError");
            }

            @Override
            public void onComplete() {
                Lg.i("onComplete");
            }
        });
    }

    private Disposable test2Subscribe;

    @OnClick(R.id.bt_text_02)
    void test2() {
        tvContent.setText(R.string.map);
        if (test2Subscribe != null) {
            test2Subscribe.dispose();
        } else {
            test2Subscribe = Observable.fromArray(new TestBean("张三"), new TestBean("李四"), new TestBean("王二麻子"))
                    .map(new Function<TestBean, String>() {
                        @Override
                        public String apply(TestBean o) {
                            String name = Thread.currentThread().getName();
                            Lg.i("map thread:" + name);
                            return o.getName();
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String o) {
                            Lg.i("O:" + o);
                        }
                    });
        }
    }

    private Disposable test3Subscribe;

    @OnClick(R.id.bt_text_03)
    void test3() {
        tvContent.setText(R.string.flatMap);
        if (test3Subscribe != null) {
            test3Subscribe.dispose();
        } else {
            test3Subscribe = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) {
                    emitter.onNext("123");
                    emitter.onNext("456");
                    emitter.onNext("789");
                    emitter.onComplete();
                    Lg.i("subscribe <-->" + Thread.currentThread().getName());
                }
            })
                    .concatMap(new Function<String, ObservableSource<String>>() {
                        /** flatMap 不保证执行顺序 */
                        @Override
                        public ObservableSource<String> apply(String o) {
                            Lg.i("o -->" + o);
                            String[] split = o.split("");
                            int delayTime = (int) (1 + Math.random() * 10);
                            return Observable.fromArray(split).delay(delayTime, TimeUnit.MILLISECONDS);
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) {
                            Lg.i("s:" + s);
                            Lg.i("accept:" + Thread.currentThread().getName());
                        }
                    });
        }
    }

    private Disposable test4Subscribe;

    @OnClick(R.id.bt_text_04)
    void test4() {
        tvContent.setText(R.string.zip);
        if (test4Subscribe != null) {
            test3Subscribe.dispose();
        } else {

            Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) {
                    emitter.onNext("a");
                    emitter.onNext("b");
                    emitter.onNext("c");
                    emitter.onNext("d");
                }
            });

            Observable<Integer> intObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                }
            });
            final Map<String, Integer> map = new HashMap<>(10);
            if (test4Subscribe != null) {
                test4Subscribe.dispose();
            } else {
                test4Subscribe = Observable.zip(stringObservable, intObservable, new BiFunction<String, Integer, String>() {
                    @Override
                    public String apply(String s, Integer integer) {
                        map.put(s, integer);
                        return s;
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Integer integer = map.get(s);
                        Lg.i("s:" + s + "  integer:" + integer);
                    }
                });
            }
        }
    }

    private Disposable subscribe;

    @OnClick(R.id.bt_text_05)
    void test5() {
        tvContent.setText(R.string.interval);
        if (subscribe != null) {
            subscribe.dispose();
        } else {
            subscribe = Observable.interval(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long value) {
                    Lg.i("aLong:" + value);
                }
            });
        }
    }
}
