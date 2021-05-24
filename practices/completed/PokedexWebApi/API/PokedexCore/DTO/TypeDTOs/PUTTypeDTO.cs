using System.ComponentModel.DataAnnotations;

namespace Pokedex.Core.DTO
{
    public class PUTTypeDTO
    {
        [StringLength(45, ErrorMessage = "Name lenght can't have more than 45 characters")]
        public string Name { get; set; }

        [RegularExpression(@"^(rgb)?\(?([01]?\d\d?|2[0-4]\d|25[0-5])(\W+)([01]?\d\d?|2[0-4]\d|25[0-5])\W+(([01]?\d\d?|2[0-4]\d|25[0-5])\)?)$",
                        ErrorMessage = "RGB Color must match: '(d,d,d)', where d equals number 0-255")]
        public string RGBColor { get; set; }
    }
}
