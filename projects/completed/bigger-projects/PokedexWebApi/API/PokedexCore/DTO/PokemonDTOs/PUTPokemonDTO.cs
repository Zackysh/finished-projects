using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Pokedex.Core.DTO
{
    public class PUTPokemonDTO
    {
        [StringLength(45, ErrorMessage = "Name lenght can't have more than 45 characters")]
        public string Name { get; set; }

        [Range(0, 1000000)]
        public int Number { get; set; }

        [StringLength(500, ErrorMessage = "Name lenght can't have more than 500 characters")]
        public string Description { get; set; }

        [StringLength(45, ErrorMessage = "Skill lenght can't have more than 45 characters")]
        public string Skill { get; set; }

        [StringLength(45, ErrorMessage = "Category lenght can't have more than 45 characters")]
        public string Category { get; set; }

        [Range(0, 1000000)]
        public double Height { get; set; }

        [Range(0, 1000000)]
        public double Weight { get; set; }

        [StringLength(45, ErrorMessage = "Sex lenght can't have more than 45 characters")]
        public string Sex { get; set; }

        [Url]
        public string Image { get; set; }

        public int[] Types { get; set; }

        public override string ToString()
        {
            return $"{{{nameof(Name)}={Name}, {nameof(Number)}={Number.ToString()}, {nameof(Description)}={Description}, {nameof(Skill)}={Skill}, {nameof(Category)}={Category}, {nameof(Height)}={Height.ToString()}, {nameof(Weight)}={Weight.ToString()}, {nameof(Sex)}={Sex}}}";
        }
    }
}
