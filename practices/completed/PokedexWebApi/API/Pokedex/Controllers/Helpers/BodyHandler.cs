using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.IO;
using System.Linq;
using System.Text;

namespace Pokedex.BLL.Helpers
{
    public class BodyHandler
    {
        /// <summary>
        ///     This method read given Request Body and parse it
        ///     to a JSON Object from Newtonsoft.
        /// </summary>
        /// <param name="Request"></param>
        /// <returns>Body as JObject</returns>
        public static JObject ReadRequestBody(HttpRequest Request)
        {
            Request.Body.Position = 0;
            string bodyJson;
            using (var reader = new StreamReader(
                    Request.Body,
                    encoding: Encoding.UTF8,
                    detectEncodingFromByteOrderMarks: false,
                    bufferSize: 512,
                    leaveOpen: true))
            {
                bodyJson = reader.ReadToEndAsync().Result;
                Request.Body.Position = 0;
            }
            // --------- Deserialize to JSON Object ---------
            return (JObject)JsonConvert.DeserializeObject(bodyJson, typeof(JObject));
        }

        /// <summary>
        ///     Author: AdriGB - Reflection is epic
        ///     Mapp a JObject properties into given Type ones,
        ///     it makes use of reflection.
        /// </summary>
        /// <param name="jsonObject">JObject to Map.</param>
        /// <param name="model">Type into which JObject will be mapped.</param>
        public static void ConformJsonObjectToModel(JObject jsonObject, Type model)
        {
            // having correctProps from given model
            string[] correctProps = model.GetProperties().ToList().ConvertAll(p => p.Name.ToLower()).ToArray();
            // get bad props from JSON
            var badProps = jsonObject.Properties().Where(prop => !correctProps.Contains(prop.Name));
            foreach (string prop in badProps.ToList().ConvertAll(p => p.Name).ToArray())
            {
                // delete bad props
                jsonObject.Remove(prop);
            }
        }
    }
}
