package pl.wsb.fitnesstracker.workoutsession;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import pl.wsb.fitnesstracker.training.api.Training;

@Getter
@Entity
public class WorkoutSession {

    @Id
    private int id;
    private String timestamp;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private double altitude;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
}
