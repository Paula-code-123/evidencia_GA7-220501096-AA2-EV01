import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Cancion } from "../interfaces/cancion";
import { ListaReproduccionRequest } from "../interfaces/lista-reproduccion-request";
import { ListaReproduccionResponse } from "../interfaces/lista-reproduccion-response";

@Injectable({
    providedIn: 'root',
})
export class PlayListService {

    private apiUrl = 'http://localhost:8080/api/v1/lists';
    constructor(private http: HttpClient) {
    }

    verCanciones() {
        return this.http.get<Cancion[]>(this.apiUrl + '/listar-canciones');
    }

    crearListaReproduccion(lista: ListaReproduccionRequest) {
        return this.http.post<ListaReproduccionResponse>(this.apiUrl, lista);
    }

    verListasReproduccion() {
        return this.http.get<ListaReproduccionResponse[]>(this.apiUrl);
    }

    verListaReproduccionPorId(id: number) {
        return this.http.get<ListaReproduccionResponse>(this.apiUrl + '/' + id);
    }

    eliminarListaReproduccionPorId(id: number) {
        return this.http.delete(this.apiUrl + '/' + id);
    }
}