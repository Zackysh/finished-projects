using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Pokedex.BLL.Contracts;
using Pokedex.Core.DTO;
using Newtonsoft.Json.Linq;
using Pokedex.BLL.Helpers;
using System;
using System.Linq;

namespace Pokedex.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TypeController : ControllerBase
    {
        public ITypeBL TypeBL;
        public TypeController(ITypeBL TypeBL)
        {
            this.TypeBL = TypeBL;
        }

        [Produces("application/json")]
        [HttpPost]
        public IActionResult Create(POSTTypeDTO typeDTO)
        {
            try
            {
                return Ok(TypeBL.Create(typeDTO));
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
                return Ok(TypeBL.Read());
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpGet("{idType}")]
        public IActionResult Read(int idType)
        {
            try
            {
                return Ok(TypeBL.Read(idType));
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Consumes("application/json")]
        [Produces("application/json")]
        [HttpPut]
        public IActionResult Update(int idType, PUTTypeDTO placeHolder) // placeholder enables body on swagger
        {
            // read body
            JObject typeFromJSON = BodyHandler.ReadRequestBody(Request);
            // remove fields other than the requested ones
            BodyHandler.ConformJsonObjectToModel(typeFromJSON, typeof(GETTypeDTO)); // requested fields == any TypeDTO fields
            // at least one CORRECT field is required
            if (typeFromJSON.Count < 1)
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
                return Ok(TypeBL.Update(idType, typeFromJSON));
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [Produces("application/json")]
        [HttpDelete("{idType}")]
        public IActionResult Delete(int idType)
        {
            try
            {
                TypeBL.Delete(idType);
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
            response.StatusCode = 500;
            return Content("{\"errors\": \"Internal Server Error\"}");
        }
    }
}
