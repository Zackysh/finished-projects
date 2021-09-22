using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.AspNetCore.Identity;
using Microsoft.OpenApi.Models;
using Pokedex.BLL.Implementations;
using Pokedex.BLL.Contracts;
using Pokedex.DAL;
using Pokedex.DAL.Contracts;
using Pokedex.DAL.Implementations;
using Microsoft.AspNetCore.Cors;

using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using System.Diagnostics;

namespace Pokedex
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers();
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "Pokedex", Version = "v1" });
            });
            services.AddCors(o => o.AddPolicy("MyPolicy", builder =>
            {
                builder.AllowAnyOrigin()
                       .AllowAnyMethod()
                       .AllowAnyHeader();
            }));
            // token auth configuration
            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
                .AddJwtBearer(options =>
                {
                    options.TokenValidationParameters = new TokenValidationParameters()
                    {
                        ValidateIssuer = true,
                        ValidateAudience = true,
                        ValidateLifetime = true,
                        ValidateIssuerSigningKey = true,
                        ValidIssuer = Configuration["JWT:Issuer"],
                        ValidAudience = Configuration["JWT:Audience"],
                        IssuerSigningKey = new SymmetricSecurityKey(
                            Encoding.UTF8.GetBytes(Configuration["JWT:SecretKey"])
                        )
                    };
                });
            // controller services
            services.AddScoped<IUserBL, UserBL>();
            services.AddScoped<IPokemonBL, PokemonBL>();
            services.AddScoped<ITypeBL, TypeBL>();
            // business logic services
            services.AddScoped<IUserRepository, UserRepository>();
            services.AddScoped<IPokemonRepository, PokemonRepository>();
            services.AddScoped<ITypeRepository, TypeRepository>();
            // data access configuration / services
            services.Add(new ServiceDescriptor(typeof(PokedexContext), new PokedexContext(Configuration.GetConnectionString("pokedb"))));
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "Pokedex v1"));
            }

            app.UseHttpsRedirection();
            app.UseAuthentication();


            app.Use((context, next) =>
            {
                context.Request.EnableBuffering();
                return next();
            });

            app.UseCors("MyPolicy");

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
