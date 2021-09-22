using Pokedex.Core.DTO;
using Pokedex.DAL.Entities;
using System.Collections.Generic;

namespace Pokedex.DAL.Contracts
{
    /// <summary>
    ///     Repository interface for Pokemon entities.
    /// </summary>
    public interface IPokemonRepository
    {
        /// <summary>
        ///     Executes a DELETE query on 'Type_Pokemon'.
        /// </summary>
        /// <param name="numPokemon">Pokemon to remove Type from.</param>
        /// <param name="idType">Type to be removed from Pokemon records.</param>
        /// <returns>Affected rows.</returns>
        public int RemoveType(int numPokemon, int idType);

        /// <summary>
        ///     Executes a INSERT query into 'Type_Pokemon' with provided information.
        ///     It assigns Types to Pokemons on DB.
        /// </summary>
        /// <param name="pokemon">Entity which will be stored.</param>
        /// <returns>Affected rows.</returns>
        public void AddType(int numPokemon, int idType);

        /// <summary>
        ///     Executes a INSERT query into 'Pokemon' with provided information.
        /// </summary>
        /// <param name="pokemon">Entity which will be stored.</param>
        /// <returns>Affected rows.</returns>
        public void Create(Pokemon pokemon, int[] types);

        /// <summary>
        ///     Executes a SELECT * query on 'Pokemon'.
        /// </summary>
        /// <returns>List of current stored pokemons.</returns>
        public List<Pokemon> Read();

        /// <summary>
        ///     Executes a SELECT * query on 'Pokemon', where ID column matches
        ///     provided ID.
        /// </summary>
        /// <param name="idPokemon">ID to look for.</param>
        /// <returns>
        ///     null: If there is no pokemon with provided ID.<br/>
        ///     Pokemon with provided ID: If found.
        /// </returns>
        public Pokemon Read(int idPokemon);

        /// <summary>
        ///     Executes a UPDATE query on 'Pokemon', where ID column matches
        ///     provided ID.
        /// </summary>
        /// <param name="pokemon">Pokemon with new attributes.</param>
        /// <param name="numPokemon">ID to look for.</param>
        /// <returns>Affected rows.</returns>
        public int Update(int numPokemon, Pokemon pokemon, int[] types);

        /// <summary>
        ///     Executes a DELETE query on 'Pokemon', where ID column matches
        ///     provided ID.
        /// </summary>
        /// <param name="idPokemon">ID to look for.</param>
        /// <returns>Affected rows.</returns>
        public int Delete(int idPokemon);
    }
}
