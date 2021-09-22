using MySql.Data.MySqlClient;
using Nancy.Json;
using Pokedex.Core.DTO;
using Pokedex.DAL.Contracts;
using Pokedex.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace Pokedex.DAL.Implementations
{
    public class PokemonRepository : IPokemonRepository
    {
        public PokedexContext Context { get; set; }
        public ITypeRepository TypeRepository { get; set; }

        public PokemonRepository(PokedexContext context, ITypeRepository TypeRepository)
        {
            this.Context = context;
            this.TypeRepository = TypeRepository;
        }

        public void Create(Pokemon pokemon, int[] types)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            // first delete possible references
            MySqlCommand cmd = new(string.Format(
                        "INSERT INTO pokemon VALUES ('{0}', {1}, '{2}', '{3}', '{4}', {5}, {6}, '{7}', '{8}')",
                        pokemon.Name, pokemon.Number, pokemon.Description, pokemon.Skill, pokemon.Category,
                        pokemon.Height.ToString("0.00", System.Globalization.CultureInfo.InvariantCulture),
                        pokemon.Weight.ToString("0.00", System.Globalization.CultureInfo.InvariantCulture),
                        pokemon.Sex, pokemon.Image), conn);
            cmd.ExecuteNonQuery();
            for (int i = 0; i < types.Length; i++)
                AddType(pokemon.Number, types[i]);
        }

        public List<Pokemon> Read()
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new("SELECT * FROM pokemon", conn);
            using var reader = cmd.ExecuteReader();
            List<Pokemon> pokemons = new();
            while (reader.Read())
            {
                Pokemon pokemon = new()
                {
                    Name = reader["name"].ToString(),
                    Number = (int)reader["number"],
                    Description = reader["description"].ToString(),
                    Skill = reader["skill"].ToString(),
                    Category = reader["category"].ToString(),
                    Height = Convert.ToDouble(reader["height"]),
                    Weight = Convert.ToDouble(reader["weight"]),
                    Sex = reader["sex"].ToString(),
                    Image = reader["image"].ToString()
                };
                pokemon.types = TypeRepository.ReadTypesFromPokemon(pokemon.Number);
                pokemons.Add(pokemon);
            }
            return pokemons;
        }



        public Pokemon Read(int numPokemon)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(string.Format("SELECT * FROM pokemon WHERE number = {0}", numPokemon), conn);
            using var reader = cmd.ExecuteReader();
            Pokemon pokemon = null;
            while (reader.Read())
            {
                pokemon = new()
                {
                    Name = reader["name"].ToString(),
                    Number = (int)reader["number"],
                    Description = reader["description"].ToString(),
                    Skill = reader["skill"].ToString(),
                    Category = reader["category"].ToString(),
                    Height = Convert.ToDouble(reader["height"]),
                    Weight = Convert.ToDouble(reader["weight"]),
                    Sex = reader["sex"].ToString(),
                    Image = reader["image"].ToString()
                };
                pokemon.types = TypeRepository.ReadTypesFromPokemon(pokemon.Number);
            }
            return pokemon;
        }

        public int Update(int numPokemon, Pokemon pokemon, int[] types)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(string.Format(
                "UPDATE pokemon SET name='{0}', `number`={1}, description='{2}', skill='{3}', category='{4}', " +
                "height={5}, weight={6}, sex='{7}', image='{8}' WHERE number={9};",
                pokemon.Name, pokemon.Number, pokemon.Description, pokemon.Skill, pokemon.Category,
                pokemon.Height.ToString("0.00", System.Globalization.CultureInfo.InvariantCulture),
                pokemon.Weight.ToString("0.00", System.Globalization.CultureInfo.InvariantCulture),
                pokemon.Sex, pokemon.Image, numPokemon), conn);
            int affRows = cmd.ExecuteNonQuery();
            if (types != null)
            {
                RemoveAllTypes(pokemon.Number);
                for (int i = 0; i < types.Length; i++)
                    AddType(pokemon.Number, types[i]);
            }
            return affRows;
        }

        public int Delete(int numPokemon)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            // first delete possible references
            MySqlCommand cmd = new(string.Format("DELETE FROM type_pokemon WHERE num_pokemon = {0};", numPokemon), conn);
            cmd.ExecuteNonQuery();

            cmd = new(string.Format("DELETE FROM pokemon WHERE number={0};", numPokemon), conn);
            return cmd.ExecuteNonQuery();
        }

        public void AddType(int numPokemon, int idType)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            // first delete possible references
            MySqlCommand cmd = new(string.Format(
                        "INSERT INTO type_pokemon VALUES ({0}, {1});", numPokemon, idType), conn);
            cmd.ExecuteNonQuery();
        }

        public int RemoveType(int numPokemon, int idType)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();

            MySqlCommand cmd = new(string.Format(
                "DELETE FROM type_pokemon WHERE num_pokemon = {0} AND id_type = {1};", numPokemon, idType), conn);
            return cmd.ExecuteNonQuery();
        }

        public int RemoveAllTypes(int numPokemon)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();

            MySqlCommand cmd = new(string.Format(
                "DELETE FROM type_pokemon WHERE num_pokemon = {0};", numPokemon), conn);
            return cmd.ExecuteNonQuery();
        }
    }
}
