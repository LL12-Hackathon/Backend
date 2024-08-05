package com.example.meetpro.mappers;

import com.example.meetpro.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberMapper {
    int insertUser(UserEntity user);
}
