package ar.noxit.security.mocks.services;

public class MockTestService implements TestService {

    private int timesInvoked = 0;

    public int getTimesInvoked() {
        return this.timesInvoked;
    }

    @Override
    public void doService() {
        this.timesInvoked = this.timesInvoked + 1;
    }
}
