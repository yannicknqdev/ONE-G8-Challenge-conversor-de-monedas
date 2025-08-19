# 💱 CoinFluxCraft - Conversor de Monedas Java

<div align="center">

![Java](https://img.shields.io/badge/Java-24%2B-orange?style=for-the-badge&logo=java)
![API](https://img.shields.io/badge/API-ExchangeRate--API-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)
**Un conversor de monedas profesional con 18 monedas soportadas, historial avanzado y análisis estadísticos**

[🚀 Características](#-características) • [📦 Instalación](#-instalación) • [🎯 Uso](#-uso) • [📊 Diagramas](#-diagramas) • [🛠️ Desarrollo](#️-desarrollo)

</div>

---

## 📋 **Tabla de Contenidos**

- [🌟 Características Principales](#-características-principales)
- [🏗️ Arquitectura](#️-arquitectura)
- [📦 Instalación y Configuración](#-instalación-y-configuración)
- [🎯 Guía de Uso](#-guía-de-uso)
- [💱 Monedas Soportadas](#-monedas-soportadas)
- [🛠️ Desarrollo y Contribución](#️-desarrollo-y-contribución)
- [📚 Documentación Técnica](#-documentación-técnica)
- [🏆 Créditos](#-créditos)

---

## 🌟 **Características Principales**

### 🎯 **Modo Dual de Operación**
- **📱 Modo Básico**: Interfaz simple con 10 conversiones predefinidas
- **🚀 Modo Avanzado**: 18 monedas, historial, estadísticas y análisis

### 💰 **Sistema de Conversiones**
- ✅ **18 monedas mundiales** con tasas en tiempo real
- ✅ **Precisión matemática** usando BigDecimal
- ✅ **Validación robusta** de entradas y datos
- ✅ **Símbolos nativos** para cada moneda (€, £, ¥, ₩, etc.)

### 📊 **Historial y Analytics**
- ✅ **Persistencia automática** en archivo local
- ✅ **Marcas de tiempo** con zonas horarias
- ✅ **Búsqueda avanzada** por fecha, moneda o cantidad
- ✅ **Estadísticas de uso** y análisis de patrones
- ✅ **Conversiones favoritas** y sugerencias inteligentes

### 🌐 **Integración API**
- ✅ **ExchangeRate-API** para datos en tiempo real
- ✅ **HttpClient nativo** de Java 11+
- ✅ **Manejo de errores** robusto
- ✅ **Configuración segura** con variables de entorno

---

## 🏗️ **Arquitectura**

### 📐 **Patrón de Capas**

```
🎨 Capa de Presentación
├── ConversorMenu.java (Modo Básico)
└── AdvancedConversorMenu.java (Modo Avanzado)

⚙️ Capa de Lógica de Negocio  
├── ConversionService.java (Lógica principal)
├── CurrencyCalculator.java (Cálculos precisos)
└── AdvancedConversionHistory.java (Gestión de historial)

📊 Capa de Datos
├── ExchangeRateResponse.java
├── ConversionResult.java
├── SupportedCurrency.java (Enum)
└── ConversionRecord.java

🌐 Capa de Acceso Externo
├── ApiClient.java (HTTP Client)
├── JsonParser.java (Gson)
└── ApiConfig.java (Configuración)

💾 Capa de Persistencia
└── conversion_history.txt (Almacenamiento local)
```

### 🔄 **Flujo Principal**
1. **Usuario** selecciona modo y conversión
2. **Validación** de monedas y cantidades
3. **API Call** a ExchangeRate-API
4. **Procesamiento** con Gson y BigDecimal
5. **Persistencia** en historial local
6. **Presentación** de resultados formateados

---

## 📦 **Instalación y Configuración**

### 🔧 **Requisitos del Sistema**
- ☕ **Java 17 o superior**
- 🌐 **Conexión a Internet** (para API calls)
- 💾 **10MB de espacio libre** (para JAR y historial)

### ⚙️ **Configuración Paso a Paso**

#### 1️⃣ **Obtener API Key**
```bash
# 1. Visita https://app.exchangerate-api.com/sign-up
# 2. Regístrate gratis (1500 requests/mes)
# 3. Copia tu API key del dashboard
```

#### 2️⃣ **Configurar Variable de Entorno**
```bash
# Linux/Mac
export EXCHANGE_RATE_API_KEY="tu_api_key_aquí"

# Windows (CMD)
set EXCHANGE_RATE_API_KEY=tu_api_key_aquí

# Windows (PowerShell)
$env:EXCHANGE_RATE_API_KEY="tu_api_key_aquí"
```

#### 3️⃣ **Clonar y Configurar Proyecto**
```bash
# Clonar repositorio
git clone https://github.com/yannicknqdev/ONE-G8-Challenge-conversor-de-monedas.git
cd ONE-G8-Challenge-conversor-de-monedas

# Verificar estructura
ls -la src/main/java/com/alura/coinfluxcraft/

# Descargar Gson (si no está incluido)
wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
```

#### 4️⃣ **Compilar y Ejecutar**
```bash
# Compilar con Gson en classpath
javac -cp ".:gson-2.10.1.jar" src/main/java/com/alura/coinfluxcraft/**/*.java

# Ejecutar aplicación
java -cp ".:gson-2.10.1.jar:src" main.java.com.alura.coinfluxcraft.Main
```

---

## 🎯 **Guía de Uso**

### 🚀 **Inicio Rápido**

Al ejecutar la aplicación, verás:

```
🌟 BIENVENIDO AL CONVERSOR DE MONEDAS 🌟

Seleccione el modo de uso:
1) 📱 Modo Básico (menú original)
2) 🚀 Modo Avanzado (con historial y estadísticas)

Ingrese su opción (1 o 2): 
```

### 📱 **Modo Básico**

Interfaz simple con 10 conversiones predefinidas:

```
*************************************
Sea bienvenido/a al Conversor de Moneda =]

1) Dólar =>> Peso argentino
2) Peso argentino =>> Dólar
3) Dólar =>> Real brasileño
4) Real brasileño =>> Dólar
5) Dólar =>> Peso colombiano
6) Peso colombiano =>> Dólar
7) Dólar =>> Peso chileno
8) Peso chileno =>> Dólar
9) Dólar =>> Boliviano boliviano
10) Boliviano boliviano =>> Dólar
11) Salir
*************************************
```

### 🚀 **Modo Avanzado**

Funcionalidades completas:

```
🔹 MENÚ PRINCIPAL 🔹
1) 🚀 Conversiones rápidas (favoritas)
2) 🎯 Conversión personalizada
3) 🌐 Explorar todas las monedas
4) 📜 Historial avanzado de conversiones
5) 📊 Estadísticas y análisis
6) 🔍 Buscar en historial
7) 📅 Actividad de hoy
8) ⭐ Gestionar favoritos
9) 🚪 Salir
```

### 💡 **Ejemplo de Conversión**

```
Conversión: USD → ARS
Ingrese la cantidad: 100

Procesando conversión...

==================================================
           RESULTADO DE CONVERSIÓN
==================================================
💱 $ 100.00 USD = $ 35,025.50 ARS
📈 Tasa: 1 USD = 350.255000 ARS
📉 Inversa: 1 ARS = 0.002855 USD
🌍 Dólar estadounidense → Peso argentino
⏰ Conversión guardada en historial
==================================================
```

---

## 💱 **Monedas Soportadas**

### 🌎 **Latinoamericanas (8)**
| Código | Moneda | Símbolo | País |
|--------|--------|---------|------|
| **ARS** | Peso argentino | $ | 🇦🇷 Argentina |
| **BOB** | Boliviano boliviano | Bs | 🇧🇴 Bolivia |
| **BRL** | Real brasileño | R$ | 🇧🇷 Brasil |
| **CLP** | Peso chileno | $ | 🇨🇱 Chile |
| **COP** | Peso colombiano | $ | 🇨🇴 Colombia |
| **MXN** | Peso mexicano | $ | 🇲🇽 México |
| **PEN** | Sol peruano | S/ | 🇵🇪 Perú |
| **UYU** | Peso uruguayo | $U | 🇺🇾 Uruguay |

### 💰 **Principales (7)**
| Código | Moneda | Símbolo | Región |
|--------|--------|---------|--------|
| **USD** | Dólar estadounidense | $ | 🇺🇸 Estados Unidos |
| **EUR** | Euro | € | 🇪🇺 Eurozona |
| **GBP** | Libra esterlina | £ | 🇬🇧 Reino Unido |
| **JPY** | Yen japonés | ¥ | 🇯🇵 Japón |
| **CAD** | Dólar canadiense | C$ | 🇨🇦 Canadá |
| **CHF** | Franco suizo | CHF | 🇨🇭 Suiza |
| **AUD** | Dólar australiano | A$ | 🇦🇺 Australia |

### 🌏 **Asiáticas (3)**
| Código | Moneda | Símbolo | País |
|--------|--------|---------|------|
| **CNY** | Yuan chino | ¥ | 🇨🇳 China |
| **KRW** | Won surcoreano | ₩ | 🇰🇷 Corea del Sur |
| **INR** | Rupia india | ₹ | 🇮🇳 India |


---

## 🛠️ **Desarrollo y Contribución**

### 🔍 **Debugging y Logs**

Para habilitar logs detallados, descomenta la línea en `ApiConfig.java`:
```java
// System.out.println("API key loaded successfully");
```

---

## 📚 **Documentación Técnica**

### 🌐 **API Integration**
- **Endpoint Base**: `https://v6.exchangerate-api.com/v6/`
- **Rate Limiting**: 1500 requests/mes (plan gratuito)
- **Formato**: JSON con estructura estándar
- **Documentación**: [ExchangeRate-API Docs](https://www.exchangerate-api.com/docs)

### 💾 **Persistencia de Datos**
- **Archivo**: `conversion_history.txt`
- **Formato**: Pipe-separated values (PSV)
- **Campos**: `from|to|amount|result|rate|timestamp`
- **Codificación**: UTF-8

### 🔐 **Seguridad**
- ✅ API keys en variables de entorno
- ✅ Validación de entrada robusta
- ✅ Timeouts configurados para requests
- ✅ Manejo seguro de errores

### ⚡ **Performance**
- ✅ HttpClient reutilizable
- ✅ Lazy loading de configuración
- ✅ Caching de enum values
- ✅ BigDecimal para precisión matemática

---

## 🏆 **Créditos**

### 👨‍💻 **Desarrollo**
- **Proyecto**: Challenge Alura - Conversor de Monedas
- **Lenguaje**: Java 24+
- **Arquitectura**: Capas con separación de responsabilidades

### 🛠️ **Tecnologías Utilizadas**
- **☕ Java 24+** - HttpClient nativo
- **📊 Gson 2.10.1** - Procesamiento JSON
- **🌐 ExchangeRate-API** - Datos de tasas de cambio
- **⏰ java.time** - Manejo de fechas y zonas horarias
- **🧮 BigDecimal** - Precisión matemática

### 📖 **Recursos de Aprendizaje**
- [Documentación Java HttpClient](https://docs.oracle.com/en/java/javase/24/docs/api/java.net.http/java/net/http/HttpClient.html)
- [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)
- [ExchangeRate-API Documentation](https://www.exchangerate-api.com/docs)
- [ISO 4217 Currency Codes](https://en.wikipedia.org/wiki/ISO_4217)

---

<div align="center">

### 🌟 **¡Gracias por usar CoinFluxCraft!** 🌟

Si este proyecto te fue útil, ⭐ **dale una estrella** en GitHub
**Hecho con ❤️ y ☕ para la comunidad de desarrolladores Java**

</div>
