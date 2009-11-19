package ar.noxit.security.mocks;

public class MockTestService implements ITestService {

    private int timesInvoked = 0;

    public int getTimesInvoked() {
        return this.timesInvoked;
    }

    @Override
    public void doService() {
        this.timesInvoked = this.timesInvoked + 1;
    }
}
