package com.gpnv.helpcenter;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


public class Information {

    public static String[] months = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    @Table(name = "Medication")
    public static class Medication extends Model {
        @Column(name = "User")
        User user;

        String name;
        String dosageType;
        String storageType;
        int storedMedication;
        long medicationTillTime;

        public List<FixedMedicationTime> times() {
            return getMany(FixedMedicationTime.class, "Medication");
        }

        public static List<Medication> getAll() {
            return new Select()
                    .from(Medication.class)
                    .orderBy("RANDOM()")
                    .execute();
        }
    }

    @Table(name = "MealMedicationTime")
    public static class MealMedicationTime extends Model {
        @Column(name = "User")
        User user;

        @Column(name = "Medication")
        Medication medication;

        @Column(name = "Meal")
        Meal meal;

        int dosage;
        long offsetTime;

        public static List<MealMedicationTime> getAll() {
            return new Select()
                    .from(MealMedicationTime.class)
                    .orderBy("RANDOM()")
                    .execute();
        }
    }

    @Table(name = "FixedMedicationTime")
    public static class FixedMedicationTime extends Model {
        @Column(name = "Medication")
        public Medication medication;

        public int dosage;
        public String time;

        public static List<FixedMedicationTime> getAll() {
            return new Select()
                    .from(FixedMedicationTime.class)
                    .orderBy("RANDOM()")
                    .execute();
        }
    }

    @Table(name = "Meal")
    public static class Meal extends Model {
        @Column(name = "User")
        User user;

        String name;
        long time;

        public static List<Meal> getAll() {
            return new Select()
                    .from(Meal.class)
                    .orderBy("RANDOM()")
                    .execute();
        }
    }

    @Table(name = "Activity")
    public static class Activity extends Model {
        String name;
        String goalType;
        int goal;
        String type;

        public static List<Activity> getAll() {
            return new Select()
                    .from(Activity.class)
                    .orderBy("RANDOM()")
                    .execute();
        }
    }

    @Table(name = "User")
    public static class User extends Model {
        String username;
        String email;
        String hashedPassword;

        public static List<User> getAll() {
            return new Select()
                    .from(User.class)
                    .orderBy("RANDOM()")
                    .execute();
        }
    }
}
