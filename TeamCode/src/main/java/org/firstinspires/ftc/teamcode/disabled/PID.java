package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

/*
This class implements a PID controller.
 */
public class PID {

    ElapsedTime time = new ElapsedTime();

    private double[] coefficient = {0, 0, 0};

    public double expected, actual;
    private double errorOld;
    private double integralError = 0;
    private double lastTime;
    private double sign = 1;

    public PID() {
        expected = 0;
        actual = 0;
        time.reset();
    }
    // Constructor
    public PID(double expected) {
        this.expected = expected;
        time.reset();
    }
    public PID(double expected, double[] coefficient) {
        this.expected = expected;
        setCoefficient(coefficient);
        time.reset();
    }
    public PID(double[] coefficient) {
        setCoefficient(coefficient);
    }
    // Set/get values
    public void setCoefficient(double[] coefficient) {
        this.coefficient = coefficient;
    }
    public double[] getCoefficient() {
        return coefficient;
    }
    public void setActual(double actual) {
        this.actual = actual;
    }

    // Calculate the error
    public double calcCorrection() {
        // Calculate P = error
        double error = (expected - actual) / actual;
        sign = Math.signum(error);
        // Calculate D = derivative of error = dE/dt
        double currentTime = time.milliseconds();
        double derivativeError = (error - errorOld) / (currentTime - lastTime);
        errorOld = error;
        // Calculate I = integral of error = (by Euler's method) I_old + error*dt
        integralError += error *(lastTime-currentTime);
        lastTime = currentTime;
        // Calculate total = sum of (coeff * P, I, D)
        return coefficient[0]* error + coefficient[1]*integralError + coefficient[2]* derivativeError;
    }

    public void reset() {
        errorOld = 0;
        integralError = 0;
        lastTime = 0;
    }
}
