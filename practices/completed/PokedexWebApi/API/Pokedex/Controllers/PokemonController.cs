using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Pokedex.BLL.Contracts;
using Pokedex.Core.DTO;
using Newtonsoft.Json.Linq;
using Pokedex.BLL.Helpers;
using System;
using System.Linq;
using Microsoft.AspNetCore.Authorization;
using System.Diagnostics;

namespace Pokedex.Controllers
{
    [Authorize]
    [ApiController]
    [Route("api/[controller]")]
    public class PokemonController : ControllerBase
    {
        public IPokemonBL PokemonBL;
        public PokemonController(IPokemonBL PokemonBL)
        {
            this.PokemonBL = PokemonBL;
        }


        [Produces("application/json")]
        [HttpPost("types")]
        public IActionResult AddType(int numPokemon, int idType)
        {
            try
            {
                PokemonBL.AddType(numPokemon, idType);
                return Ok("{message: \"Successful insert\"}");
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpDelete("types")]
        public IActionResult RemoveType(int numPokemon, int idType)
        {
            try
            {
                PokemonBL.RemoveType(numPokemon, idType);
                return Content("{\"message\": \"Successful removal\"}");
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpPost]
        public IActionResult Create(POSTPokemonDTO pokemonDTO)
        {
            try
            {
                return Ok(PokemonBL.Create(pokemonDTO));
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpGet]
        public IActionResult Read()
        {
            try
            {
                Debug.WriteLine("Hello");
                foreach (var header in Request.Headers)
                {
                    Debug.WriteLine(header);
                }
                return Ok(PokemonBL.Read());
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpGet("{numPokemon}")]
        public IActionResult Read(int numPokemon)
        {
            try
            {
                return Ok(PokemonBL.Read(numPokemon));
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpPut]
        public IActionResult Update(int numPokemon, PUTPokemonDTO placeHolder) // placeholder enables body on swagger
        {
            // read body
            JObject pokemonFromJSON = BodyHandler.ReadRequestBody(Request);
            // remove fields other than the requested ones
            BodyHandler.ConformJsonObjectToModel(pokemonFromJSON, typeof(PUTPokemonDTO)); // requested fields == any PokemonDTO fields
            // at least one CORRECT field is required
            if (pokemonFromJSON.Count < 1)
            {
                return HandleError(new HttpErrorResponseDTO // proper 409 Conflict error code
                {
                    StatusCode = 400,
                    ErrorMessage = "At least one child token is required, remember to write token names in lowercase."
                }, Response);
            }
            // all barriers passed, not even rambo would have believed it
            try
            {
                Debug.WriteLine("==============================");
                Debug.WriteLine(pokemonFromJSON);
                return Ok(PokemonBL.Update(numPokemon, pokemonFromJSON));
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpDelete("{numPokemon}")]
        public IActionResult Delete(int numPokemon)
        {
            try
            {
                PokemonBL.Delete(numPokemon);
                return Content("{\"message\": \"Successful removal\"}");
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        private ContentResult HandleError(HttpErrorResponseDTO e, HttpResponse response)
        {
            Response.ContentType = "application/json";
            if (e.StatusCode == 404)
            {
                response.StatusCode = 404;
                return Content($"{{ \"errors\": \"Not Found\", \"message\": \"{ e.ErrorMessage }\" }}");
            }
            if (e.StatusCode == 409)
            {
                response.StatusCode = 409;
                return Content($"{{" +
                    $"\"errors\": \"Duplicate entry\", " +
                    $"\"message\": \"{ e.ErrorMessage }\" }}");
            }
            if (e.StatusCode == 400)
            {
                response.StatusCode = 400;
                return Content($"{{" +
                    $"\"errors\": \"Bad Request\", " +
                    $"\"message\": \"{ e.ErrorMessage }\" }}");
            }
            response.StatusCode = 500;
            return Content("{\"errors\": \"Internal Server Error\"}");
        }
    }
}
