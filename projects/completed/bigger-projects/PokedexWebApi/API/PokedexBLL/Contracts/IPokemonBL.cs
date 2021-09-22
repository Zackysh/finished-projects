using Newtonsoft.Json.Linq;
using Pokedex.Core.DTO;
using System.Collections.Generic;

namespace Pokedex.BLL.Contracts
{
    public interface IPokemonBL
    {
        /// <summary>
        ///     Remove specific type from one pokemon.
        /// </summary>
        /// <param name="numPokemon">Remove type from this pokemon</param>
        /// <param name="idType">Remove this type</param>
        public void RemoveType(int numPokemon, int idType);
        /// <summary>
        ///     Add specific type from one pokemon.
        /// </summary>
        /// <param name="numPokemon">Add type to this pokemon</param>
        /// <param name="idType">Add this type</param>
        public void AddType(int numPokemon, int idType);
        /// <summary>
        ///     Create new Pokemon.    
        /// </summary>
        /// <param name="pokemonDTO"></param>
        /// <returns>Created pokemon.</returns>
        public GETPokemonDTO Create(POSTPokemonDTO pokemonDTO);
        /// <summary>
        ///     Read all pokemons.
        /// </summary>
        /// <returns>List with all pokemons.</returns>
        public List<GETPokemonDTO> Read();
        /// <summary>
        ///     Read specific pokemon.
        /// </summary>
        /// <param name="id">Specific pokemon ID.</param>
        /// <returns>Pokemon if found.</returns>
        /// <returns>Null if not found.</returns>
        public GETPokemonDTO Read(int id);
        /// <summary>
        ///     Update specific pokemon.
        ///     It use reflection to change fields.
        /// </summary>
        /// <param name="id">Pokemon to update.</param>
        /// <param name="pokemonDTO">New fields.</param>
        /// <returns>Updated pokemon.</returns>
        public GETPokemonDTO Update(int id, JObject pokemonDTO);
        /// <summary>
        ///     Delete specific Pokemon.
        /// </summary>
        /// <param name="id">Specific Pokemon ID.</param>
        public void Delete(int id);
    }
}
