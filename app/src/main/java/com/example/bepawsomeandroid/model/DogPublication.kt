data class dogPublication(
    val id: String, // Puedes usar un identificador único para cada publicación
    val raza: String,
    val subraza: String?,
    val nombre: String,
    val edad: Int,
    val genero: Genero, // Enum para macho o hembra
    val descripcion: String,
    val peso: Double,
    val ubicacion: Provincia,
    val imagenes: List<String> ,// Lista de URLs de imágenes
    val isFavorite: Boolean = false // Si el usuario lo marcó como favorito,
)

enum class Genero {
    MACHO,
    HEMBRA
}
enum class Provincia {
    BUENOS_AIRES,
    CATAMARCA,
    CHACO,
    CHUBUT,
    CORDOBA,
    CORRIENTES,
    ENTRE_RIOS,
    FORMOSA,
    JUJUY,
    LA_PAMPA,
    LA_RIOJA,
    MENDOZA,
    MISIONES,
    NEUQUEN,
    RIO_NEGRO,
    SALTA,
    SAN_JUAN,
    SAN_LUIS,
    SANTA_CRUZ,
    SANTA_FE,
    SANTIAGO_DEL_ESTERO,
    TIERRA_DEL_FUEGO,
    TUCUMAN
}
