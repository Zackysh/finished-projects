using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.Core.DTO
{
    public class GETTypeDTO
    {
        public int IdType { get; set; }
        public string Name { get; set; }
        public string RGBColor { get; set; }

        public override string ToString()
        {
            return $"{{{nameof(IdType)}={IdType.ToString()}, {nameof(Name)}={Name}, {nameof(RGBColor)}={RGBColor}}}";
        }
    }
}
