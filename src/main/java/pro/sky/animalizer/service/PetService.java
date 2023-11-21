package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.PetNotFoundException;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.repositories.PetRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * Класс-сервис с бизнес-логикой по работе с питомцами.
 */
@Service
public class PetService {
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet getPetById(Long petId) {
        logger.info("getPetById method has been invoked");
        logger.debug("Requesting info for Pet with id: {}", petId);
        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> getAllPets() {
        logger.info("getAllPets method has been invoked");
        return petRepository.findAll();
    }

    public Pet getPetByUserId(Long userId) {
        logger.info("getPetByUserId method has been invoked");
        logger.debug("Requesting info for Pet with user's id: {}", userId);
        logger.error("There is no user with id: " + userId);
        return petRepository.findByAdopter_id(userId).orElseThrow(UserNotFoundException::new);
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
        Optional.ofNullable(pet.getPetName()).ifPresent(editedPet::setPetName);
        Optional.ofNullable(pet.getPhotoUrlPath()).ifPresent(editedPet::setPhotoUrlPath);
        return petRepository.save(editedPet);
    }

    public void deletePet(Long petId) {
        logger.info("deletePet method has been invoked");
        logger.error("There is no pet with id: " + petId);
        Pet deletePet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        petRepository.delete(deletePet);
    }

    public Collection<Pet> getAllPetsWithoutAdopter() {
        return petRepository.findAllWithoutAdopter();
    }
}