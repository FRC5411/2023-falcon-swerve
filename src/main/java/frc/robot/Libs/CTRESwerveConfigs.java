package frc.robot.Libs;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import frc.robot.Constants.DRIVETRAIN;

public class CTRESwerveConfigs {

    // Falonc Swerve Drive motor Configs, the PID vals can be determined using sys id in drivetrain analysis
    public static WPI_TalonFX configDrive(WPI_TalonFX driveMotor) {
        StatorCurrentLimitConfiguration DRIVE_CURRENT_LIMIT = new StatorCurrentLimitConfiguration(true, 60, 60, 0);
        driveMotor.configFactoryDefault();
        driveMotor.setInverted(TalonFXInvertType.CounterClockwise);
        driveMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.configStatorCurrentLimit(DRIVE_CURRENT_LIMIT);
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        driveMotor.setSelectedSensorPosition(0);
        driveMotor.config_kP(0, DRIVETRAIN.DRIVE_kP);
        driveMotor.config_kF(0, DRIVETRAIN.DRIVE_kF);
        driveMotor.configVoltageCompSaturation(12);
        driveMotor.enableVoltageCompensation(true);

        return driveMotor;
    }

    // Cancoder most be configed first before the azimuth motor
    // Try using the 0.2 P first and see if it works since we are using the internal PID loop
    public static WPI_TalonFX configAzimuth (WPI_TalonFX motor, CANCoder position) {
      
        StatorCurrentLimitConfiguration AZIMUTH_CURRENT_LIMIT = new StatorCurrentLimitConfiguration(true, 20, 20, 0);
        motor.configFactoryDefault();
        motor.setInverted(TalonFXInvertType.CounterClockwise);
        motor.setNeutralMode(NeutralMode.Brake);
        motor.configRemoteFeedbackFilter(position, 0);
        motor.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
        motor.configStatorCurrentLimit(AZIMUTH_CURRENT_LIMIT);
        motor.setSelectedSensorPosition(position.getAbsolutePosition());
        motor.config_kP(0, DRIVETRAIN.AZIMUTH_kP);
        motor.config_kD(0, DRIVETRAIN.AZIMUTH_kD);
        motor.config_kF(0, DRIVETRAIN.AZIMUTH_kF);
        motor.configNeutralDeadband(DRIVETRAIN.AZIMUTH_DEADBAND);

        return motor;
      }

      // Config position
      public static CANCoder configPosition (CANCoder encoder, double offset) {
        encoder.configFactoryDefault();
        encoder.configMagnetOffset(offset);
        encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        encoder.setPositionToAbsolute();

        return encoder;
      }
}
