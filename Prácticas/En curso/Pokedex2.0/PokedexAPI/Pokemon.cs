using System;
using System.Collections.Generic;

namespace PokedexAPI
{
    /**
     * @param Id int
     *
     */
    public class Pokemon
    {
        public int Id { get; }
        public string Name { get; set; }
        public int Number { get; set; }
        public string Description { get; set; }
        public string Skill { get; set; }
        public string Category { get; set; }
        public double Height { get; set; }
        public double Weight { get; set; }
        public string Sex { get; set; }
        public List<PokeType> Types { get; set; }
        public int[] BaseAttributes { get; set; }
    }
}
