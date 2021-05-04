using System;

namespace PokedexAPI
{
    public class PokeType
    {
        public int id { get; }
        public string name { get; set; }
        public RGBColor Color { get; set; }

        public class RGBColor
        {
            private int r { get; }
            private int g { get; }
            private int b { get; }
            public RGBColor(int r, int g, int b)
            {
                this.r = r;
                this.g = g;
                this.b = b;
            }
        }
    }
}
