package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class SmartDoorLockTest {
    private final int MAX_ATTEMPT_EXPECTED = 3;
    private final int DEFAULT_PIN = 1313;
    private SmartDoorLock doorLockToTest;
    private final int GENERIC_PIN = 1111;
    @BeforeEach

    public void init(){
        doorLockToTest= new SimpleSmartDoorLock();
    }

    @Test
    public void checkIfInitiallyBlocked(){assertFalse(doorLockToTest.isBlocked());};

    @Test
    public void checkValueOfMaxAttempts(){assertEquals(MAX_ATTEMPT_EXPECTED,doorLockToTest.getMaxAttempts());}

    @Test
    public void checkIfInitiallyUnlocked() {
        assertFalse(doorLockToTest.isLocked());
    }

    @Test
    public void testCorrectUseOfSetPin(){assertDoesNotThrow(()->doorLockToTest.setPin(DEFAULT_PIN));}

    @Test
    public void testUnlockWhenDoorAlreadyUnlocked(){assertThrows(IllegalComponentStateException.class,()->doorLockToTest.unlock(GENERIC_PIN));}

    @Test
    public void testLockWhenPinIsNotSet(){assertThrows(IllegalComponentStateException.class,()->doorLockToTest.lock());}

    private void setPinWithDefaultValueAndLockDoor(){
        doorLockToTest.setPin(DEFAULT_PIN);
        doorLockToTest.lock();
    }

    @Test
    public void testLockWhenAlreadyLocked(){
        setPinWithDefaultValueAndLockDoor();
        assertThrows(IllegalComponentStateException.class,()->doorLockToTest.lock());
    }

    @Test
    public void testLock(){
        setPinWithDefaultValueAndLockDoor();
        assertTrue(doorLockToTest.isLocked());
    }

    @Test
    public void testSetPinWhileLocked(){
        setPinWithDefaultValueAndLockDoor();
        assertThrows(IllegalComponentStateException.class,()->doorLockToTest.setPin(DEFAULT_PIN));
    }

    @Test
    public void testPinGraterThanMaximumValue(){
        setPinWithDefaultValueAndLockDoor();
        assertThrows(IllegalArgumentException.class, () -> doorLockToTest.setPin(99991));}

    @Test
    public void testPinLesserThanMinimumValue(){
        setPinWithDefaultValueAndLockDoor();
        assertThrows(IllegalArgumentException.class, () -> doorLockToTest.setPin(-1));}

    private void prepareLockedDoorThanUnlock(final int pin){
        setPinWithDefaultValueAndLockDoor();
        doorLockToTest.unlock(pin);
    }

    @Test
    public void testUnlockWithCorrectPin(){
        prepareLockedDoorThanUnlock(DEFAULT_PIN);
        assertEquals(0,doorLockToTest.getFailedAttempts());
        assertFalse(doorLockToTest.isLocked());
        assertFalse(doorLockToTest.isBlocked());
    }

    @Test
    public void testUnlockWithIncorrectPin(){
        prepareLockedDoorThanUnlock(GENERIC_PIN);
        assertEquals(1,doorLockToTest.getFailedAttempts());
        assertTrue(doorLockToTest.isLocked());
        assertFalse(doorLockToTest.isBlocked());

    }

    @Test
    public void testIfDoorBecomesBlocked(){
        setPinWithDefaultValueAndLockDoor();
        for(int counterAttempts=0; counterAttempts<MAX_ATTEMPT_EXPECTED;counterAttempts++){
            doorLockToTest.unlock(GENERIC_PIN);
        }
        assertEquals(MAX_ATTEMPT_EXPECTED,doorLockToTest.getFailedAttempts());
        assertTrue(doorLockToTest.isLocked());
        assertTrue(doorLockToTest.isBlocked());
    }
}
