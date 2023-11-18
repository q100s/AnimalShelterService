package pro.sky.animalizer.model;

public enum UserType {
    VOLUNTEER("User волонтёр."),
    ADOPTER("User усыновитель."),
    DEFAULT("User без животного."),
    BADUSER("User с плохой историей усыновления.");

    UserType(String s) {
    }
    UserType() {
    }
}