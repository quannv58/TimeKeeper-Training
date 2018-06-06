package com.gem.timekeeper.mapper;

import com.gem.timekeeper.data.dto.UserDTO;
import com.gem.timekeeper.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bavv on 5/30/18.
 */
public class UserMapper {
  public UserMapper() {
  }

  public UserModel transform(UserDTO dto) {
    if (null != dto) {
      UserModel model = new UserModel();

      model.setId(dto.getId());
      model.setUsername(dto.getUsername());
      model.setEmail(dto.getEmail());
      model.setStatus(dto.getStatus());
      model.setToken(dto.getToken());
      model.setRole(dto.getRole());

      return model;
    }

    return null;
  }

  public UserDTO transform(UserModel model) {
    if (null != model) {
      UserDTO dto = new UserDTO();

      dto.setId(model.getId());
      dto.setUsername(model.getUsername());
      dto.setEmail(model.getEmail());
      dto.setStatus(model.getStatus());
      dto.setToken(model.getToken());
      dto.setRole(model.getRole());

      return dto;
    }

    return null;
  }

  public List<UserModel> transformToModel(List<UserDTO> dtos) {
    if (null != dtos && !dtos.isEmpty()) {

      List<UserModel> models = new ArrayList<>();

      for (UserDTO dto : dtos) {
        UserModel model = transform(dto);
        if (null != model) {
          models.add(model);
        }
      }

      return models;
    }

    return null;
  }

  public List<UserDTO> transformToDTO(List<UserModel> models) {
    if (null != models && !models.isEmpty()) {

      List<UserDTO> dtos = new ArrayList<>();

      for (UserModel model : models) {
        UserDTO dto = transform(model);
        if (null != dto) {
          dtos.add(dto);
        }
      }

      return dtos;
    }

    return null;
  }
}
