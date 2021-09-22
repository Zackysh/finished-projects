using Pokedex.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.DAL.Contracts
{
    public interface IUserRepository
    {
        /// <summary>
        ///     Fetch (if exist) desired User.
        /// </summary>
        /// <param name="user">User to look for.</param>
        /// <returns>Found User If any record is found.</returns>
        /// <returns>null If no record is found.</returns>
        public User Login(User user);
        /// <summary>
        ///     Try to Insert given User.
        /// </summary>
        /// <param name="user"></param>
        /// <returns>Number of affected rows.</returns>
        public int Register(User user);
    }
}
