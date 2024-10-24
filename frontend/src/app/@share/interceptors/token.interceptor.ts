import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../../auth/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService); // Usamos `inject` para acceder al servicio en la función
    const token = authService.getToken();

    if (token) {
        const cloned = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        });
        return next(cloned);
    }
    return next(req);
};