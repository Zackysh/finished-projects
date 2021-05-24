using MySql.Data.MySqlClient;
using System;
using System.Net.Http;

namespace Pokedex.Core.DTO
{
    public class HttpErrorResponseDTO : Exception
    {
        public string ErrorMessage { get; set; }
        public int StatusCode { get; set; }
    }
}