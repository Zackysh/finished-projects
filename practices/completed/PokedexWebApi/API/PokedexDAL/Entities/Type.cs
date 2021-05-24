using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.DAL.Entities
{
    public class Type
    {
        public int IdType { get; set; }
        public string Name { get; set; }
        public string RGBColor { get; set; }

        public override string ToString()
        {
            return $"{{{nameof(Name)}={Name}, {nameof(RGBColor)}={RGBColor}}}";
        }
    }
}
