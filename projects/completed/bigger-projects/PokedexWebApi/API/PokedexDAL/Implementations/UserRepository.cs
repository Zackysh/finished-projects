using MySql.Data.MySqlClient;
using Pokedex.DAL.Contracts;
using Pokedex.DAL.Entities;

namespace Pokedex.DAL.Implementations
{
    public class UserRepository : IUserRepository
    {
        public PokedexContext Context { get; set; }
        public UserRepository(PokedexContext context)
        {
            this.Context = context;
        }

        public User Login(User user)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(
                string.Format("select * from user where " +
                              "username = '{0}'",
                              user.Username), conn);
            using var reader = cmd.ExecuteReader();
            int count = 0;
            User userFromDB = new();
            while (reader.Read())
            {
                count++;
                userFromDB = new()
                {
                    Username = reader["username"].ToString(),
                    Password = reader["password"].ToString()
                };
            }
            return count == 1 ? userFromDB : null;
        }

        public int Register(User user)
        {
            using MySqlConnection conn = Context.GetConnection();
            conn.Open();
            MySqlCommand cmd = new(string.Format("INSERT INTO `user` (username, password) VALUES('{0}', '{1}');",
                        user.Username, user.Password), conn);
            return cmd.ExecuteNonQuery();
        }
    }
}
