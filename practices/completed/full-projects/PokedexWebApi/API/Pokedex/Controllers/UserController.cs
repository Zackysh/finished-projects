using BC = BCrypt.Net.BCrypt;
using Pokedex.Core.DTO;
using Pokedex.BLL.Contracts;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using System.IdentityModel.Tokens.Jwt;
using System;
using System.Security.Claims;
using System.Diagnostics;

namespace Pokedex.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        public IUserBL UserBL { get; set; }
        private readonly IConfiguration configuration;
        public UserController(IUserBL UserBL, IConfiguration configuration)
        {
            this.configuration = configuration;
            this.UserBL = UserBL;
        }

        [HttpPost("login")]
        [Produces("application/json")]
        [AllowAnonymous]
        public IActionResult Login(UserDTO userDTO)
        {
            try
            {
                Debug.WriteLine("login...");
                if (BC.Verify(userDTO.Password, UserBL.Login(userDTO).Password))
                {
                    var token = GenerarTokenJWT(userDTO);
                    Response.Headers.Add("token", token);
                    Response.Headers.Add("content-type", "application/json");
                    return Content("{\"message\": \"Successful login!\", \"token\": \"" + token + "\"}");
                }
                else
                {
                    return Unauthorized();
                }
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        [HttpPost("register")]
        [Produces("application/json")]
        [AllowAnonymous]
        public IActionResult Register(UserDTO userDTO)
        {
            try
            {
                userDTO.Password = BC.HashPassword(userDTO.Password);
                if (UserBL.Register(userDTO))
                {
                    Response.Headers.Add("token", GenerarTokenJWT(userDTO));
                    return Content("{\"message\": \"Successful register!\"}");
                }
                else
                {
                    return Unauthorized();
                }
            }
            catch (HttpErrorResponseDTO e)
            {
                return HandleError(e, Response);
            }
        }

        private string GenerarTokenJWT(UserDTO userDTO)
        {
            // Token
            var _symmetricSecurityKey = new SymmetricSecurityKey(
                    Encoding.UTF8.GetBytes(configuration["JWT:SecretKey"])
                );
            var _signingCredentials = new SigningCredentials(
                    _symmetricSecurityKey, SecurityAlgorithms.HmacSha256
                );
            var _Header = new JwtHeader(_signingCredentials);
            // Token Claims
            var _Claims = new[] {
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim("name", userDTO.Username),
            };
            // Create tokens Payload
            var _Payload = new JwtPayload(
                    issuer: configuration["JWT:Issuer"],
                    audience: configuration["JWT:Audience"],
                    claims: _Claims,
                    notBefore: DateTime.UtcNow,
                    // Expires in 24 horas.
                    expires: DateTime.UtcNow.AddHours(24)
                );
            // Generate TOKEN
            var _Token = new JwtSecurityToken(
                    _Header,
                    _Payload
                );
            return new JwtSecurityTokenHandler().WriteToken(_Token);
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
