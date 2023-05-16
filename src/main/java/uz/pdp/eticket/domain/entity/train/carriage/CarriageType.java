package uz.pdp.eticket.domain.entity.train.carriage;

public enum CarriageType {
    SEDENTARY(1),
    COMPOSITE(2),
    COMPARTMENT(3),
    LUX(4);

    private final Integer value;

    CarriageType(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
}
