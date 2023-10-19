package com.dpap.bookingapp.users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRepository implements UserDatabase {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserRepository(UserMapper userMapper, UserJpaRepository userJpaRepository, RoleRepository roleRepository) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(userMapper::fromUserEntity);
    }

    @Override
    public User save(User user) {
        List<RoleEntity> roles = roleRepository.findAll().stream().filter(role -> role.getType().name().equals("USER")).collect(Collectors.toList());
        var userEntity = userJpaRepository.save(userMapper.entityFromUser(user, roles));
        return userMapper.fromUserEntity(userEntity);
    }

    @Override
    public List<User> findAllUsers() {
        return userJpaRepository.findAll()
                .stream()
                .map(userMapper::fromUserEntity)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
       return userJpaRepository.findById(id).map(userMapper::fromUserEntity);
    }
    public Optional<UserEntity> findEntityById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
