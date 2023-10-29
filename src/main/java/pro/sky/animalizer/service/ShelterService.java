package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.Shelter;
import pro.sky.animalizer.repositories.ShelterRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс-сервис с бизнес-логикой по работе с приютами.
 */
@Service
public class ShelterService {
    private static final Logger logger = LoggerFactory.getLogger(ShelterService.class);
    private final ShelterRepository repository;

    public ShelterService(ShelterRepository repository) {
        this.repository = repository;
    }

    public Shelter getShelterById(Long shelterId) {
        logger.info("getShelterById method has been invoked");
        logger.debug("Requesting info for shelter with id: {}", shelterId);
        logger.error("There is no shelter with id: " + shelterId);
        return repository.findById(shelterId).orElseThrow(ShelterNotFoundException::new);
    }

    public List<Shelter> getAllShelters() {
        logger.info("getAllShelters method has been invoked");
        return repository.findAll();
    }

    public Shelter createShelter(Shelter shelter) {
        logger.info("createShelter method has been invoked");
        return repository.save(shelter);
    }

    public Shelter editShelter(Long shelterId, Shelter shelter) {
        logger.info("editShelter method has been invoked");
        logger.error("There is no shelter with id: " + shelterId);
        Shelter editedShelter = repository.findById(shelterId).orElseThrow(ShelterNotFoundException::new);
        Optional.ofNullable(shelter.getAddress()).ifPresent(editedShelter::setAddress);
        Optional.ofNullable(shelter.getSecurityPhoneNumber()).ifPresent(editedShelter::setSecurityPhoneNumber);
        Optional.ofNullable(shelter.getSchedule()).ifPresent(editedShelter::setSchedule);
        Optional.ofNullable(shelter.getSafetyMeasures()).ifPresent(editedShelter::setSafetyMeasures);
        Optional.ofNullable(shelter.getDirectionPathFile()).ifPresent(editedShelter::setDirectionPathFile);
        Optional.ofNullable(shelter.getShelterType()).ifPresent(editedShelter::setShelterType);
        return repository.save(editedShelter);
    }

    public Shelter deleteShelter(Long shelterId) {
        logger.info("deleteShelter method has been invoked");
        logger.error("There is no shelter with id: " + shelterId);
        Shelter deletedShelter = repository.findById(shelterId).orElseThrow(ShelterNotFoundException::new);
        repository.delete(deletedShelter);
        return deletedShelter;
    }
}
