import { Cancion } from "./cancion";

export interface ListaReproduccionResponse {
    id: Number;
    nombre: string;
    descripcion: string;
    canciones: Cancion[];
}