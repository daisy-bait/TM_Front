package co.edu.usco.TM.dto.appointment;

public enum PetSpecie {

    DOG, CAT, RODENT, FISH;

    public String getMessageKey() {
        return "pet.species." + this.name();
    }

    public String getIconKey() {
        return "pet.species.icon." + this.name();
    }

}
