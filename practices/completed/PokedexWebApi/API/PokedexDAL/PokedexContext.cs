using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Pokedex.DAL
{
    /// <summary>
    ///     Context with DB connection information.
    /// </summary>
    public class PokedexContext
    {
        public string ConnectionString { get; set; }

        public PokedexContext(string connectionString)
        {
            this.ConnectionString = connectionString;
        }

        public MySqlConnection GetConnection()
        {
            return new MySqlConnection(ConnectionString);
        }
    }
}
