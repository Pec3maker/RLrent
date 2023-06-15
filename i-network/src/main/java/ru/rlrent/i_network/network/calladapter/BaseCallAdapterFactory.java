package ru.rlrent.i_network.network.calladapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import ru.rlrent.i_network.network.error.NoInternetException;

/**
 * кроме конвертирования запроса в Observable, выполняет следующие функции:
 * Конвертирует IOException в NoConnectionException
 */
public abstract class BaseCallAdapterFactory extends CallAdapter.Factory {

    private RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private ResultCallAdapter resultCallAdapter;

    @SuppressWarnings("unchecked")
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?, ?> rxCallAdapter = rxJavaCallAdapterFactory.get(returnType, annotations, retrofit);
        resultCallAdapter = new ResultCallAdapter(rxCallAdapter, returnType);
        return resultCallAdapter;
    }

    /**
     * Метод обработки ошибки {@link HttpException}
     * Здесь определеяется поведение на различные коды ошибок. Например:
     * * c кодом 401 и если пользователь был авторизован - сбрасывает все данные пользователя и открывает экран авторизации
     * * c кодом 400 перезапрашивает токен и повторяет предыдущий запрос
     */
    protected <R> Observable<R> onHttpException(HttpException e, Call<R> call) {
        return onHttpException(e);
    }

    /**
     * @deprecated in version 0.2.1, replaced by {@link #onHttpException(HttpException, Call)}
     */
    @Deprecated
    protected <R> Observable<R> onHttpException(HttpException e) {
        return Observable.error(e);
    }

    /**
     * метод позволяет выполнить call(например, неудавшийся запрос)
     * необходим для предков класса BaseCallAdapterFactory
     */
    @SuppressWarnings("unchecked")
    protected Object adaptCallAdapter(Call<?> call) {
        return resultCallAdapter.adapt(call);
    }

    private final class ResultCallAdapter<R> implements CallAdapter<R, Object> {
        private final Type responseType;
        private final CallAdapter<R, Object> rxCallAdapter;

        protected ResultCallAdapter(CallAdapter<R, Object> rxCallAdapter, Type returnType) {
            Type observableType;
            if (returnType instanceof ParameterizedType) {
                observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
            } else {
                observableType = returnType;
            }
            this.rxCallAdapter = rxCallAdapter;
            this.responseType = observableType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object adapt(Call<R> call) {
            Object observable = rxCallAdapter.adapt(call);

            if (observable instanceof Flowable) {
                return ((Flowable<R>) observable).onErrorResumeNext((Throwable e) ->
                        handleNetworkError(e, call).toFlowable(BackpressureStrategy.LATEST));

            } else if (observable instanceof Maybe) {
                return ((Maybe<R>) observable).onErrorResumeNext((Throwable e) ->
                        handleNetworkError(e, call).singleElement());

            } else if (observable instanceof Single) {
                return ((Single<R>) observable).onErrorResumeNext((Throwable e) ->
                        handleNetworkError(e, call).singleOrError());

            } else if (observable instanceof Completable) {
                return ((Completable) observable).onErrorResumeNext((Throwable e) ->
                        handleNetworkError(e, call).ignoreElements());

            } else {
                return ((Observable<R>) observable).onErrorResumeNext((Throwable e) -> handleNetworkError(e, call));
            }
        }

        private Observable<R> handleNetworkError(Throwable e, Call<R> call) {
            if (e instanceof IOException) {
                return Observable.error(new NoInternetException(e));
            } else if (e instanceof HttpException) {
                return onHttpException((HttpException) e, call);
            } else {
                return Observable.error(e);
            }
        }
    }
}