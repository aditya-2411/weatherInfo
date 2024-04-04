Weather API Service

This project provides RESTful API endpoints to fetch weather data for a particular city. It leverages third-party weather APIs to retrieve weather forecasts.

Endpoints
1. signup for new user  
URL: /weather/signup  
Method: POST  
Body (required) : name, email, password, contactNumber

2. login for users  
URL: /weather/login  
Method: POST  
Body(required) : email,password  
Response: JWT Token

3. Get Weather Forecast Summary  
URL: /weather/summary  
Method: GET  
Authentication : Bearer (provide jwt token obtained from login endpoint to access this)  
Parameters(required)
city: The name of the city for which weather data is requested.

4. Get Weather Forecast hourly (will require premium account to access hourly data)  
URL: /weather/hourly  
Method: GET  
Authentication : Bearer (provide jwt token obtained from login endpoint to access this)  
Parameters:
city: The name of the city for which weather data is requested.

run : git clone https://github.com/aditya-2411/weatherInfo.git

NOTE - If the data of a particular city is not found, not found exception is thrown. Please try with european cities.
4th endpoint will return internal server error for now as 3rd party weather api doesn't provide hourly data in basic plan. Service logic can be found in code.
Also sharing the application.properties file, so you don't have to set env var.

