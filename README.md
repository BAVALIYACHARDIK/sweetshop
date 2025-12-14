# SweetShop

Project details
---------------

Overview
- Full-stack demo composed of a Spring Boot backend and a React (Vite) frontend.

Backend (Spring Boot)
- Language / framework: Java, Spring Boot
- Data store: MongoDB (Spring Data MongoDB)
- Package roots:
  - Controllers: `src/main/java/com/sweetshop/controller`
  - Services: `src/main/java/com/sweetshop/service`
  - DTOs: `src/main/java/com/sweetshop/dto`
  - Models: `src/main/java/com/sweetshop/model`
  - Repositories: `src/main/java/com/sweetshop/repository`

- Key backend files:
  - `AuthController.java` — REST endpoints under `/api/auth`.
  - `AuthService.java` — registration, login and JWT generation logic.
  - `UserRepository.java` — Mongo repository for `User` documents.
  - `AuthResponse.java`, `RegisterRequest.java`, `LoginRequest.java` — DTO classes.

- API endpoints (request / response shapes)
  - POST `/api/auth/register`
    - Request JSON: `{ "name": string, "email": string, "password": string }`
    - Response JSON: `AuthResponse` with fields `{ id, name, email, password, token, message }`.
  - POST `/api/auth/login`
    - Request JSON: `{ "email": string, "password": string }`
    - Response JSON: `AuthResponse` with fields `{ id, name, email, password, token, message }`.

- Data model (User)
  - `User` document fields: `id` (String), `name` (String), `email` (String), `password` (String).

- Auth and token
  - JWTs are generated in `AuthService.generateToken(subject)` and include `sub`, `iat`, `exp` claims.
  - Token expiration is set to 1 day in the current implementation (see `AuthService`).

Frontend (React + Vite)
- Location: `frontend/`
- Key files and behavior:
  - `frontend/src/App.jsx` — client routes and router setup (React Router).
  - `frontend/src/services/api.js` — functions calling backend endpoints (`registeruser`, `loginuser`) and `isTokenValid()` helper.
  - `frontend/src/pages/Register.jsx`, `frontend/src/pages/Login.jsx` — auth forms; they store `token` and `user` in `localStorage` on success.
  - `frontend/src/pages/Sweet.jsx` — search UI placeholder (header, search bar, filter modal).

- Frontend routes
  - `/` → Register (landing)
  - `/login` → Login
  - `/register` → Register
  - `/auth/register` → Register (compatibility)

- Local storage
  - `token` saved under `localStorage` key `token` when login/register returns a token.
  - `user` saved as JSON string under `localStorage` key `user`.

Build and run
- Backend (PowerShell):
```powershell
cd .\
.\gradlew.bat bootRun
```

- Frontend (PowerShell):
```powershell
cd frontend
npm install
npm run dev
```


My Ai Usage
- i used ai(github copilot) in creating backend spring boot structure and fronted react js structure so the data flow can be easy (and it take care of SOLID principle indirectly ) and scaling them become easy when we use many api handlers
- takes the help for api endpoint 
- takes suggestion on how to use JWT token in fronted , use of lombok (for reducing boilerplate code)
- and for creating ui form and its some css 
- for writing the test code for ensuring correct data flow





Notes
- The project uses a hard-coded JWT secret in `AuthService` and stores passwords as plain text in `User`. The code and file locations above reflect the current repository state and implementation.

.\gradlew.bat bootRun
