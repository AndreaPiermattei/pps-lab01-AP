package tdd;

import java.awt.*;

public class SimpleSmartDoorLock implements SmartDoorLock{

    private boolean locked = false;
    private final int MAX_ATTEMPTS = 3;
    private final int MAX_LENGTH_PIN = 4;
    private int failedAttempts = 0;
    private int pin = -1;

    @Override
    public void setPin(int pin) throws IllegalComponentStateException,IllegalArgumentException {
        int lengthOfPin = Integer.toString(pin).length();
        if(lengthOfPin!=MAX_LENGTH_PIN){throw new IllegalArgumentException("invalid pin");}
        if(locked || (failedAttempts == MAX_ATTEMPTS)){throw new IllegalComponentStateException("door is not open! cannot set pin");}
        this.pin=pin;
    }

    @Override
    public void unlock(int pin) throws IllegalComponentStateException{
        if(!this.locked){throw new IllegalComponentStateException("door already unlocked");}
        if(!(failedAttempts == MAX_ATTEMPTS)){
            if(this.failedAttempts < MAX_ATTEMPTS){
                if(this.pin == pin){
                    this.locked = false;
                }else{
                    this.failedAttempts = this.failedAttempts + 1;
                }
            }
        }

    }

    @Override
    public void lock() throws IllegalComponentStateException{
        if(this.locked){throw new IllegalComponentStateException("already locked");}
        if(this.pin == -1){throw new IllegalComponentStateException("pin is not set");}
        this.locked = true;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public boolean isBlocked() {
        return this.failedAttempts == MAX_ATTEMPTS;
    }

    @Override
    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    @Override
    public int getFailedAttempts() {
        return this.failedAttempts;
    }

    @Override
    public void reset() {
        this.locked = false;
        this.failedAttempts = 0;
        this.pin = -1;
    }
}
