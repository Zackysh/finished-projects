using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using AutoMapper;
using MySql.Data.MySqlClient;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Pokedex.BLL.Contracts;
using Pokedex.Core.DTO;
using Pokedex.DAL.Contracts;
using Pokedex.DAL.Entities;

namespace Pokedex.BLL.Implementations
{
    public class PokemonBL : IPokemonBL
    {
        public IPokemonRepository PokemonRepository { get; set; }

        // Por falta de tiempo (examenes y temas personales) he decidido
        // no refactorizar el auto mapper, entre otras cosas

        public MapperConfiguration Config;
        public IMapper POSTPokemon_To_Pokemon_Mapper;
        public MapperConfiguration Config2;
        public IMapper POSTPokemon_To_GETPokemon_Mapper;
        public MapperConfiguration Config3;
        public IMapper Pokemon_To_GETPokemon_Mapper;
        public MapperConfiguration Config4;
        public IMapper PUTPokemon_To_GETPokemon_Mapper;
        public MapperConfiguration Config5;
        public IMapper PUTPokemon_To_Pokemon_Mapper;

        public PokemonBL(IPokemonRepository PokemonRepository)
        {
            this.PokemonRepository = PokemonRepository;
            // mapers config
            Config = new MapperConfiguration(cfg => { cfg.CreateMap<POSTPokemonDTO, Pokemon>().ForMember(dest => dest.types, act => act.Ignore()); });
            Config2 = new MapperConfiguration(cfg => { cfg.CreateMap<POSTPokemonDTO, GETPokemonDTO>(); });
            Config3 = new MapperConfiguration(cfg => { cfg.CreateMap<Pokemon, GETPokemonDTO>(); });
            Config4 = new MapperConfiguration(cfg => { cfg.CreateMap<PUTPokemonDTO, GETPokemonDTO>(); });
            Config5 = new MapperConfiguration(cfg => { cfg.CreateMap<PUTPokemonDTO, Pokemon>(); });
            // mappers
            POSTPokemon_To_Pokemon_Mapper = Config.CreateMapper();
            POSTPokemon_To_GETPokemon_Mapper = Config2.CreateMapper();
            Pokemon_To_GETPokemon_Mapper = Config3.CreateMapper();
            PUTPokemon_To_GETPokemon_Mapper = Config4.CreateMapper();
            PUTPokemon_To_Pokemon_Mapper = Config5.CreateMapper();
        }

        public void RemoveType(int numPokemon, int idType)
        {
            try
            {
                int affectedRows = PokemonRepository.RemoveType(numPokemon, idType);
                if (affectedRows < 1)
                {
                    throw new HttpErrorResponseDTO
                    {
                        StatusCode = 404,
                        ErrorMessage = "Provided Pokemon or Type might not exist or provided Pokemon doesn't have provided Type."
                    };
                }
            }
            catch (MySqlException e)
            {
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public void AddType(int numPokemon, int idType)
        {
            try
            {
                PokemonRepository.AddType(numPokemon, idType);
            }
            catch (MySqlException e)
            {
                if (e.Number == 1452) // sql cannot add, foreign key fails (type or pokemon not found)
                {
                    throw new HttpErrorResponseDTO // proper 404 Not Founs error code
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("Pokemon with ID {0} or Type with ID {1} doesn't exist.", numPokemon, idType)
                    };
                }
                if (e.Number == 1062) // sql duplicate entry error
                {
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 409,
                        ErrorMessage = string.Format("Pokemon with ID {0} has already Type with ID {1}", numPokemon, idType)
                    };
                }
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public GETPokemonDTO Create(POSTPokemonDTO pokemonDTO)
        {
            try
            {
                PokemonRepository.Create(POSTPokemon_To_Pokemon_Mapper.Map<POSTPokemonDTO, Pokemon>(pokemonDTO), pokemonDTO.Types);
                // as nothing blew up, we return successfully created pokemon
                return Read(pokemonDTO.Number);
            }
            catch (MySqlException e)
            {
                if (e.Number == 1062) // sql duplicate entry error
                {
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 409,
                        ErrorMessage = string.Format("There is already a pokemon with this name or number: {0}, {1}", pokemonDTO.Name, pokemonDTO.Number)
                    };
                }
                if (e.Number == 1452) // sql cannot add, foreign key fails (type or pokemon not found)
                {
                    throw new HttpErrorResponseDTO // proper 404 Not Founs error code
                    {
                        StatusCode = 404,
                        ErrorMessage = "One or more introduced types doesn't exist."
                    };
                }
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public List<GETPokemonDTO> Read()
        {
            try
            {
                List<GETPokemonDTO> pokemonsDTO = new();
                // as IMapper can't mapp List's ...
                foreach (var pokemon in PokemonRepository.Read())
                {
                    // we map each pokemon one by one
                    pokemonsDTO.Add(Pokemon_To_GETPokemon_Mapper.Map<Pokemon, GETPokemonDTO>(pokemon));
                }
                return pokemonsDTO;
            }
            catch (MySqlException e)
            {
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public GETPokemonDTO Read(int numPokemon)
        {
            try
            {
                Pokemon pokemon = PokemonRepository.Read(numPokemon);
                if (pokemon == null) // if no pokemon is found
                {
                    throw new HttpErrorResponseDTO // proper error code 404
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("No pokemon with Number = {0}", numPokemon)
                    };
                }
                return Pokemon_To_GETPokemon_Mapper.Map<Pokemon, GETPokemonDTO>(pokemon);
            }
            catch (MySqlException e)
            {
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public GETPokemonDTO Update(int numPokemon, JObject pokemonJson)
        {
            try
            {
                // fetch actual state of pokemon
                Pokemon currentPokemon = PokemonRepository.Read(numPokemon);
                if (currentPokemon == null) throw new HttpErrorResponseDTO // proper 404 error code
                {
                    StatusCode = 404,
                    ErrorMessage = string.Format("No pokemon with Number = {0}", numPokemon)
                };
                // update currentPokemon with pokemonJson
                foreach (string modelProp in currentPokemon.GetType().GetProperties().ToList().ConvertAll(p => p.Name).ToArray())
                {
                    JToken result = pokemonJson.GetValue(modelProp.ToLower());
                    Debug.WriteLine(result);
                    if (result != null)
                    {
                        PropertyInfo targetProp = currentPokemon.GetType().GetProperty(modelProp, BindingFlags.Public | BindingFlags.Instance);
                        if (targetProp != null && targetProp.CanWrite)
                        {
                            if (targetProp.PropertyType.Name == "Int32")
                                targetProp.SetValue(currentPokemon, int.Parse(result.ToString()), null);
                            else if (targetProp.PropertyType.Name == "Double")
                                targetProp.SetValue(currentPokemon, double.Parse(result.ToString()), null);
                            else if (targetProp.PropertyType.Name == "List`1")
                                continue;
                            else
                                targetProp.SetValue(currentPokemon, result.ToString(), null);
                        }
                    }
                }
                JToken typesToken = pokemonJson.GetValue("types");
                int[] types = typesToken == null ? null :
                    JsonConvert.DeserializeObject<int[]>(typesToken.ToString());
                // then send modified pokemon
                int affectedRows = PokemonRepository.Update(numPokemon, currentPokemon, types);
                if (affectedRows < 1) // if no record was affected, no pokemon was found
                {
                    throw new HttpErrorResponseDTO // proper 404 error code
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("No pokemon with Number = {0}", numPokemon)
                    };
                }

                JToken tokenNum = pokemonJson.GetValue("number");
                return Read(tokenNum != null ? int.Parse(tokenNum.ToString()) : numPokemon);
            }
            catch (MySqlException e)
            {
                if (e.Number == 1062) // sql duplicate entry error
                {
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 409,
                        ErrorMessage = "There is already a pokemon with this name or number"
                    };
                }
                if (e.Number == 1452) // sql cannot add, foreign key fails (type or pokemon not found)
                {
                    Debug.WriteLine(e);
                    throw new HttpErrorResponseDTO // proper 404 Not Founs error code
                    {
                        StatusCode = 404,
                        ErrorMessage = "One or more introduced types doesn't exist."
                    };
                }
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }

        public void Delete(int numPokemon)
        {
            try
            {
                int affectedRows = PokemonRepository.Delete(numPokemon);
                if (affectedRows < 1) // if no record was affected, no pokemon was found
                {
                    throw new HttpErrorResponseDTO // proper 404 error code
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("No pokemon with Number = {0}", numPokemon)
                    };
                }
            }
            catch (MySqlException e)
            {
                // Unexpected errors
                Debug.WriteLine(e);
                throw new HttpErrorResponseDTO
                {
                    StatusCode = 500,
                    ErrorMessage = "Internal Server Error"
                };
            }
        }
    }
}