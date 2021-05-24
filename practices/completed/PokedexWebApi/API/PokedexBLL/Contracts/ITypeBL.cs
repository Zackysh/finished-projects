using Newtonsoft.Json.Linq;
using Pokedex.Core.DTO;
using System.Collections.Generic;

namespace Pokedex.BLL.Contracts
{
    public interface ITypeBL
    {
        /// <summary>
        ///     Create new Type.    
        /// </summary>
        /// <param name="tyèDTO"></param>
        /// <returns>Created type.</returns>
        public GETTypeDTO Create(POSTTypeDTO typeDTO);
        /// <summary>
        ///     Read all types.
        /// </summary>
        /// <returns>List with all types.</returns>
        public List<GETTypeDTO> Read();
        /// <summary>
        ///     Read specific type.
        /// </summary>
        /// <param name="id">Specific type ID.</param>
        /// <returns>Type if found.</returns>
        /// <returns>Null if not found.</returns>
        public GETTypeDTO Read(int id);
        /// <summary>
        ///     Update specific type.
        ///     It uses reflection to change new fields.
        /// </summary>
        /// <param name="id">Type to update.</param>
        /// <param name="typeDTO">New fields.</param>
        /// <returns>Updated type.</returns>
        public GETTypeDTO Update(int id, JObject typeDTO);
        /// <summary>
        ///     Delete specific Type.
        /// </summary>
        /// <param name="id">Specific Type ID.</param>
        public void Delete(int id);
    }
}
