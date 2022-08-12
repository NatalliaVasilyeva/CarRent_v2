package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.DriverLicenseMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicenseJpa;
import com.dmdev.natalliavasilyeva.domain.model.DriverLicense;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.DriverLicenseRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserDetailsRepository;
import com.dmdev.natalliavasilyeva.service.exception.DriverLicenseBadRequestException;
import com.dmdev.natalliavasilyeva.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class DriverLicenseService {

    private static final Logger logger = LoggerFactory.getLogger(DriverLicenseService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    DriverLicenseRepository driverLicenseRepository = repositoryFactory.getDriverLicenseRepository();
    UserDetailsRepository userDetailsRepository = repositoryFactory.getUserDetailsRepository();


    public DriverLicense createDriverLicense(DriverLicense driverLicense) {
        isUniqueDriverLicenseNumber(driverLicense.getNumber());
        var jpa = DriverLicenseMapper.toJpa(driverLicense);
        return driverLicenseRepository.save(jpa)
                .map(DriverLicenseMapper::fromJpa)
                .orElseThrow(RuntimeException::new);
    }

    public DriverLicense updateDriverLicense(Long id, DriverLicense driverLicense) {
        var existingDriverLicense = ensureDriverLicenseExistsById(id);
        if (!existingDriverLicense.getNumber().equals(driverLicense.getNumber())) {
            isUniqueDriverLicenseNumber(driverLicense.getNumber());
        }
        ensureUserDetailsExistsById(driverLicense.getId());
        existingDriverLicense.setNumber(driverLicense.getNumber());
        existingDriverLicense.setIssueDate(driverLicense.getIssueDate());
        existingDriverLicense.setExpiredDate(driverLicense.getExpiredDate());
        return driverLicenseRepository.update(existingDriverLicense)
                .map(DriverLicenseMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with driver license updating"));
    }

    public List<DriverLicense> getAllDriverLicenses() {
        return driverLicenseRepository.findAll()
                .stream()
                .map(DriverLicenseMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public DriverLicense getDriverLicenseById(Long id) {
        return DriverLicenseMapper.fromJpa(ensureDriverLicenseExistsById(id));
    }

    public boolean deleteDriverLicenseById(Long id) {
        ensureDriverLicenseExistsById(id);
        return driverLicenseRepository.deleteById(id);
    }

    public DriverLicense deleteDriverLicense(DriverLicense driverLicense) {
        ensureDriverLicenseExistsById(driverLicense.getId());
        return driverLicenseRepository.delete(DriverLicenseMapper.toJpa(driverLicense))
                .map(DriverLicenseMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException("Problem with driver license deleting"));
    }

    public DriverLicense findDriverLicenseByUserId(Long userId) {

        return driverLicenseRepository.findByUserId(userId)
                .map(DriverLicenseMapper::fromJpa)
                .orElseThrow(() -> new RuntimeException(String.format("Driver license for user with id '%s' doesn't exists", userId)));
    }

    public boolean isDriverLicenseExpired(Long userId) {
        DriverLicense driverLicense = findDriverLicenseByUserId(userId);
        return driverLicense.getExpiredDate().isAfter(Instant.now());
    }

    public List<DriverLicense> findAllExpiredDriverLicenses() {
        return driverLicenseRepository.findAllExpired()
                .stream()
                .map(DriverLicenseMapper::fromJpa)
                .collect(Collectors.toList());
    }

    private DriverLicenseJpa ensureDriverLicenseExistsById(Long id) {
        return driverLicenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Driver license with id %s does not exist.", id)));
    }

    private void ensureUserDetailsExistsById(Long id) {
        if (!userDetailsRepository.findByUserId(id).isPresent()) {
            throw new NotFoundException(String.format("User details with id %s does not exist.", id));
        }
    }

    private void isUniqueDriverLicenseNumber(String licenseNumber) {
        if (driverLicenseRepository.existByNumber(licenseNumber)) {
            throw new DriverLicenseBadRequestException(String.format("Driver license with number '%s' already exists", licenseNumber));
        }
    }
}