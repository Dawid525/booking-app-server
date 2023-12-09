package com.dpap.bookingapp;

import com.dpap.bookingapp.availability.timeslot.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class TimeSlotTest {

    private final LocalDateTime _2022_05_24_10_00 = LocalDateTime.of(2022, 5, 24, 10, 0);
    private final LocalDateTime _2022_05_25_12_00 = LocalDateTime.of(2022, 5, 25, 12, 0);
    private final LocalDateTime _2022_05_25_13_00 = LocalDateTime.of(2022, 5, 25, 13, 0);
    private final LocalDateTime _2022_05_25_14_00 = LocalDateTime.of(2022, 5, 25, 14, 0);
    private final LocalDateTime _2022_05_26_12_00 = LocalDateTime.of(2022, 5, 26, 12, 0);
    private final LocalDateTime _2022_05_27_12_00 = LocalDateTime.of(2022, 5, 27, 12, 0);
    private final LocalDateTime _2022_05_28_12_00 = LocalDateTime.of(2022, 5, 26, 12, 0);

    @Test
    public void shouldReturnTwoTimeSlots() {
        var result = new TimeSlot(_2022_05_25_12_00, _2022_05_25_12_00.plusDays(2))
                .allTimeSlotsWithDurationBetweenEach(22);
        assertEquals(2, result.size());
        assertEquals(new TimeSlot(_2022_05_25_12_00, _2022_05_25_12_00.plusHours(22)), result.get(0));
        assertEquals(new TimeSlot(_2022_05_25_12_00.plusDays(1), _2022_05_25_12_00.plusDays(1).plusHours(22)), result.get(1));
    }

    @Test
    public void shouldThrowExceptionWhenStartIsAfterFinish() {
        assertThrows(RuntimeException.class, () -> new TimeSlot(LocalDateTime.now().plusHours(2), LocalDateTime.now()));
    }

    @Test
    public void shouldReturnTrueWhenPeriodIntersects() {
        var result = new TimeSlot(_2022_05_25_12_00, _2022_05_26_12_00)
                .intersect(new TimeSlot(_2022_05_24_10_00, _2022_05_25_14_00));
        var result2 = new TimeSlot(_2022_05_25_12_00, _2022_05_26_12_00)
                .intersect(new TimeSlot(_2022_05_25_13_00, _2022_05_25_14_00));
        var result3 = new TimeSlot(_2022_05_25_12_00, _2022_05_26_12_00)
                .intersect(new TimeSlot(_2022_05_25_14_00, _2022_05_28_12_00));
        var result4 = new TimeSlot(_2022_05_25_12_00, _2022_05_26_12_00)
                .intersect(new TimeSlot(_2022_05_25_12_00, _2022_05_26_12_00));
        assertTrue(result);
        assertTrue(result2);
        assertTrue(result3);
        assertTrue(result4);
    }

    @Test
    public void shouldReturnFalseWhenPeriodDoesNotIntersect() {
        var result = new TimeSlot(_2022_05_25_12_00, _2022_05_25_13_00)
                .intersect(new TimeSlot(_2022_05_25_14_00, _2022_05_26_12_00));
        assertFalse(result);
    }

    @Test
    public void shouldReturnOverlappedTimeSlot() {
        var result = new TimeSlot(_2022_05_25_12_00, _2022_05_26_12_00)
                .overlap(new TimeSlot(_2022_05_26_12_00, _2022_05_27_12_00));
        assertEquals(new TimeSlot(_2022_05_25_12_00, _2022_05_27_12_00), result);
    }
}
