// package com.example.microservice.user.services;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.example.microservice.user.config.exceptions.NotFoundException;
// import com.example.microservice.user.domain.address.Address;
// import com.example.microservice.user.domain.user.User;
// import com.example.microservice.user.dto.address.AddressRequestDTO;
// import com.example.microservice.user.dto.user.UserIdDTO;
// import com.example.microservice.user.dto.user.UserRequestDTO;
// import com.example.microservice.user.repositories.UserRepository;
// import com.example.microservice.user.domain.user.status.Status;

// import java.util.Date;
// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// public class UserServiceTest {
    
//     @Mock
//     private UserRepository userRepository;

//     @InjectMocks
//     private UserService userService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void shouldCreateUserSuccessfully() {
//         AddressRequestDTO addressRequest = new AddressRequestDTO("Rua 1", 123, null, "Bairro 1", "Cidade 1", "Estado 1", "11111-111");
//         UserRequestDTO userRequest = new UserRequestDTO("2222", "password", "name", new Date(), Status.Ativo, addressRequest);
//         Address address = new Address();
//         address.setId("1234");
//         address.setStreet(addressRequest.street());
//         address.setNumber(addressRequest.number());
//         address.setComplement(addressRequest.complement());
//         address.setNeighborhood(addressRequest.neighborhood());
//         address.setCity(addressRequest.city());
//         address.setState(addressRequest.state());
//         address.setZipCode(addressRequest.zipCode());

//         User user = new User();
//         user.setId("1234");
//         user.setCpf(userRequest.cpf());
//         user.setPassword(userRequest.password());
//         user.setName(userRequest.name());
//         user.setBirthDate(userRequest.birthDate());
//         user.setStatus(userRequest.status());
//         user.setAddress(address);
        

//         when(userRepository.findByCpf(userRequest.cpf())).thenReturn(null);
//         when(userRepository.save(any(User.class))).thenReturn(user);

//         UserIdDTO createdUser = userService.createUser(userRequest);

//         assertThat(createdUser).isNotNull();
//         assertThat(createdUser.userId()).isEqualTo("1234");

//         verify(userRepository, times(1)).findByCpf(userRequest.cpf());
//         verify(userRepository, times(1)).save(any(User.class));
//     }

//     @Test
//     public void shouldThrowExceptionWhenUserNotFoundForUpdate() {
//         AddressRequestDTO addressRequest = new AddressRequestDTO("Rua 1", 123, "Bairro 1", "Cidade 1", "Estado 1", "11111-111", null);
//         UserRequestDTO userRequest = new UserRequestDTO(
//             "11111111111", 
//             "password", 
//             "name", 
//             new Date(), 
//             Status.Ativo,
//             addressRequest
//         );

//         when(userRepository.findById("1234")).thenReturn(Optional.empty());

//         assertThatThrownBy(() -> userService.updateUser("1234", userRequest))
//                 .isInstanceOf(NotFoundException.class)
//                 .hasMessageContaining("User not found with id: 1234");

//         verify(userRepository, times(1)).findById("1234");
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     public void shouldUpdateUserSuccessfully() {
//         UserRequestDTO userRequest = new UserRequestDTO("2222", "password", "name", new Date(), Status.Ativo, new AddressRequestDTO());
//         User existingUser = new User();
//         existingUser.setId("1234");
//         existingUser.setCpf("1111");
//         existingUser.setPassword("oldPassword");
//         existingUser.setName("oldName");
//         existingUser.setBirthDate(new Date());
//         existingUser.setStatus(Status.Ativo);

//         when(userRepository.findById("1234")).thenReturn(Optional.of(existingUser));
//         when(userRepository.save(any(User.class))).thenReturn(existingUser);

//         User updatedUser = userService.updateUser("1234", userRequest);

//         assertThat(updatedUser).isNotNull();
//         assertThat(updatedUser.getCpf()).isEqualTo(userRequest.getCpf());
//         assertThat(updatedUser.getPassword()).isEqualTo(userRequest.getPassword());
//         assertThat(updatedUser.getName()).isEqualTo(userRequest.getName());

//         verify(userRepository, times(1)).findById("1234");
//         verify(userRepository, times(1)).save(any(User.class));
//     }
// }