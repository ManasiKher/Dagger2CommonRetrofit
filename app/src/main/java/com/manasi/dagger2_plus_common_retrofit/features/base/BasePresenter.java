package com.manasi.dagger2_plus_common_retrofit.features.base;



import com.manasi.dagger2_plus_common_retrofit.netowrk.RetrofitCommonClass;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;
    RetrofitCommonClass retrofitCommonClass = new RetrofitCommonClass();

    @Inject
    public BasePresenter() {
        //this.mDataManager = dataManager;
        //this.mSchedulerProvider = schedulerProvider;
        // this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;

    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    @Override
    public void setUserAsLoggedOut() {

    }

    public V getMvpView() {
        return mMvpView;
    }

    public RetrofitCommonClass getRCommonInstance() {
        return retrofitCommonClass;
    }

    public void callAPI(Call call) {
        try {
            retrofitCommonClass.initialiseCall(call);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
