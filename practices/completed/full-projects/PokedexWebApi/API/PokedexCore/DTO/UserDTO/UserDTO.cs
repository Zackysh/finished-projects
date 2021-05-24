using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.Core.DTO
{
    public class UserDTO
    {
        [Required(ErrorMessage = "Username is required")]
        public string Username { get; set; }
        [Required(ErrorMessage = "Password is required")]
        public string Password { get; set; }

        public override string ToString()
        {
            return $"{{{nameof(Username)}={Username}, {nameof(Password)}={Password}}}";
        }
    }
}
