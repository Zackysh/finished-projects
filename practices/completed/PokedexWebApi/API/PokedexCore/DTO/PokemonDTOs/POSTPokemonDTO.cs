using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.Core.DTO
{
    public class POSTPokemonDTO
    {
        [Required(ErrorMessage = "Name is required")]
        [StringLength(45, ErrorMessage = "Name lenght can't have more than 45 characters")]
        public string Name { get; set; }

        [Required(ErrorMessage = "Number is required")]
        [Range(1, 1000000)]
        public int Number { get; set; }

        [Required(ErrorMessage = "Description is required")]
        [StringLength(500, ErrorMessage = "Name lenght can't have more than 500 characters")]
        public string Description { get; set; }

        [Required(ErrorMessage = "Skill is required")]
        [StringLength(45, ErrorMessage = "Skill lenght can't have more than 45 characters")]
        public string Skill { get; set; }

        [Required(ErrorMessage = "Category is required")]
        [StringLength(45, ErrorMessage = "Category lenght can't have more than 45 characters")]
        public string Category { get; set; }

        [Required(ErrorMessage = "Height is required")]
        [Range(1, 1000000)]
        public double Height { get; set; }

        [Required(ErrorMessage = "Weight is required")]
        [Range(1, 1000000)]
        public double Weight { get; set; }

        [Required(ErrorMessage = "Sex is required")]
        [StringLength(45, ErrorMessage = "Sex lenght can't have more than 45 characters")]
        public string Sex { get; set; }

        [Required(ErrorMessage = "Image is required")]
        [Url]
        public string Image { get; set; }

        [Required(ErrorMessage = "Types are required")]
        public int[] Types { get; set; }

    }
}
