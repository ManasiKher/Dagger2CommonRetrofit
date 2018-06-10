package com.manasi.dagger2_plus_common_retrofit.features.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.custom.AlertDialogButtonsClickListener;
import com.manasi.dagger2_plus_common_retrofit.features.base.BaseActivity;
import com.manasi.dagger2_plus_common_retrofit.features.dashboard.model.AppointmentsModel;
import com.manasi.dagger2_plus_common_retrofit.features.dashboard.model.DashboardModel;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginActivity;
import com.manasi.dagger2_plus_common_retrofit.utils.CommonUtils;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;
import com.manasi.dagger2_plus_common_retrofit.utils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardNavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AlertDialogButtonsClickListener, DashboardContract.View {

    @BindView(R.id.tv_toolbar_reset)
    TextView tvToolbarReset;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_submit)
    TextView tvToolbarSubmit;
    @BindView(R.id.iv_toolbar_notification)
    ImageView ivToolbarNotification;
    @BindView(R.id.tv_toolbar_notification_count)
    TextView tvToolbarNotificationCount;
    @BindView(R.id.cl_notifications)
    ConstraintLayout clNotifications;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.inc_toolbar)
    View incToolbar;
    @BindView(R.id.rv_appointments)
    RecyclerView rvAppointments;

    List<DashboardModel> dashboardModelList = new ArrayList<>();

    long nutritionistNumber = 1234567890;
    @BindView(R.id.nav_call)
    TextView navCall;
    @BindView(R.id.nav_email)
    TextView navEmail;
    @BindView(R.id.nav_logout)
    TextView navLogout;

    private static final String TAG = "DashboardNavigationActi";

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    DashboardPresenter<DashboardContract.View> dashboardPresenter;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mail_id)
    TextView tvMailId;
    @BindView(R.id.tv_mobile_number)
    TextView tvMobileNumber;
    @BindView(R.id.tv_initial_of_name)
    TextView tvInitialOfName;
    @BindView(R.id.tv_interactions_count)
    TextView tvInteractionsCount;
    @BindView(R.id.tv_month_count)
    TextView tvMonthCount;
    @BindView(R.id.tv_no_data_found)
    TextView tvNoDataFound;
    @BindView(R.id.ll_months)
    LinearLayout llMonths;
    @BindView(R.id.ll_interactions)
    LinearLayout llInteractions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_navigation);
        ButterKnife.bind(this);
        llInteractions.bringToFront();
        llMonths.bringToFront();
        setUp();
        getActivityComponent().inject(this);
        dashboardPresenter.onAttach(this);
        Log.d(TAG, "onCreate: SPV_IS_WELCOME_DONE " + SharedPrefUtils.getBoolean(this, Constants.SPK_LOGIN, Constants.SPV_IS_WELCOME_DONE));


        dashboardPresenter.getDashboardAPI();


        //dashboard list main...!!!
        rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        //prepareTimelineData();
        //rvAppointments.setAdapter(new DashboardAdapter_old(this, true, dashboardModelList));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                aptMonthList.clear();
                dashboardPresenter.getDashboardAPI();

            }
        });

        rvAppointments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        addClickEffectToViews();


    }

    private void addClickEffectToViews() {
        //tvN.setBackground(RippleDrawableHelper.createRippleDrawable(holder.tvSelect, context.getResources().getColor(R.color.grey_ripple)));

    }


    private void prepareTimelineData() {
        String[] monthArr = new String[]{"Decemeber 2017", "January 2018", "February 2018", "March 2018", "April 2018"};
        String[] dateMonthArr = new String[]{"Dec", "Jan", "Feb", "Mar", "Apr"};
        String[] dayMonthArr = new String[]{"Thursday", "Friday", "Tuesday", "Wednesday", "Monday"};
        int weekNumber = 0;

        for (int i = 0; i < 5; i++) {
            DashboardModel dashboardModel = new DashboardModel();
            dashboardModel.setMonthName(monthArr[i]);
            List<AppointmentsModel> appointmentsModelList = new ArrayList<>();
            for (int j = 0; j <= 3; j++) {
                AppointmentsModel appointmentsModel = new AppointmentsModel();
                appointmentsModel.setCallType(Constants.APPOINTMENT_TYPE_ON_CALL);
                String meetingDate = new Random().nextInt(31) + " " + dateMonthArr[i] + ", " + dayMonthArr[i];
                appointmentsModel.setMeetingDate(meetingDate);
                appointmentsModel.setMeetingTime("10:00 am");
                appointmentsModel.setWeekNumber(++weekNumber);
                int random = new Random().nextInt(4) + 1;
                appointmentsModel.setSet((i + 1) % random == 0);
                appointmentsModelList.add(appointmentsModel);

                if (i == 0) {
                    appointmentsModel.setCompleted(true);
                }

                if (weekNumber == 6) {
                    appointmentsModel.setUpcoming(true);
                }
            }
            dashboardModel.setAppointmentsModelList(appointmentsModelList);
            dashboardModelList.add(dashboardModel);
        }


    }


    @Override
    protected void setUp() {

        Toolbar toolbar = incToolbar.findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //remove default toolbar title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvToolbarTitle.setText(getString(R.string.app_name));
        tvToolbarReset.setVisibility(View.GONE);
        tvToolbarSubmit.setVisibility(View.GONE);
        clNotifications.setVisibility(View.VISIBLE);
        ivToolbarNotification.setVisibility(View.VISIBLE);
        tvToolbarNotificationCount.setText(2 + "");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mSwipeRefreshLayout.setEnabled(true);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSwipeRefreshLayout.setEnabled(false);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navView.setNavigationItemSelectedListener(this);

    }

    private boolean exit = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (exit) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, this.getString(R.string.info_double_tap_to_exit), Toast.LENGTH_SHORT).show();
                exit = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3000);
            }


        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_call) {
            // Handle the camera action
            CommonUtils.openPhoneDialer(this, nutritionistNumber + "");
        } else if (id == R.id.nav_email) {
            try {
                openEmailIntent(2);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_logout) {
            CommonUtils.showCommoneAlertDialog(this, Constants.DIALOG_TYPE_LOGOUT,
                    "", getString(R.string.dialog_msg_logout),
                    getString(R.string.dialog_cancel), getString(R.string.nav_logout));

        }

        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

   /* private void openPhoneDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + nutritionistNumber));
        startActivity(intent);
    }*/

    private void openEmailIntent(int method) throws Exception {
        String email = SharedPrefUtils.getString(this, Constants.SPK_LOGIN, Constants.SPV_NUTR_EMAIL); /* Your email address here */
        String subject = "";/* Your subject here */
        String body = "";/* Your body here */
        String chooserTitle = "";/* Your chooser title here */

        if (method == 1) {
            //1. Custom Uri:
            Uri uri = Uri.parse("mailto:" + email)
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .appendQueryParameter("body", body)
                    .build();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, chooserTitle));
        }

        if (method == 2) {
            //2. Using Intent extras:
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
            startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
        }

        if (method == 3) {
            //3. Support Library ShareCompat:
            ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .addEmailTo(email)
                    .setSubject(subject)
                    .setText(body)
                    //.setHtmlText(body) //If you are using HTML in your body text
                    .setChooserTitle(chooserTitle)
                    .startChooser();
        }
    }


    @OnClick(R.id.cl_notifications)
    public void onViewClicked() {
    }


    @Override
    public void onAlertDialogButtonClick(String type, boolean isPositiveButtonClicked) {
        if (isPositiveButtonClicked) {
            if (type.equalsIgnoreCase(Constants.DIALOG_TYPE_LOGOUT)) {
                dashboardPresenter.logoutUserAPI();
                //logoutUser();
            }
        }

    }

    @OnClick({R.id.nav_call, R.id.nav_email, R.id.nav_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_call:
                CommonUtils.openPhoneDialer(this, nutritionistNumber + "");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_email:
                try {
                    openEmailIntent(2);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.nav_logout:
                CommonUtils.showCommoneAlertDialog(this, Constants.DIALOG_TYPE_LOGOUT,
                        "", getString(R.string.dialog_msg_logout),
                        getString(R.string.dialog_cancel), getString(R.string.nav_logout));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    public void doScreenTransition(String apiName) {

    }

    @Override
    public void dismissRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void doLogoutSuccessOp() {

        //TODO - use android manifest launch modes here.. check if possible or not..!!!
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //clear all shared pref....
        SharedPrefUtils.clearAllPrefs(DashboardNavigationActivity.this, Constants.SPK_LOGIN);
        //these are values..... no need to clear it.. above fn will delete all the keys attached the SPK-LOGIN key
        //TODO - check if it works..!!!
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == 234) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: Appointment booked... call getPlan again...!!!!!");
                dashboardPresenter.getDashboardAPI();

            }
        }
    }
}
