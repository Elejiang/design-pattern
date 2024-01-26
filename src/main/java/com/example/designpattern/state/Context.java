package com.example.designpattern.state;

class Context {
    public final static OpeningState openingState = new OpeningState();
    public final static ClosingState closingState = new ClosingState();
    public final static RunningState runningState = new RunningState();
    public final static StoppingState stoppingState = new StoppingState();

    private LiftState liftState;

    public LiftState getLiftState() {
        return liftState;
    }

    public void setLiftState(LiftState liftState) {
        this.liftState = liftState;
        this.liftState.setContext(this);
    }

    public void open() {
        liftState.open();
    }
    public void close() {
        liftState.close();
    }
    public void run() {
        liftState.run();
    }
    public void stop() {
        liftState.stop();
    }
}
