using MySql.Data.MySqlClient;
using Pokedex.Core.DTO;
using Pokedex.DAL.Contracts;
using System.Collections.Generic;
using System.Diagnostics;
using Type = Pokedex.DAL.Entities.Type;

namespace Pokedex.DAL.Implementations
{
    public class TypeRepository : ITypeRepository
    {
        public PokedexContext Context { get; set; }
        public TypeRepository(PokedexContext context)
        {
            this.Context = context;
        }

        public int Create(Type type)
        {
            using MySqlConnection conn = Context.GetConnection();
            Debug.WriteLine(type);
            conn.Open();
            MySqlCommand cmd = new(string.Format(
                        "INSERT INTO type VALUES (null, '{0}', '{1}');", type.Name, type.RGBColor), conn);
            return cmd.ExecuteNonQuery();
        }

        public List<GETTypeDTO> ReadTypesFromPokemon(int number)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new($"select type.* from type join type_pokemon on type_pokemon.id_type = type.id_type and num_pokemon = {number};", conn);
            using var reader = cmd.ExecuteReader();
            List<GETTypeDTO> types = new();
            while (reader.Read())
            {
                types.Add(new GETTypeDTO
                {
                    IdType = (int)reader["id_type"],
                    Name = reader["name"].ToString(),
                    RGBColor = reader["rgb_color"].ToString()
                });
            }
            return types;
        }

        public List<Type> Read()
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new("SELECT * FROM type", conn);
            using var reader = cmd.ExecuteReader();
            List<Type> types = new();
            while (reader.Read())
            {
                types.Add(new Type
                {
                    IdType = (int)reader["id_type"],
                    Name = reader["name"].ToString(),
                    RGBColor = reader["rgb_color"].ToString()
                });
            }
            return types;
        }

        public Type Read(int idType)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(string.Format("SELECT * FROM type WHERE id_type = {0}", idType), conn);
            using var reader = cmd.ExecuteReader();
            Type type = null;
            while (reader.Read())
            {
                type = new Type
                {
                    IdType = (int)reader["id_type"],
                    Name = reader["name"].ToString(),
                    RGBColor = reader["rgb_color"].ToString()
                };
            }
            return type;
        }

        public int Update(int idType, Type type)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(string.Format(
                "UPDATE type SET name = '{0}', `rgb_color`= '{1}' WHERE id_type = {2};",
                type.Name, type.RGBColor, idType), conn);
            return cmd.ExecuteNonQuery();
        }

        public int Delete(int idType)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(string.Format("DELETE FROM type WHERE id_type = {0};", idType), conn);
            return cmd.ExecuteNonQuery();
        }
    }
}
