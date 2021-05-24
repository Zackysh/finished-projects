using Pokedex.Core.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.BLL.Contracts
{
    public interface IUserBL
    {
        /// <summary>
        ///     Handle User Login. It will return (if found)
        ///     one User from DB with same name as given one.
        /// </summary>
        /// <param name="loginDTO"></param>
        /// <returns>Username with hashed password from DB (if found).</returns>
        /// <returns>null (if not found).</returns>
        public UserDTO Login(UserDTO loginDTO);
        /// <summary>
        ///     It will Insert given User into DB (if not exist yet).
        /// </summary>
        /// <param name="LOGINdto"></param>
        /// <returns>true (if all went Ok).</returns>
        /// <returns>false (if something went wrong</returns>
        public bool Register(UserDTO LOGINdto);
    }
}
