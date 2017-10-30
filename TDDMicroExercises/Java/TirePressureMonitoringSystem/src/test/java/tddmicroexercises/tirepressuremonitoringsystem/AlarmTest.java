package tddmicroexercises.tirepressuremonitoringsystem;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AlarmTest {

    @Test
    public void shouldReturnTrueIfAlarmIsOn() throws Exception {
        //setup
        Alarm alarm = new Alarm();

        Field alarmOnField = Alarm.class.getDeclaredField("alarmOn");
        alarmOnField.setAccessible(true);
        alarmOnField.setBoolean(alarm, true);

        //exercise
        boolean result = alarm.isAlarmOn();

        //assertion
        assertTrue("isAlarmOn() should return true", result);
    }

    @Test
    public void shouldReturnFalseIfAlarmIsOn() throws Exception {
        //setup
        Alarm alarm = new Alarm();

        Field alarmOnField = Alarm.class.getDeclaredField("alarmOn");
        alarmOnField.setAccessible(true);
        alarmOnField.setBoolean(alarm, false);

        //exercise
        boolean result = alarm.isAlarmOn();

        //assertion
        assertFalse("isAlarmOn() should return false", result);
    }

    @Test
    public void checkShouldTurnAlarmOnIfPressureIsTooHigh() throws Exception {
        //setup
        Alarm alarm = new Alarm();
        Alarm spyAlarm = spy(alarm);

        //exercise
        doReturn(122.0).when(spyAlarm).getPsiPressureValue();
        spyAlarm.check();

        //assertion
        verify(spyAlarm, times(1)).changeAlarmState(true);
    }

    @Test
    public void checkShouldTurnAlarmOnIfPressureIsTooLow() throws Exception {
        //setup
        Alarm alarm = new Alarm();
        Alarm spyAlarm = spy(alarm);

        //exercise
        doReturn(10.0).when(spyAlarm).getPsiPressureValue();
        spyAlarm.check();

        //assertion
        verify(spyAlarm, times(1)).changeAlarmState(true);
    }

    @Test
    public void checkShouldNotTurnAlarmOnIfPressureIsInsideExpectedRange() throws Exception {
        //setup
        Alarm alarm = new Alarm();
        Alarm spyAlarm = spy(alarm);

        //exercise
        doReturn(18.5).when(spyAlarm).getPsiPressureValue();
        spyAlarm.check();

        //assertion
        verify(spyAlarm, never()).changeAlarmState(true);
    }

    @Test
    public void checkIfAlarmTriesToGetPressureValueFromSensor() throws Exception {
        //setup
        Alarm alarm = new Alarm();

        //access current sensor
        Field sensor = Alarm.class.getDeclaredField("sensor");
        sensor.setAccessible(true);
        Sensor spySensor = spy((Sensor) sensor.get(alarm));
        sensor.set(alarm, spySensor);

        //exercise
        doReturn(1.0).when(spySensor).popNextPressurePsiValue();
        alarm.getPsiPressureValue();

        //assertion
        verify(spySensor, times(1)).popNextPressurePsiValue();
    }
}