package com.example.respfitprototype.ui

class UserDetails(
    val modExerciseMins : Int = 0,
    val vigExerciseMins : Int= 0,
    val age : Int= 0,
    val height: Float= 0f,
    val weight: Float= 0f,
    val respiratoryDisType :String = "",
    val userType: UserType = UserType.PATIENT,
    val physicalActivityLevel: PhysicalActivityLevel = PhysicalActivityLevel.LOW,
    val weightLevel: WeightLevel = WeightLevel.OVERWEIGHT, // BMI,
    val physicalActivityProgress: Float= 0f,
    val weightProgress: Float= 0f

) {
    enum class UserType {
        PATIENT,
        DOCTOR,
        CAREGIVER,
        OTHER
    }

    enum class PhysicalActivityLevel {
        LOW {
            override fun getGoalName(): String {
                return "Gentle Exercise Routine"
            }

            override fun getGoalDescription(): String {
                return "Description: Engage in gentle exercise routines that won't trigger asthma symptoms. Aim for activities that are less intense and performed in a controlled environment.\n" +
                        "Examples: ${ExerciseExamples.WALKING(30, "minutes")}, ${ExerciseExamples.GENTLE_YOGA(20, "minutes")}, ${ExerciseExamples.SWIMMING(25, "minutes")}, ${ExerciseExamples.TAI_CHI(15, "minutes")}, ${ExerciseExamples.LIGHT_STRETCHING(10, "minutes")}"
            }
        },
        MODERATE {
            override fun getGoalName(): String {
                return "Aerobic Conditioning"
            }

            override fun getGoalDescription(): String {
                return "Description: Focus on improving cardiovascular health and lung function through moderate aerobic exercises. These activities should increase your heart rate and breathing, but not excessively.\n" +
                        "Examples: ${ExerciseExamples.BRISK_WALKING(45, "minutes")}, ${ExerciseExamples.HIKING(60, "minutes")}, ${ExerciseExamples.CYCLING(30, "minutes")}, ${ExerciseExamples.STATIONARY_BIKE(40, "minutes")}, ${ExerciseExamples.WATER_AEROBICS(45, "minutes")}, ${ExerciseExamples.LOW_IMPACT_AEROBICS(35, "minutes")}"
            }
        },
        HIGH {
            override fun getGoalName(): String {
                return "Asthma-Safe High-Intensity Training"
            }

            override fun getGoalDescription(): String {
                return "Description: Aim for high-intensity interval training (HIIT) or vigorous activities that are safe for asthma patients. Always monitor your breathing and consult with your doctor or asthma specialist before attempting high-intensity exercises.\n" +
                        "Examples: ${ExerciseExamples.INTENSE_EXERCISE(20, "minutes")}, ${ExerciseExamples.SPRINTING(10, "minutes")}, ${ExerciseExamples.FAST_RUNNING(15, "minutes")}, ${ExerciseExamples.HIGH_INTENSITY_SWIMMING(25, "minutes")}"
            }
        };

        abstract fun getGoalName(): String
        abstract fun getGoalDescription(): String

        companion object ExerciseExamples {
            private const val YOU_CAN_DO = "You can do"

            fun WALKING(duration: Int, unit: String) = "comfortable walking $duration $unit"
            fun GENTLE_YOGA(duration: Int, unit: String) = "gentle yoga $duration $unit"
            fun SWIMMING(duration: Int, unit: String) = "controlled swimming $duration $unit"
            fun TAI_CHI(duration: Int, unit: String) = "Tai Chi $duration $unit"
            fun LIGHT_STRETCHING(duration: Int, unit: String) = "light stretching $duration $unit"
            fun BRISK_WALKING(duration: Int, unit: String) = "brisk walking $duration $unit"
            fun HIKING(duration: Int, unit: String) = "hiking $duration $unit"
            fun CYCLING(duration: Int, unit: String) = "cycling $duration $unit"
            fun STATIONARY_BIKE(duration: Int, unit: String) = "stationary bike $duration $unit"
            fun WATER_AEROBICS(duration: Int, unit: String) = "water aerobics $duration $unit"
            fun LOW_IMPACT_AEROBICS(duration: Int, unit: String) = "low-impact aerobics $duration $unit"
            fun INTENSE_EXERCISE(duration: Int, unit: String) = "bursts of intense exercise $duration $unit"
            fun SPRINTING(duration: Int, unit: String) = "sprinting $duration $unit"
            fun FAST_RUNNING(duration: Int, unit: String) = "fast pace running $duration $unit"
            fun HIGH_INTENSITY_SWIMMING(duration: Int, unit: String) = "high-intensity swimming $duration $unit"
        }

        fun getExerciseExamples(): List<String> {
            return when (this) {
                LOW -> listOf(
                    ExerciseExamples.WALKING(30, "mins"),
                    ExerciseExamples.GENTLE_YOGA(20, "mins"),
                    ExerciseExamples.SWIMMING(25, "mins"),
                    ExerciseExamples.TAI_CHI(15, "mins"),
                    ExerciseExamples.LIGHT_STRETCHING(10, "mins")
                )
                MODERATE -> listOf(
                    ExerciseExamples.BRISK_WALKING(45, "mins"),
                    ExerciseExamples.HIKING(60, "mins"),
                    ExerciseExamples.CYCLING(30, "mins"),
                    ExerciseExamples.STATIONARY_BIKE(40, "mins"),
                    ExerciseExamples.WATER_AEROBICS(45, "mins"),
                    ExerciseExamples.LOW_IMPACT_AEROBICS(35, "mins")
                )
                HIGH -> listOf(
                    ExerciseExamples.INTENSE_EXERCISE(20, "mins"),
                    ExerciseExamples.SPRINTING(10, "mins"),
                    ExerciseExamples.FAST_RUNNING(15, "mins"),
                    ExerciseExamples.HIGH_INTENSITY_SWIMMING(25, "mins")
                )
            }
        }
    }

    enum class WeightLevel {
        UNDERWEIGHT {
            override fun getGoalName(): String {
                return "Healthy Weight Gain"
            }

            override fun getGoalDescription(): String {
                return "Description: If you are underweight, your goal is to achieve a healthy weight gain by consuming a balanced diet and engaging in regular exercise to build muscle mass.\n" +
                        "Examples: ${WeightExamples.FOCUS_ON_NUTRIENT_DENSE_FOODS}, ${WeightExamples.STRENGTH_TRAINING_EXERCISES}"
            }
        },
        NORMAL {
            override fun getGoalName(): String {
                return "Maintain Healthy Weight"
            }

            override fun getGoalDescription(): String {
                return "Description: Maintain your current healthy weight by continuing to eat a balanced diet and staying physically active.\n" +
                        "Examples: ${WeightExamples.EAT_VARIETY_OF_NUTRIENT_RICH_FOODS}, ${WeightExamples.MODERATE_INTENSITY_EXERCISES}"
            }
        },
        OVERWEIGHT {
            override fun getGoalName(): String {
                return "Weight Loss"
            }

            override fun getGoalDescription(): String {
                return "Description: If you are overweight, your goal is to achieve weight loss by adopting a healthy eating plan and increasing your physical activity level.\n" +
                        "Examples: ${WeightExamples.FOCUS_ON_CALORIE_CONTROLLED_DIET}, ${WeightExamples.AEROBIC_EXERCISES_AND_STRENGTH_TRAINING}"
            }
        };

        abstract fun getGoalName(): String
        abstract fun getGoalDescription(): String

        companion object {
            object WeightExamples {
                const val FOCUS_ON_NUTRIENT_DENSE_FOODS = "Focus on nutrient-dense foods and consume around 500 calories more than your daily maintenance needs."
                const val STRENGTH_TRAINING_EXERCISES = "Engage in strength training exercises at least 2-3 times a week to build muscle mass."
                const val EAT_VARIETY_OF_NUTRIENT_RICH_FOODS = "Continue to eat a variety of nutrient-rich foods in appropriate portions."
                const val MODERATE_INTENSITY_EXERCISES = "Stay physically active with a combination of moderate-intensity exercises like brisk walking and jogging."
                const val FOCUS_ON_CALORIE_CONTROLLED_DIET = "Focus on a calorie-controlled diet with an emphasis on whole foods and aim to create a calorie deficit of 500-1000 calories per day."
                const val AEROBIC_EXERCISES_AND_STRENGTH_TRAINING = "Engage in a combination of aerobic exercises (e.g., brisk walking, cycling) and strength training to support weight loss."
            }
        }
    }

    //A no-argument constructor
    constructor() : this(0,0,0,0f,0f,"",UserType.OTHER, PhysicalActivityLevel.LOW, WeightLevel.NORMAL,0f,0f)

}
