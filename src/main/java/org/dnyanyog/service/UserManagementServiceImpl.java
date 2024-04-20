package org.dnyanyog.service;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import org.dnyanyog.dto.AddUserRequest;
import org.dnyanyog.dto.AddUserResponse;
import org.dnyanyog.dto.DiscountRequest;
import org.dnyanyog.dto.DiscountResponse;
import org.dnyanyog.encryption.EncryptionUtil;
import org.dnyanyog.entity.Discount;
import org.dnyanyog.entity.Users;
import org.dnyanyog.enums_with_values.ErrorCode;
import org.dnyanyog.repo.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService {

  Logger logger = LoggerFactory.getLogger(UserManagementService.class);

  @Autowired UsersRepository userRepo;

  @Autowired AddUserResponse userResponse;

  @Autowired private List<Long> userIds;

  @Autowired EncryptionUtil encryptionService;

  @Override
  public Optional<AddUserResponse> addUpdateUser(AddUserRequest request) throws Exception {

    Users usersTable = new Users();
    usersTable.setUserId(request.getUserId());
    usersTable.setEmail(request.getEmail());
    usersTable.setPassword(encryptionService.encrypt(request.getPassword()));
    usersTable.setUsername(request.getUsername());

    usersTable = userRepo.save(usersTable);

    userResponse.setStatus(ErrorCode.USER_FOUND.getStatus());
    userResponse.setMessage(ErrorCode.USER_FOUND.getMessage());
    userResponse.getUserData().setUserId(usersTable.getUserId());

    userResponse.getUserData().setEmail(usersTable.getEmail());
    userResponse.getUserData().setUsername(usersTable.getUsername());
    userResponse.getUserData().setAge(usersTable.getAge());

    return Optional.of(userResponse);
  }

  @Override
  public AddUserResponse getSingleUser(Long userId) throws Exception {

    Optional<Users> receivedData = userRepo.findById(userId);

    if (receivedData.isEmpty()) {
      userResponse.setStatus(ErrorCode.USER_NOT_FOUND.getStatus());
      userResponse.setMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    } else {
      Users user = receivedData.get();

      userResponse.setStatus(ErrorCode.USER_SEARCH.getStatus());
      userResponse.setMessage(ErrorCode.USER_SEARCH.getMessage());
      userResponse.getUserData().setUserId(user.getUserId());
      userResponse.getUserData().setEmail(user.getEmail());
      userResponse.getUserData().setUsername(user.getUsername());
      userResponse.getUserData().setAge(user.getAge());
    }
    return userResponse;
  }

  @Override
  public List<Users> getAllUser() {
    return userRepo.findAll();
  }

  @Override
  public List<Long> getAllUserIds() {

    List<Users> users = userRepo.findAll();

    for (Users user : users) {
      if (nonNull(user)) {
        userIds.add(user.getUserId());
      }
    }
    return userIds;
  }

  @Override
  public Optional<DiscountResponse> addDiscount(DiscountRequest request) {
    try {
      DiscountResponse response = new DiscountResponse();

      double discount = calculateDiscount(request.getAge());

      if ("female".equalsIgnoreCase(request.getGender())) {
        discount += 5;
      }

      saveDiscountInDatabase(request.getAge(), request.getGender(), discount);

      response.setDiscountPercentage(discount);

      return Optional.of(response);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  private void saveDiscountInDatabase(int age, String gender, double discount) {

    Discount discountEntity = new Discount();
    discountEntity.setAge(age);
    discountEntity.setGender(gender);
    discountEntity.setDiscountPercentage(discount);

    userRepo.save(discountEntity);
  }

  private double calculateDiscount(int age) {
    if (age < 30) {
      return 10;
    } else if (age >= 30 && age < 60) {
      return 5;
    } else {
      return 15;
    }
  }
}
