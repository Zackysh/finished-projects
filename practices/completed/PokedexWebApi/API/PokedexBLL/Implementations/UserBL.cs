
using MySql.Data.MySqlClient;
using Pokedex.BLL.Contracts;
using Pokedex.Core.DTO;
using Pokedex.DAL.Contracts;
using Pokedex.DAL.Entities;
using System.Diagnostics;

namespace Pokedex.BLL.Implementations
{
    public class UserBL : IUserBL
    {
        public IUserRepository LoginRepository { get; set; }
        public UserBL(IUserRepository loginRepository)
        {
            this.LoginRepository = loginRepository;
        }

        public UserDTO Login(UserDTO userDTO)
        {
            User user = new()
            {
                Username = userDTO.Username,
                Password = userDTO.Password
            };
            try
            {
                User userFromDB = LoginRepository.Login(user);
                if (userFromDB == null)
                    throw new HttpErrorResponseDTO
                    {
                        StatusCode = 404,
                        ErrorMessage = "User not found."
                    };
                UserDTO userDTOFromDB = new()
                {
                    Username = userFromDB.Username,
                    Password = userFromDB.Password
                };
                return userDTOFromDB;
            }
            catch (MySqlException e)
            {
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public bool Register(UserDTO userDTO)
        {
            User user = new()
            {
                Username = userDTO.Username,
                Password = userDTO.Password
            };
            try
            { return LoginRepository.Register(user) == 1; }
            catch (MySqlException e)
            {
                if (e.Number == 1062) // sql duplicate entry error
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 409,
                        ErrorMessage = "This username is not available, try another."
                    };
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }
    }
}
