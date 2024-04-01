package com.example.fyp_application.Utils;

import javafx.scene.chart.ValueAxis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TicketVolumeDateAxis extends ValueAxis<Number> {
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void setRange(Object o, boolean b) {

    }

    @Override
    protected Object getRange() {
        return null;
    }

    @Override
    protected List<Number> calculateTickValues(double v, Object o) {
        return null;
    }

    @Override
    protected String getTickMarkLabel(Number value) {
        // Convert the numeric value to a date string
        return format.format(new Date(value.longValue()));
    }

    @Override
    protected List<Number> calculateMinorTickMarks() {
        return null;
    }
}
