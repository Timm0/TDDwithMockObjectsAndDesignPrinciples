package tddmicroexercises.tirepressuremonitoringsystem;

public class Alarm
{
    private final double lowPressureThreshold = 17;
    private final double highPressureThreshold = 21;

    private Sensor sensor;
    private boolean alarmOn = false;

    public Alarm() {
        this.sensor = new Sensor();
    }

    public void check()
    {
        double psiPressureValue = getPsiPressureValue();

        if (psiPressureValue < lowPressureThreshold || highPressureThreshold < psiPressureValue)
        {
            changeAlarmState(true);
        }
    }

    protected void changeAlarmState(boolean newAlarmState) {
        this.alarmOn = newAlarmState;
    }

    protected double getPsiPressureValue() {
        return sensor.popNextPressurePsiValue();
    }

    public boolean isAlarmOn()
    {
        return alarmOn;
    }
}
