using Pokedex.Core.DTO;
using Pokedex.DAL.Entities;
using System.Collections.Generic;

namespace Pokedex.DAL.Contracts
{
    /// <summary>
    ///     Repository interface for Type Type entities.
    /// </summary>
    public interface ITypeRepository
    {
        /// <summary>
        ///     Executes a INSERT query into 'Type' with provided information.
        /// </summary>
        /// <param name="type">Entity which will be stored.</param>
        /// <returns>Affected rows.</returns>
        public int Create(Type type);

        public List<GETTypeDTO> ReadTypesFromPokemon(int number);

        /// <summary>
        ///     Executes a SELECT * query on 'Type'.
        /// </summary>
        /// <returns>List of current stored type.</returns>
        public List<Type> Read();

        /// <summary>
        ///     Executes a SELECT * query on 'Type', where ID column matches
        ///     provided ID.
        /// </summary>
        /// <param name="idType">ID to look for.</param>
        /// <returns>
        ///     null: If there is no type with provided ID.<br/>
        ///     Type with provided ID: If found.
        /// </returns>
        public Type Read(int idType);

        /// <summary>
        ///     Executes a UPDATE query on 'Type', where ID column matches
        ///     provided ID.
        /// </summary>
        /// <param name="type">Type with new attributes.</param>
        /// <param name="idType">ID to look for.</param>
        /// <returns>Affected rows.</returns>
        public int Update(int idType, Type type);

        /// <summary>
        ///     Executes a DELETE query on 'Type', where ID column matches
        ///     provided ID.
        /// </summary>
        /// <param name="idType">ID to look for.</param>
        /// <returns>Affected rows.</returns>
        public int Delete(int idType);
    }
}
