package pro.sky.animalizer.model;

public enum UserType {
    VALUNTEER ("User волонтёр."),
    ADOPTER ("User усыновитель."),
    DEFAULT ("User без животного."),
    BADUSER ("User с плохой историей усыновления.");
    UserType (String s) {
    }

    UserType() {
    }
    class VALUNTEER {
        void ask(){
            System.out.println("Здраствуйте! Вы позвали Волонтёра. Чем я могу помочь?");
        }

    }
}

