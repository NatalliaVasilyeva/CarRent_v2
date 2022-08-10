package com.dmdev.natalliavasilyeva.service;

import com.dmdev.natalliavasilyeva.api.mapper.UserMapper;
import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicenseJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.ShotUser;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.domain.model.UserLogin;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryCustomFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.RepositoryFactory;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.dao.UserCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.DriverLicenseRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserDetailsRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao.UserRepository;
import com.dmdev.natalliavasilyeva.service.exception.NotFoundException;
import com.dmdev.natalliavasilyeva.service.exception.UserBadRequestException;
import com.dmdev.natalliavasilyeva.utils.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
    RepositoryCustomFactory repositoryCustomFactory = RepositoryCustomFactory.getInstance();
    DriverLicenseRepository driverLicenseRepository = repositoryFactory.getDriverLicenseRepository();
    UserDetailsRepository userDetailsRepository = repositoryFactory.getUserDetailsRepository();
    UserRepository userRepository = repositoryFactory.getUserRepository();
    UserCustomRepository userCustomRepository = repositoryCustomFactory.getUserCustomRepository();

    public User createUser(User user) {
        if (ensureEmailExists(user.getEmail()) || ensureLoginExists(user.getLogin())) {
            throw new UserBadRequestException("Login or email already exists. Please choose other one!");
        }

        var userJpa = UserMapper.toUserJpa(user);
        userJpa.setPassword(PasswordUtils.generateHash(user.getLogin(), user.getPassword()));
        var savedUserJpa = userRepository.save(userJpa);

        var userDetailsJpa = UserMapper.toUserDetailsJpa(user);
        savedUserJpa.ifPresent(jpa -> userDetailsJpa.setUserId(jpa.getId()));
        var savedUserDetailsJpa = userDetailsRepository.save(userDetailsJpa);

        String driverNumber = user.getDriverLicense().getNumber();
        Instant issueDate = user.getDriverLicense().getIssueDate();
        Instant expiredDate = user.getDriverLicense().getExpiredDate();

        Optional<DriverLicenseJpa> savedDriverLicenseJpa = Optional.empty();
        if (!(StringUtils.isBlank(driverNumber) &&
                (issueDate == null || StringUtils.isBlank(issueDate.toString())) &&
                (expiredDate == null || StringUtils.isBlank(user.getDriverLicense().getExpiredDate().toString())))) {
            var driverLicenseJpa = UserMapper.toDriverLicenseJpa(user);
            savedUserDetailsJpa.ifPresent(jpa -> driverLicenseJpa.setUserDetailsId(jpa.getId()));
            savedDriverLicenseJpa = driverLicenseRepository.save(driverLicenseJpa);
        }

        if (savedDriverLicenseJpa.isEmpty()) {
            return UserMapper.fromJpa(savedUserJpa.get(), savedUserDetailsJpa.get(), new DriverLicenseJpa.Builder().build());
        } else {
            return savedDriverLicenseJpa.map(jpa ->
                            UserMapper.fromJpa(savedUserJpa.get(), savedUserDetailsJpa.get(), jpa))
                    .orElseThrow(() -> new RuntimeException("Problem with user saving."));
        }
    }

    public User updateUser(Long id, User user) {
        var existingUserJpa = ensureUserExistsByIdWithPassword(id);
        if (ensureEmailExists(user.getEmail())) {
            throw new UserBadRequestException("Email already exists. Please choose other one!");
        }
        if (!Objects.equals(user.getEmail(), existingUserJpa.getEmail())) {
            existingUserJpa.setEmail(user.getEmail());
        }
        var savedUserJpa = userRepository.update(existingUserJpa);

        var savedUserDetails = savedUserJpa.map(jpa -> {
                    var existingUserDetailsJpa = ensureUserDetailsExistsByUserId(jpa.getId());
                    existingUserDetailsJpa.setName(user.getName());
                    existingUserDetailsJpa.setSurname(user.getSurname());
                    existingUserDetailsJpa.setAddress(user.getAddress());
                    existingUserDetailsJpa.setPhone(user.getPhone());
                    return userDetailsRepository.update(existingUserDetailsJpa).orElseThrow(() -> new RuntimeException("Problem with user updating."));
                }
        );

        var savedDriverLicense = savedUserDetails.map(jpa -> {
                    var existingDriverLicenseJpa = ensureDriverLicenseExistsByUserDetailsId(jpa.getId());
                    existingDriverLicenseJpa.setNumber(user.getDriverLicense().getNumber());
                    existingDriverLicenseJpa.setIssueDate(user.getDriverLicense().getIssueDate());
                    existingDriverLicenseJpa.setExpiredDate(user.getDriverLicense().getExpiredDate());
                    return driverLicenseRepository.update(existingDriverLicenseJpa).orElseThrow(() -> new RuntimeException("Problem with user updating."));
                }
        );

        return savedDriverLicense.map(jpa ->
                        UserMapper.fromJpa(savedUserJpa.get(), savedUserDetails.get(), jpa))
                .orElseThrow(() -> new RuntimeException("Problem with user updating."));
    }

    public ShotUser getShotUserById(Long id) {
        return UserMapper.fromJpa(ensureUserExistsById(id));
    }

    public ShotUser login(UserLogin user) {
        String hash = PasswordUtils.generateHash(user.getLogin(), user.getPassword());
        UserJpa userJpa = ensureUserExistsByLoginAndPassword(user.getLogin(), hash);
        return UserMapper.fromJpa(userJpa);
    }

    public boolean updatePassword(Long id, String password) {
        var existingUserJpa = ensureUserExistsById(id);
        String hash = PasswordUtils.generateHash(existingUserJpa.getLogin(), password);
        existingUserJpa.setPassword(hash);
        var savedUserJpa = userRepository.update(existingUserJpa);
        return savedUserJpa.isPresent();
    }

    public User getUserById(Long id) {
        UserJpa userJpa = ensureUserExistsById(id);
        UserDetailsJpa userDetailsJpa = userDetailsRepository
                .findByUserId(userJpa.getId()).orElseThrow(() -> new NotFoundException(String.format("User details with user id %s does not exist.", userJpa.getId())));
        DriverLicenseJpa driverLicenseJpa = driverLicenseRepository
                .findByUserDetailsId(userDetailsJpa.getId()).orElseThrow(() -> new NotFoundException(String.format("Driver license with user user details id %s does not exist.", userDetailsJpa.getId())));
        return UserMapper.fromJpa(userJpa, userDetailsJpa, driverLicenseJpa);
    }

    public User getCustomUserById(Long id) {
        return userCustomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
    }

    public List<ShotUser> getAllShotUsers() {
        return UserMapper.fromJpaList(userRepository.findAll());
    }
    public List<User> getAllUsers() {
        List<UserJpa> userJpas = userRepository.findAll();
        List<UserDetailsJpa> userDetailsJpas = userDetailsRepository.findAll();
        List<DriverLicenseJpa> driverLicenseJpas = driverLicenseRepository.findAll();
        return UserMapper.fromJpaList(userJpas, userDetailsJpas, driverLicenseJpas);
    }

    public List<User> getAllCustomUsers() {
        return userCustomRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    public boolean deleteUserById(Long id) {
        ensureUserExistsById(id);
        return userRepository.deleteById(id);
    }

    public User deleteUser(User user) {
        ensureUserExistsById(user.getId());
        boolean result = userRepository.delete(UserMapper.toUserJpa(user))
                .map(shotUser -> {
                    UserDetailsJpa userDetailsJpa = userDetailsRepository
                            .findByUserId(shotUser.getId()).orElseThrow(() -> new NotFoundException(String.format("User detail with user id %s does not exist.", shotUser.getId())));
                    Optional<UserDetailsJpa> deleted = userDetailsRepository.delete(userDetailsJpa);
                    UserDetailsJpa jpa = null;
                    if (deleted.isPresent()) {
                        jpa = deleted.get();
                    }
                    return jpa;
                })
                .map(userDetailsJpa -> {
                    DriverLicenseJpa driverLicenseJpa = driverLicenseRepository
                            .findByUserDetailsId(userDetailsJpa.getId()).orElseThrow(() -> new NotFoundException(String.format("Driver license with user details id %s does not exist.", userDetailsJpa.getId())));
                    Optional<DriverLicenseJpa> deleted = driverLicenseRepository.delete(driverLicenseJpa);
                    DriverLicenseJpa jpa = null;
                    if (deleted.isPresent()) {
                        jpa = deleted.get();
                    }
                    return jpa;
                })
                .isPresent();
        if (result) {
            return user;
        } else {
            throw new RuntimeException("Problem with user removing.");
        }
    }

    public List<ShotUser> findAllUsersByRole(String role) {
        return userRepository.findAllByRole(role)
                .stream()
                .map(UserMapper::fromJpa)
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersByRole(String role) {
        return userCustomRepository.findByRole(role)
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    public User findCustomUserByLogin(String login) {
        return userCustomRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(String.format("User with login %s does not exist.", login)));
    }

    public User findCustomUserByEmail(String email) {
        return userCustomRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s does not exist.", email)));
    }

    public List<User> findAllCustomUsersByRegistrationDate(Instant registrationDate) {
        return userCustomRepository.findByRegistrationDate(registrationDate)
                .stream()
                .sorted(Comparator.comparing(User::getRegistrationDate))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersByRegistrationDateLessThan(Instant registrationDate) {
        return userCustomRepository.findByRegistrationDateLess(registrationDate)
                .stream()
                .sorted(Comparator.comparing(User::getRegistrationDate))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersByRegistrationDateMoreThan(Instant registrationDate) {
        return userCustomRepository.findByRegistrationDateMore(registrationDate)
                .stream()
                .sorted(Comparator.comparing(User::getRegistrationDate))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersByPhone(String phone) {
        return userCustomRepository.findByPhone(phone)
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersByBirthday(Instant birthday) {
        return userCustomRepository.findByBirthday(birthday)
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersWithOrders() {
        return userCustomRepository.findAllWithOrders()
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersWithoutOrders() {
        return userCustomRepository.findAllWithoutOrders()
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    public List<User> findAllCustomUsersWithExpiredDriverLicense() {
        return userCustomRepository.findAllWithExpiredDriverLicense()
                .stream()
                .sorted(Comparator.comparing(User::getSurname))
                .collect(Collectors.toList());
    }

    private boolean ensureLoginExists(String login) {
        return userRepository.existByLogin(login);
    }

    private boolean ensureEmailExists(String email) {
        return userRepository.existByEmail(email);
    }

    private UserJpa ensureUserExistsByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new NotFoundException("User with such credentials does not exist."));
    }

    private UserJpa ensureUserExistsById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
    }

    private UserJpa ensureUserExistsByIdWithPassword(Long id) {
        return userRepository.findWithPasswordById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
    }

    private UserDetailsJpa ensureUserDetailsExistsByUserId(Long id) {
        return userDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException(String.format("User details with user id %s does not exist.", id)));
    }

    private DriverLicenseJpa ensureDriverLicenseExistsByUserDetailsId(Long id) {
        return driverLicenseRepository.findByUserDetailsId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Driver license for user with user details id %s does not exist.", id)));
    }
}