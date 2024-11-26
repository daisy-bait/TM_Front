package co.edu.usco.TM.dto.shared;

public enum PetSpecie {

    DOG, CAT, RODENT, FISH;

    public String getPetSpecie() {
        return name();
    }

    public String getMessageKey() {
        return "pet.species." + getPetSpecie();
    }

    public String getIconKey() {
        return "pet.species.icon." + getPetSpecie();
    }

}
