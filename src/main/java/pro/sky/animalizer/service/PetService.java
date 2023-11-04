package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.animalizer.exceptions.PetNotFoundException;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.repositories.PetRepository;

import java.util.List;
import java.util.Optional;


/**
 * Класс-сервис с бизнес-логикой по работе с питомцами.
 */
public class PetService {
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);
    @Autowired
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }
    public Pet getPetById(Long petId) {
        logger.info("getPetById method has been invoked");
        logger.debug("Requesting info for Pet with id: {}", petId);
        logger.error("There is no Pet with id: " + petId);
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getAllPet() {
        logger.info("getAllPets method has been invoked");
        return petRepository.findAll();
    }

    public Pet createPet(Pet pet) {
        logger.info("createPet method has been invoked");
        return petRepository.save(pet);
    }


    public Pet editPet(Long petId, Pet pet) {
        logger.info("editPet method has been invoked");
        logger.error("There is no pet with id: " + petId);
        Pet editedPet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        Optional.ofNullable(pet.getPetType()).ifPresent(editedPet::setPetType);
        Optional.ofNullable(pet.getPetBreed()).ifPresent(editedPet::setPetBreed);
        Optional.ofNullable(pet.getPetOwner()).ifPresent(editedPet::setPetOwner);
        return petRepository.save(editedPet);
    }

    public Pet deletePet(Long petId) {
        logger.info("deletePet method has been invoked");
        logger.error("There is no pet with id: " + petId);
        Pet deletePet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        petRepository.delete(deletePet);
        return deletePet;
    }

    public enum DogHandler {
        IVAN ("У собаки всегда должна быть чистая вода в миске"),
        ANTON ("Собаку нужно любить и ценить"),
        PETR ("Собаке нужно ставить прививки раз в год"),
        ALEX ("Собаку нельзя оставлять одну, если порода плохо переносит одиночество");
        DogHandler(String s) {
        }
    }


}
