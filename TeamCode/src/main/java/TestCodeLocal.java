import com.qualcomm.robotcore.hardware.DigitalChannel;

public class TestCodeLocal {
     digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    // Wait for the game to start (driver presses PLAY)
    waitForStart();
}

    public void Red1() {
// is button pressed?
        if (digitalTouch.getState() == false) {
            // button is pressed.
            telemetry.addData("Button", "PRESSED");
        } else (digitalTouch.getState() == true){
            // button is not pressed.
            telemetry.addData("Button", "NOT PRESSED");
        }

        telemetry.addData("Status", "Running");
        telemetry.update();
}
