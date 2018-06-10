package com.manasi.dagger2_plus_common_retrofit.features.dashboard;

import com.manasi.dagger2_plus_common_retrofit.features.base.MvpPresenter;
import com.manasi.dagger2_plus_common_retrofit.features.base.MvpView;

public class DashboardContract {

    public interface View extends MvpView {
        void doScreenTransition(String apiName);

        void dismissRefreshLayout();

        void doLogoutSuccessOp();

    }

    interface Presenter<V extends DashboardContract.View> extends MvpPresenter<V> {
        void getDashboardAPI();

        void logoutUserAPI();

    }

}
