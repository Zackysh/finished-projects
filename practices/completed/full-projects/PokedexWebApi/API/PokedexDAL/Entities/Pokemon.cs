using Pokedex.Core.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.DAL.Entities
{
    public class Pokemon
    {
        public string Name { get; set; }
        public int Number { get; set; }
        public string Description { get; set; }
        public string Skill { get; set; }
        public string Category { get; set; }
        public double Height { get; set; }
        public double Weight { get; set; }
        public string Sex { get; set; }
        public string Image { get; set; }
        public List<GETTypeDTO> types { get; set; }

        public override string ToString()
        {
            return $"{{{nameof(Name)}={Name}, {nameof(Number)}={Number.ToString()}, {nameof(Description)}={Description}, {nameof(Skill)}={Skill}, {nameof(Category)}={Category}, {nameof(Height)}={Height.ToString()}, {nameof(Weight)}={Weight.ToString()}, {nameof(Sex)}={Sex}}}";
        }
    }
}
