using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.Linq;
using System.Reflection;
using AutoMapper;
using MySql.Data.MySqlClient;
using Newtonsoft.Json.Linq;
using Pokedex.BLL.Contracts;
using Pokedex.Core.DTO;
using Pokedex.DAL.Contracts;
using Pokedex.DAL.Entities;

namespace Pokedex.BLL.Implementations
{
    public class TypeBL : ITypeBL
    {
        public ITypeRepository TypeRepository { get; set; }

        public MapperConfiguration Config;
        public IMapper POSTType_To_Type_Mapper;
        public MapperConfiguration Config2;
        public IMapper POSTType_To_GETType_Mapper;
        public MapperConfiguration Config3;
        public IMapper Type_To_GETType_Mapper;
        public MapperConfiguration Config4;
        public IMapper PUTType_To_GETType_Mapper;
        public MapperConfiguration Config5;
        public IMapper PUTType_To_Type_Mapper;

        public TypeBL(ITypeRepository TypeRepository)
        {
            this.TypeRepository = TypeRepository;
            // mapers config
            Config = new MapperConfiguration(cfg => { cfg.CreateMap<POSTTypeDTO, Type>(); });
            Config2 = new MapperConfiguration(cfg => { cfg.CreateMap<POSTTypeDTO, GETTypeDTO>(); });
            Config3 = new MapperConfiguration(cfg => { cfg.CreateMap<Type, GETTypeDTO>(); });
            Config4 = new MapperConfiguration(cfg => { cfg.CreateMap<PUTTypeDTO, GETTypeDTO>(); });
            Config5 = new MapperConfiguration(cfg => { cfg.CreateMap<PUTTypeDTO, Type>(); });
            // mappers
            POSTType_To_Type_Mapper = Config.CreateMapper();
            POSTType_To_GETType_Mapper = Config2.CreateMapper();
            Type_To_GETType_Mapper = Config3.CreateMapper();
            PUTType_To_GETType_Mapper = Config4.CreateMapper();
            PUTType_To_Type_Mapper = Config5.CreateMapper();
        }

        public GETTypeDTO Create(POSTTypeDTO typeDTO)
        {
            try
            {
                // map Type DTO to Type Entity, then it's sent to DAL
                TypeRepository.Create(POSTType_To_Type_Mapper.Map<POSTTypeDTO, Type>(typeDTO));
            }
            catch (MySqlException e)
            {
                if (e.Number == 1062) // sql duplicate entry error
                {
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 409,
                        ErrorMessage = string.Format("There is already a type with this Name: {0}", typeDTO.Name)
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
            // as nothing blew up, we return successfully created type
            return POSTType_To_GETType_Mapper.Map<POSTTypeDTO, GETTypeDTO>(typeDTO);
        }

        public List<GETTypeDTO> Read()
        {
            try
            {
                List<GETTypeDTO> typesDTO = new();
                // as IMapper can't mapp List's ...
                foreach (var type in TypeRepository.Read())
                {
                    // we map each type one by one
                    typesDTO.Add(Type_To_GETType_Mapper.Map<Type, GETTypeDTO>(type));
                }
                return typesDTO;
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

        public GETTypeDTO Read(int idType)
        {
            try
            {
                Type type = TypeRepository.Read(idType);
                if (type == null) // if no type is found
                {
                    throw new HttpErrorResponseDTO // proper error code 404
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("No type with ID = {0}", idType)
                    };
                }
                return Type_To_GETType_Mapper.Map<Type, GETTypeDTO>(type);
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

        public GETTypeDTO Update(int idType, JObject typeJson)
        {
            try
            {
                // fetch actual state of type
                Type currentType = TypeRepository.Read(idType);
                if (currentType == null) throw new HttpErrorResponseDTO // proper 404 error code
                {
                    StatusCode = 404,
                    ErrorMessage = string.Format("No type with ID = {0}", idType)
                };
                // update currentType with typeJson
                foreach (string modelProp in currentType.GetType().GetProperties().ToList().ConvertAll(p => p.Name).ToArray())
                {
                    JToken result = typeJson.GetValue(modelProp.ToLower());
                    if (result != null)
                    {
                        PropertyInfo targetProp = currentType.GetType().GetProperty(modelProp, BindingFlags.Public | BindingFlags.Instance);
                        if (targetProp != null && targetProp.CanWrite)
                        {
                            if (targetProp.PropertyType.Name == "Int32")
                                targetProp.SetValue(currentType, int.Parse(result.ToString()), null);
                            else
                                targetProp.SetValue(currentType, result.ToString(), null);
                        }
                    }
                }
                // then send modified type
                int affectedRows = TypeRepository.Update(idType, currentType);
                if (affectedRows < 1) // if no record was affected, no type was found
                {
                    throw new HttpErrorResponseDTO // proper 404 error code
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("No type with ID = {0}", idType)
                    };
                }
                return Type_To_GETType_Mapper.Map<Type, GETTypeDTO>(currentType);
            }
            catch (MySqlException e)
            {
                if (e.Number == 1062) // sql duplicate entry error
                {
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 409,
                        ErrorMessage = "There is already a type with this name or number"
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

        public void Delete(int idType)
        {
            try
            {
                int affectedRows = TypeRepository.Delete(idType);
                if (affectedRows < 1) // if no record was affected, no type was found
                {
                    throw new HttpErrorResponseDTO // proper 404 error code
                    {
                        StatusCode = 404,
                        ErrorMessage = string.Format("No type with ID = {0}", idType)
                    };
                }
            }
            catch (MySqlException e)
            {
                if (e.Number == 1451) // sql cannot delete parent row, foreign key fails
                {
                    throw new HttpErrorResponseDTO // proper 409 Conflict error code
                    {
                        StatusCode = 403,
                        ErrorMessage = "You can't delete this type since one or more pokemons reference it"
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

        private static void ChangePropertiesToLowerCase(JObject jsonObject)
        {
            foreach (var property in jsonObject.Properties().ToList())
            {
                if (property.Value.Type == JTokenType.Object) // replace property names in child object
                    ChangePropertiesToLowerCase((JObject)property.Value);

                if (property.Value.Type == JTokenType.Array)
                {
                    var arr = JArray.Parse(property.Value.ToString());
                    foreach (var pr in arr)
                    {
                        ChangePropertiesToLowerCase((JObject)pr);
                    }

                    property.Value = arr;
                }

                property.Replace(new JProperty(property.Name.ToLower(CultureInfo.InvariantCulture), property.Value)); // properties are read-only, so we have to replace them
            }
        }

    }
}