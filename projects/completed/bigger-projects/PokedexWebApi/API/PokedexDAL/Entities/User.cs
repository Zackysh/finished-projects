using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.DAL.Entities
{
    public class User
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public override string ToString()
        {
            return $"{{{nameof(Username)}={Username}, {nameof(Password)}={Password}}}";
        }
    }
}
