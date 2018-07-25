package lb.base.demo.com.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.base_tools.Lg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
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
            test2Subscribe = null;
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
            test3Subscribe = null;
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
            test4Subscribe.dispose();
            test4Subscribe = null;
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
                test4Subscribe = null;
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

    private Disposable test5Subscribe;

    private Long time = 5L;

    @OnClick(R.id.bt_text_05)
    void test5() {
        tvContent.setText(R.string.interval);
        if (test5Subscribe != null) {
            test5Subscribe.dispose();
            test5Subscribe = null;
        } else {
            test5Subscribe = Observable.interval(1, TimeUnit.SECONDS).map(new Function<Long, Long>() {
                @Override
                public Long apply(Long aLong) {
                    Lg.i("apply:" + aLong);
                    return time - aLong;
                }
            }).take(time + 1).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) {
                    Lg.i("倒计时：" + aLong);
                }
            });
        }
    }

    Disposable test6Subscribe;

    @OnClick(R.id.bt_text_06)
    void test6() {
        tvContent.setText(R.string.repeat);
        if (test6Subscribe != null) {
            test6Subscribe.dispose();
            test6Subscribe = null;
        } else {
            test6Subscribe = Observable.fromArray("aa", "bb", "cc").repeat(4).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    Lg.i("s:" + s);
                }
            });
        }
    }

    Disposable test7Subscribe;

    @OnClick(R.id.bt_text_07)
    void test7() {
        tvContent.setText(R.string.range);
        if (test7Subscribe != null) {
            test7Subscribe.dispose();
            test7Subscribe = null;
        } else {
            test7Subscribe = Observable.range(2, 5).subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) {
                    Lg.i("accept:" + integer);
                }
            });
        }
    }

    @OnClick(R.id.bt_text_08)
    void test8() {
        tvContent.setText("数组转集合");
        Disposable subscribe = Observable.just(1, 2, 3, 4, 5).toList().subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) {
                Lg.i("integers:" + integers);
            }
        });
        subscribe.isDisposed();
    }

    @OnClick(R.id.bt_text_09)
    void test9() {
        tvContent.setText("延迟发送数据");
        Disposable subscribe = Observable.just(1, 2, 3).delay(10, TimeUnit.SECONDS).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                Lg.i("integer:" + integer);
            }
        });
        Lg.i("disposed:" + subscribe.isDisposed());
    }

    @OnClick(R.id.bt_text_10)
    void text10() {
        Random random = new Random();
        int type = random.nextInt(2);
        tvContent.setText("背压处理");
        Lg.i("type:" + type);
        if (type == 1) {
            test10Demo1();
        } else {
            test10Demo2();
        }
    }

    void test10Demo2() {
        Disposable text10Demo2Subscribe = Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()     //只处理128K以内的命令，多于的舍弃
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Thread.sleep(1000);
                        Lg.i("aLong:" + aLong);
                    }
                });
        Lg.i("text10Demo2Subscribe:" + text10Demo2Subscribe.isDisposed());
    }

    void test10Demo1() {
        //        处理不过来的指令会放到内存中，指令越多，内在占用越大
        Disposable text10Demo1Subscribe = Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Thread.sleep(1000);
                        Lg.i("aLong:" + aLong + "  -->" + Thread.currentThread().getName());
                    }
                });
        Lg.i("text10Demo1Subscribe:" + text10Demo1Subscribe.isDisposed());
    }
}
