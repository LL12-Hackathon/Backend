package com.example.meetpro.service;

import com.example.meetpro.dto.user.UserEditRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.example.meetpro.entity.user.User;
import com.example.meetpro.Role;
import com.example.meetpro.dto.user.UserSignUpDto;
import com.example.meetpro.repository.user.UserRepository;
import com.example.meetpro.exception.MemberNotFoundException;
import com.example.meetpro.dto.user.UserSimpleResponseDto;
import com.example.meetpro.dto.board.BoardSimpleDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    @Transactional()
    public List<UserSimpleResponseDto> findAllMembers() {
        return userRepository.findAll().stream()
                .map(UserSimpleResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional()
    public UserSimpleResponseDto findMember(final Long id) {
        User user = userRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return UserSimpleResponseDto.toDto(user);
    }


    @Transactional
    public User editMemberInfo(final User user, final UserEditRequestDto userEditRequestDto) {
        // refreshToken 처리는 어떻게 할지 추후에 고민해보기
        user.editUser(userEditRequestDto);
        return user;
    }

    @Transactional
    public void deleteMemberInfo(final User user) {
        // jwt 토큰 만료 처리는 어떻게 할지 추후에 고민해보기
        userRepository.delete(user);
    }


}