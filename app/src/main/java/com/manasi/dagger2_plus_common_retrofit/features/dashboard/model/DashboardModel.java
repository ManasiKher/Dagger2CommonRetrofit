package com.manasi.dagger2_plus_common_retrofit.features.dashboard.model;

import java.util.List;

public class DashboardModel {
    String monthName;
    String monthId;
    List<AppointmentsModel> appointmentsModelList;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthId() {
        return monthId;
    }

    public void setMonthId(String monthId) {
        this.monthId = monthId;
    }

    public List<AppointmentsModel> getAppointmentsModelList() {
        return appointmentsModelList;
    }

    public void setAppointmentsModelList(List<AppointmentsModel> appointmentsModelList) {
        this.appointmentsModelList = appointmentsModelList;
    }
}
