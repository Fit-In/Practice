package com.example.android.SimpleCalc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CalculatorTestByMock {

    @Mock
    Calculator mCalculator;

    @Test
    public void addTwoNumbers() {
        MockitoAnnotations.initMocks(this);
        when(mCalculator.add(1d,1d)).thenReturn(2d);
        assertTrue(mCalculator.add(1d,1d) == 2d);
    }
}
