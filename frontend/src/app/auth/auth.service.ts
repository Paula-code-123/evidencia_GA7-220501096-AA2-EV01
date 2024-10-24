import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/api/v1/auth/login';

    constructor(private http: HttpClient) { }

    // Método para autenticar
    login(username: string, password: string): Observable<any> {
        return this.http.post<any>(this.apiUrl, { username, password })
            .pipe(
                tap(response => {
                    // Almacenar el token en localStorage o sessionStorage
                    localStorage.setItem('token', response.token);
                })
            );
    }

    // Método para obtener el token almacenado
    getToken(): string | null {
        return localStorage.getItem('token');
    }

    // Método para cerrar sesión
    logout(): void {
        localStorage.removeItem('token');
    }

    // Verificar si el usuario está autenticado
    isAuthenticated(): boolean {
        return !!this.getToken(); // Retorna true si hay un token almacenado
    }
}