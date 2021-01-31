/*This is bringing in all of the information from the SDK we downloaded
An SDK is a kit of programs needed and making your life easier because you don't have to write them*/
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
//import com.qualcomm.robotcore.hardware.DigitalChannel;

//import java.security.PublicKey;

//Here we are registering the name
@Autonomous(name="DemoAuto", group = "Demo")
public class DemoAuto extends LinearOpMode {
//Here we are declaring the motors and sensors

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
   // private DigitalChannel touch;

    //Here we are giving "backing" to the declaration
//LOL
    public void intihardware() {
        frontRight = hardwareMap.dcMotor.get("fr");
        frontLeft = hardwareMap.dcMotor.get("fl");
        backRight = hardwareMap.dcMotor.get("br");
        backLeft = hardwareMap.dcMotor.get("bl");
       // touch = hardwareMap.get(DigitalChannel.class, "touch_sensor" );
    }

    //Here we are setting the motors to zero
    public void pause() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    //Here we are telling the motors to start moving at half power forward
    public void forwardHalfPower() {
        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);
    }

    //By having the two wheels on the same side at negative power it will cause the robot to turn
    public void turn() {
        frontRight.setPower(1);
        frontLeft.setPower(-1);
        backRight.setPower(1);
        backLeft.setPower(-1);
    }

    private double ticksPerRevNR40 = 1120;

    //The post gear box gear ratio.
    private double gearRatio = 1.0;
    //The circumference of the drive wheel.
    private double wheelCircumference = 31.9024; // ??
    //Formula to calculate ticks per centimeter for the current drive set up.FORWARDS/BACKWARD ONLY
    private double ticksPerCm = (ticksPerRevNR40 * gearRatio) / wheelCircumference;
    //Formula to calculate ticks per centimeter for the current drive set up.SIDEWAYS


    @Override
    public void runOpMode() throws InterruptedException {
        forwardHalfPower();
        pause();
        turn();
    
        waitForStart();
    }

    public void Red1() {
    waitForStart();
    while (opModeIsActive()) {
        forward(60, .5);
        sideRight(30, .5);
        forward(45,.5);
        break;
    }

    }
    public void Red2() {
       waitForStart();
        while (opModeIsActive()){
            forward(60,.5);
            sideLeft(30,.5);
            forward(60,.5);
            break;
    }
    }

    public void Red3() {
        waitForStart();
        while (opModeIsActive()){
            forward(60,.5);
            sideRight(30,.5);
            forward(75,.5);
            break;
        }
    }

    public void Blue1() {
        waitForStart();
        while (opModeIsActive()){
            forward(60,.5);
            sideLeft(30,.5);
            forward(45,.5);
            break;
        }
    }

    public void Blue2() {
        waitForStart();
        while (opModeIsActive()){
            forward(60,.5);
            sideRight(30,.5);
            forward(60,.5);
            break;
        }
    }

    public void Blue3() {
        waitForStart();
        while (opModeIsActive()){
            forward(60,.5);
            sideLeft(30,.5);
            forward(75,.5);
            break;
        }
    }

    public void chooseprogram() {
        telemetry.addData("ChoosePosition", "RED1-A, RED2-B, RED3-X, BLUE1-A,BLUE2-B,BLUE3-X");
        while (!isStarted()) {
            if (gamepad1.a) {
                telemetry.addData("RobotPosition", "RED1");
                Red1();
                telemetry.update();
                break;
            } else if (gamepad1.b) {
                telemetry.addData("RobotPosition", "RED2");
                Red2();
                telemetry.update();
                break;
            } else if (gamepad1.x) {
                telemetry.addData("RobotPosition", "RED3");
                Red3();
                telemetry.update();
                break;
            } else if (gamepad2.a) {
                telemetry.addData("RobotPosition", "BLUE1");
                Blue1();
                telemetry.update();
                break;
            } else if (gamepad2.b) {
                telemetry.addData("RobotPosition", "BLUE2");
                Blue2();
                telemetry.update();
                break;
            } else if (gamepad2.x) {
                telemetry.addData("RobotPosition", "BLUE3");
                Blue3();
                telemetry.update();
                break;
            } else {
                telemetry.addData("waiting", "help");
                telemetry.update();
            }
        }
    }

    public  void setDriveMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);

    }
    public void forward(double targetDistance, double power){
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double targetDistanceTicks = Math.abs(targetDistance * ticksPerCm);
        double currentDistanceTicks = 0;
        while ((Math.abs(currentDistanceTicks) < targetDistanceTicks) && opModeIsActive()) {
            telemetry.addData("Target pos ticks: ", targetDistanceTicks);
            telemetry.addData("Target Distance:", targetDistance + "cm");
            currentDistanceTicks = (frontRight.getCurrentPosition() +
                    frontLeft.getCurrentPosition() +
                    backRight.getCurrentPosition() +
                    backLeft.getCurrentPosition()) / 4.0;
            telemetry.addData("Current pos ticks Avg: ", currentDistanceTicks);
            telemetry.addData("Current Distance cm", currentDistanceTicks / ticksPerCm);
            telemetry.update();
        }
        stopMotors();
    }
    public void sideLeft(double targetDistance, double power){
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double currentDistance = 0;
        while (Math.abs(currentDistance) < targetDistance && opModeIsActive()) {
            currentDistance = frontRight.getCurrentPosition();
            frontLeft.setPower(power);
            frontRight.setPower(-power);
            backLeft.setPower(-power);
            backRight.setPower(power);
        }
        stopMotors();
    }
    public void sideRight(double targetDistance, double power) {
        setDriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double currentDistance = 0;
        while ((Math.abs(currentDistance) < targetDistance) && opModeIsActive()) {
            currentDistance = frontLeft.getCurrentPosition();
            frontLeft.setPower(-power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(-power);
        }
        stopMotors();
    }
public void stopMotors(){
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
}

    public void run(){
    intihardware();
    chooseprogram();
}
}

